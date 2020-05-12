package com.salesianostriana.dam.keepNotas.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
data class Nota (

        var titulo : String,
        var contenido : String,

        @JsonBackReference
        @ManyToOne
        var autor : Usuario,


        @Id @GeneratedValue val id: UUID? = null,

        var fechaCreacion : LocalDate? = LocalDate.now(),
        var ultimaEdicion : LocalDate? = LocalDate.now()


)

@Entity
data class Usuario (

        var nombre : String,
        var apellidos : String,
        var nick : String,
        var password : String,

        @Id @GeneratedValue val id: UUID? = null,

        @JsonManagedReference
        @OneToMany(mappedBy="autor", fetch = FetchType.LAZY)
        var notas : List<Nota>? = null
)