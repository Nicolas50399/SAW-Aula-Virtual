package frba.utn.edu.ar.aula_virtual.services;

import frba.utn.edu.ar.aula_virtual.entities.Curso;
import frba.utn.edu.ar.aula_virtual.entities.Usuario;

import java.util.Optional;
import java.util.Set;

public interface CursoService {

    /**
     * Busca los cursos en los que un usuario está inscripto como estudiante.
     * @param username El nombre de usuario del estudiante.
     * @return Un conjunto de Cursos.
     */
    Set<Curso> findCursosPorEstudiante(String username);

    /**
     * Busca los cursos que un usuario dicta como profesor.
     * @param username El nombre de usuario del profesor.
     * @return Un conjunto de Cursos.
     */
    Set<Curso> findCursosPorProfesor(String username);

    /**
     * Busca un curso específico por su ID.
     * @param id El ID del curso.
     * @return Un Optional que puede contener el Curso si se encuentra.
     */
    Optional<Curso> findCursoById(Long id);
}
