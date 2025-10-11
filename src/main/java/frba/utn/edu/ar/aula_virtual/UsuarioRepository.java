package frba.utn.edu.ar.aula_virtual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // Con solo extender JpaRepository, Spring Data nos da automáticamente
    // métodos como save(), findById(), findAll(), etc. No necesitamos escribir más.
}
