package com.salesianostriana.dam.keepNotas.repository

import com.salesianostriana.dam.keepNotas.model.Autor
import com.salesianostriana.dam.keepNotas.model.Nota
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

interface NotaRepository : JpaRepository<Nota, UUID> {

}

interface AutorRepository : JpaRepository<Autor, UUID> {

}

@Component
class InitDataComponent(
        val notaRepository: NotaRepository,
        val autorRepository: AutorRepository
) {

    @PostConstruct
    fun initData() {

    }
}