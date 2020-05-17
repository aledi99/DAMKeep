package com.salesianostriana.dam.keep.keepNotas.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.salesianostriana.dam.keep.keepNotas.model.Nota
import com.salesianostriana.dam.keep.keepNotas.model.Usuario
import org.aspectj.weaver.ast.Not
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class NotaDTO(
        val id: UUID?,
        val titulo: String,
        val contenido: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val fechaCreacion: LocalDateTime?,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val ultimaEdicion: LocalDateTime?,
        val autor: UsuarioDTO?
        )

fun Nota.toNotaDTO() = NotaDTO(id, titulo, contenido, fechaCreacion, ultimaEdicion, autor?.toUsuarioDTO())

fun List<Nota>?.toListNotaDTO(notas : List<Nota>?): List<NotaDTO>? {
        var notasChanged : MutableList<NotaDTO> = mutableListOf()
        if (notas != null) {
                if(notas.isNotEmpty()) {
                if (notas != null) {
                        for(nota in notas) {
                                notasChanged.add(nota.toNotaDTO())
                        }
                }
                }
        }

        return notasChanged.toList()
}


data class NuevaNotaDTO(
        val titulo: String,
        val contenido: String
)

fun NuevaNotaDTO.toNota(usuario : Usuario) = Nota(titulo, contenido, usuario)

data class UsuarioDTO(
        var nick : String,
        var nombreCompleto : String,
        var roles : String,
        val id: UUID? = null
)

fun Usuario.toUsuarioDTO() = UsuarioDTO(username, nombreCompleto, roles.joinToString(), id)

data class NewUsuarioDTO(
        var nick: String,
        var fullname: String,
        val password: String,
        val password2: String
)