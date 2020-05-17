package com.salesianostriana.dam.keep.keepNotas.controller

import com.salesianostriana.dam.keep.keepNotas.dto.*
import com.salesianostriana.dam.keep.keepNotas.model.Nota
import com.salesianostriana.dam.keep.keepNotas.model.Usuario
import com.salesianostriana.dam.keep.keepNotas.repository.NotaRepository
import com.salesianostriana.dam.keep.keepNotas.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/nota")
class NotaController(val notaRepository: NotaRepository) {


    private fun allNotas(): List<Nota> {
        var result: List<Nota>
        with(notaRepository) {
            result = findAll()

        }
        if (result.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No existen notas")
        return result
    }

    private fun oneNota(id: UUID) : Nota {
        var result: Optional<Nota>
        with(notaRepository) {
            result = findById(id)

        }
        return result.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "No hay ninguna nota asociada al id")}
    }

    @GetMapping("/me")
    fun myNotas(@AuthenticationPrincipal user: Usuario) : List<NotaDTO>? {
        return user.notas?.map { it.toNotaDTO() }
    }

    @GetMapping("/")
    fun all() = allNotas().map { it.toNotaDTO() }

    @GetMapping("/{id}")
    fun onlyNota(@PathVariable id : UUID) = oneNota(id).toNotaDTO()

    @PostMapping("/")
    fun newNota(@RequestBody nuevaNota : NuevaNotaDTO, @AuthenticationPrincipal user : Usuario) =
            ResponseEntity.status(HttpStatus.CREATED).body(notaRepository.save(nuevaNota.toNota(user)).toNotaDTO())

    @PutMapping("/{id}")
    fun editNota(@RequestBody editNota: NuevaNotaDTO, @PathVariable id : UUID): NotaDTO {
        return notaRepository.findById(id)
                .map { notaFound ->
                    val notaUpdated : Nota =
                            notaFound.copy(titulo = editNota.titulo,
                            contenido = editNota.contenido)
                    notaRepository.save(notaUpdated).toNotaDTO()
                }.orElseThrow {
                    ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la nota con el id $id")
                }
    }

    @DeleteMapping("/{id}")
    fun deleteNota(@PathVariable id: UUID) : ResponseEntity<Void> {
        notaRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }


}

@RestController
@RequestMapping("/user")
class UserController(val userService: UsuarioService) {

    @PostMapping("/")
    fun nuevoUsuario(@RequestBody newUser : NewUsuarioDTO): ResponseEntity<UsuarioDTO> =
            userService.create(newUser).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toUsuarioDTO()) }.orElseThrow {
                ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${newUser.nick} ya existe")
            }

}