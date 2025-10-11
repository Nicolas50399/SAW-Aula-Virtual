package frba.utn.edu.ar.aula_virtual;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Importante añadir esta línea
import org.springframework.security.crypto.password.PasswordEncoder; // Y esta
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Le decimos a Spring Security que use nuestra base de datos para buscar usuarios.
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("SELECT username, password, 'true' as enabled FROM usuario WHERE username=?");
        users.setAuthoritiesByUsernameQuery("SELECT username, role as authority FROM usuario WHERE username=?");
        return users;
    }

    // --- NUEVO MÉTODO AÑADIDO ---
    // Definimos un PasswordEncoder que no hace nada.
    // Esto es INSEGURO para producción, pero necesario para nuestra vulnerabilidad de hash manual.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    // -----------------------------

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/dashboard", true)
                )
                .logout(logout -> logout.permitAll());

        // Deshabilitamos la protección CSRF para la ruta de la consola H2
        http.csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()));
        // Permitimos que la consola H2 se muestre en un frame (necesario para su funcionamiento)
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}