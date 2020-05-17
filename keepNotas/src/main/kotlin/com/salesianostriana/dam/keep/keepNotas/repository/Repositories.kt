package com.salesianostriana.dam.keep.keepNotas.repository

import com.salesianostriana.dam.keep.keepNotas.model.Nota
import com.salesianostriana.dam.keep.keepNotas.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

interface NotaRepository : JpaRepository<Nota, UUID> {


}

interface UsuarioRepository : JpaRepository<Usuario, UUID> {

    fun findByUsername(username : String) : Optional<Usuario>

}

@Component
class InitDataComponent(
        val notaRepository: NotaRepository,
        val usuarioRepository: UsuarioRepository,
        val encoder: PasswordEncoder
) {

    @PostConstruct
    fun initData() {
        val user = Usuario("usuario", encoder.encode("1234"), "Usuario", "USER")
        val admin = Usuario("admin", encoder.encode("1234"), "Administrador", "ADMIN")
        usuarioRepository.save(user)
        usuarioRepository.save(admin)

        var nota1 = Nota("Me gradúo", "Estoy preparado para lo que sea", user)
        var nota2 = Nota("Pan y huevos", "Para que no se me olvide", admin)
        notaRepository.save(nota1)
        notaRepository.save(nota2)

    }
}