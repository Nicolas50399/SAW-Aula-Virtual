package frba.utn.edu.ar.aula_virtual.repositories;

import frba.utn.edu.ar.aula_virtual.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importa esta clase
import org.springframework.data.repository.query.Param; // Y esta
import org.springframework.stereotype.Repository;

import java.util.Set; // Y esta

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // --- MÉTODO NUEVO AÑADIDO ---
    /**
     * Busca todos los cursos en los que un estudiante está inscripto,
     * usando una consulta JPQL explícita para evitar problemas de Lazy Loading.
     * @param username El nombre de usuario del estudiante.
     * @return Un conjunto de Cursos.
     */
    @Query("SELECT c FROM Curso c JOIN c.estudiantes e WHERE e.username = :username")
    Set<Curso> findCursosByEstudianteUsername(@Param("username") String username);

}