package com.salesianostriana.dam.keep.keepNotas.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Nota (

        var titulo : String,
        var contenido : String,

        @JsonBackReference
        @ManyToOne
        var autor : Usuario? = null,

        @CreatedDate
        var fechaCreacion : LocalDateTime? = null,
        @LastModifiedDate
        var ultimaEdicion : LocalDateTime? = null,

        @Id @GeneratedValue val id: UUID? = null


)

@Entity
data class Usuario(

        @Column(nullable = false, unique = true)
        private var username: String,

        private var password: String,

        var nombreCompleto : String,


        @ElementCollection(fetch = FetchType.EAGER)
        val roles: MutableSet<String> = HashSet(),

        private val nonExpired: Boolean = true,

        private val nonLocked: Boolean = true,

        private val enabled: Boolean = true,

        private val credentialsNonExpired : Boolean = true,

        @Id @GeneratedValue val id : UUID? = null,

        @JsonBackReference
        @OneToMany(mappedBy="autor", fetch = FetchType.LAZY)
        var notas : List<Nota>? = null


) : UserDetails {

        constructor(username: String, password: String, nombreCompleto: String, role: String) :
                this(username, password, nombreCompleto, mutableSetOf(role), true, true, true, true)

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
                roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

        override fun isEnabled() = enabled
        override fun getUsername() = username
        override fun isCredentialsNonExpired() = credentialsNonExpired
        override fun getPassword() = password
        override fun isAccountNonExpired() = nonExpired
        override fun isAccountNonLocked() = nonLocked

        override fun equals(other: Any?): Boolean {
                if (this === other)
                        return true
                if (other === null || other !is Usuario)
                        return false
                if (this::class != other::class)
                        return false
                return id == other.id
        }

        override fun hashCode(): Int {
                if (id == null)
                        return super.hashCode()
                return id.hashCode()
        }
}