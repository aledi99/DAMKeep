package com.salesianostriana.dam.keep.keepNotas.security

import com.salesianostriana.dam.keep.keepNotas.security.jwt.JwtAuthenticationEntryPoint
import com.salesianostriana.dam.keep.keepNotas.security.jwt.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfigurePasswordEncoder() {

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

}

@Configuration
class ConfigureCors() {

    @Bean
    fun corsConfigurer()  = object : WebMvcConfigurer {

        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                    .allowedOrigins("*", "10.0.2.2")
                    .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                    .maxAge(3600)
        }
    }


}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
        private val userDetailsService: UserDetailsService,
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        private val jwtAuthorizationFilter: JwtAuthorizationFilter,
        private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console/**").permitAll()
                .antMatchers(POST, "/auth/login", "/auth/token", "/user/").permitAll()
                .antMatchers(GET, "/nota/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(POST, "/nota/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(PUT, "/nota/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(DELETE, "/nota/**").hasAnyRole("USER", "ADMIN")

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        //For the H2 console
        http.headers().frameOptions().disable()

        // @formatter:on
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


}
