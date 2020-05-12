package com.salesianostriana.dam.keepNotas.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.salesianostriana.dam.keepNotas.model.Nota
import com.salesianostriana.dam.keepNotas.model.Usuario
import java.time.LocalDate
import java.util.*

data class NotaDTO(
        val id: UUID?,
        val titulo: String,
        val contenido: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val fechaCreacion: LocalDate?,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val ultimaEdicion: LocalDate?,
        val autor: Usuario
        )

fun Nota.toNotaDTO() = NotaDTO(id, titulo, contenido, fechaCreacion, ultimaEdicion, autor)

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

fun List<NotaDTO>.toListNota(notas : List<NotaDTO>): List<Nota> {
        var notasChanged : MutableList<Nota> = mutableListOf()
        if(notas.isNotEmpty()) {
                for(nota in notas) {
                       notasChanged.add(nota.toNota())
                }
        }

        return notasChanged.toList()
}

fun NotaDTO.toNota() = Nota( titulo, contenido, autor, id, fechaCreacion, ultimaEdicion)

data class NuevaNotaDTO(
        val titulo: String,
        val contenido: String,
        val autor: Usuario

)

fun NuevaNotaDTO.toNota() = Nota(titulo, contenido, autor)

data class UsuarioDTO(
        val id : UUID?,
        val nombre : String,
        val apellidos : String,
        val nick : String,
        val password : String,
        val notas : List<NotaDTO>?
)

fun Usuario.toUsuarioDTO() = UsuarioDTO(id, nombre, apellidos, nick, password, notas.toListNotaDTO(notas))