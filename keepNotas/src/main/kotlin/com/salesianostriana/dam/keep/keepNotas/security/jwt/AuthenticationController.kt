package com.salesianostriana.dam.keep.keepNotas.security.jwt

import com.salesianostriana.dam.keep.keepNotas.dto.UsuarioDTO
import com.salesianostriana.dam.keep.keepNotas.dto.toUsuarioDTO
import com.salesianostriana.dam.keep.keepNotas.model.Usuario
import com.salesianostriana.dam.keep.keepNotas.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class AuthenticationController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenProvider: JwtTokenProvider,
        private val bearerTokenExtractor: BearerTokenExtractor,
        private val userService: UsuarioService
) {

    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody loginRequest : LoginRequest) : ResponseEntity<JwtUserResponse> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.username, loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as Usuario
        //val jwtToken = jwtTokenProvider.generateToken(authentication)
        val jwtToken = jwtTokenProvider.generateToken(user)
        //val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(authentication)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse(jwtToken, jwtRefreshToken, user.toUsuarioDTO()))

    }

    @PostMapping("/auth/token")
    fun refreshToken(request : HttpServletRequest) : ResponseEntity<JwtUserResponse> {

        val refreshToken = bearerTokenExtractor.getJwtFromRequest(request).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el token de refresco")
        }

        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                val userId = jwtTokenProvider.getUserIdFromJWT(refreshToken)
                val user: Usuario = userService.findById(userId).orElseThrow {
                    UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                }
                val jwtToken = jwtTokenProvider.generateToken(user)
                val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

                return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse(jwtToken, jwtRefreshToken, user.toUsuarioDTO()))
            }
        } catch (ex : Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error en la validación del token")
        }

        // En cualquier otro caso
        return ResponseEntity.badRequest().build()

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    fun me(@AuthenticationPrincipal user : Usuario) = user.toUsuarioDTO()




}


data class LoginRequest(
        @NotBlank val username : String,
        @NotBlank val password: String
)

data class JwtUserResponse(
        val token: String,
        val refreshToken: String,
        val user : UsuarioDTO
)