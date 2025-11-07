package frba.utn.edu.ar.aula_virtual.services;

import frba.utn.edu.ar.aula_virtual.entities.Curso;
import frba.utn.edu.ar.aula_virtual.entities.Usuario;
import frba.utn.edu.ar.aula_virtual.repositories.CursoRepository;
import frba.utn.edu.ar.aula_virtual.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service // Le dice a Spring que esta clase es un servicio y que debe gestionarla.
public class CursoServiceImplementacion implements CursoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<Curso> findCursosPorEstudiante(String username) {
        System.out.println("--- DEBUG: Entrando a findCursosPorEstudiante para el usuario: " + username);
        Set<Curso> cursosEncontrados = cursoRepository.findCursosByEstudianteUsername(username);
        System.out.println("--- DEBUG: La consulta al repositorio encontró " + cursosEncontrados.size() + " cursos.");
        return cursosEncontrados;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Curso> findCursosPorProfesor(String username) {
        // Esta lógica para el profesor puede quedarse como está, ya que es una relación diferente
        // y parece no estar dando problemas.
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(username);
        return usuarioOpt.map(Usuario::getCursosDictados).orElse(Set.of());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findCursoById(Long id) {
        return cursoRepository.findById(id);
    }
}
