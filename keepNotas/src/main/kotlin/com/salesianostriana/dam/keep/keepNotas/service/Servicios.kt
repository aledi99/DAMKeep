package com.salesianostriana.dam.keep.keepNotas.service

import com.salesianostriana.dam.keep.keepNotas.dto.NewUsuarioDTO
import com.salesianostriana.dam.keep.keepNotas.model.Usuario
import com.salesianostriana.dam.keep.keepNotas.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(
        private val repo: UsuarioRepository,
        private val encoder: PasswordEncoder
){

    fun create(newUser : NewUsuarioDTO): Optional<Usuario> {
        if (findByUsername(newUser.nick).isPresent)
            return Optional.empty()
        return Optional.of(
                with(newUser) {
                    repo.save(Usuario(nick, encoder.encode(password), fullname, "USER"))
                }

        )
    }

    fun findByUsername(nick : String) = repo.findByUsername(nick)

    fun findById(id : UUID) = repo.findById(id)

}

@Service("userDetailsService")
class UserDetailsServiceImpl(
        private val userService: UsuarioService
) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails =
            userService.findByUsername(username).orElseThrow {
                UsernameNotFoundException("Usuario $username no encontrado")
            }
}