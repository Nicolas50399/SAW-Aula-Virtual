package frba.utn.edu.ar.aula_virtual.controllers;

import frba.utn.edu.ar.aula_virtual.entities.Curso;
import frba.utn.edu.ar.aula_virtual.services.CursoService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/cursos") // Todas las URLs de este controlador empezarán con /cursos
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/mis-cursos")
    public String mostrarMisCursos(Model model, Authentication authentication) {
        String username = authentication.getName();
        String rol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        System.out.println("--- DEBUG: Entrando a mostrarMisCursos para el usuario: " + username + " con rol: " + rol);

        Set<Curso> cursos;
        String tituloPagina = "";

        if ("ESTUDIANTE".equals(rol)) {
            cursos = cursoService.findCursosPorEstudiante(username);
            tituloPagina = "Mis Cursos Inscriptos";
        } else if ("PROFESOR".equals(rol)) {
            cursos = cursoService.findCursosPorProfesor(username);
            tituloPagina = "Cursos que Dicto";
        } else {
            cursos = Set.of();
            tituloPagina = "Mis Cursos";
        }

        System.out.println("--- DEBUG: El servicio devolvió " + cursos.size() + " cursos. Pasando al modelo.");

        model.addAttribute("cursos", cursos);
        model.addAttribute("titulo", tituloPagina);

        return "mis-cursos";
    }

    @GetMapping("{nombre}")
    public String verCurso(@PathVariable("nombre") String nombreCurso,
                           Model model,
                           Authentication authentication)
    {
        String rol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        model.addAttribute("role", rol);

        return "curso-detalle-" + nombreCurso;
    }


}
