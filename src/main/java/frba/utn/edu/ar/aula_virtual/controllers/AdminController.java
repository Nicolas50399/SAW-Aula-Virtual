package frba.utn.edu.ar.aula_virtual.controllers;

import frba.utn.edu.ar.aula_virtual.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin") // <- El prefijo para toda la clase
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/panel")
    public String mostrarPanelAdmin() {
        return "admin-panel";
    }

    // --- MÉTODO CORREGIDO ---
    // La ruta ahora es solo "/buscar", que se combinará con "/admin"
    @PostMapping("/buscar")
    public String buscarUsuario(@RequestParam String username, Model model) {

        // --- ¡¡¡INICIO DE LA VULNERABILIDAD DE INYECCIÓN SQL!!! ---
        String sqlQuery = "SELECT * FROM usuario WHERE username = '" + username + "'";
        // --- FIN DE LA VULNERABILIDAD ---

        System.out.println("--- DEBUG: Ejecutando consulta SQL vulnerable: " + sqlQuery);

        try {
            Query query = entityManager.createNativeQuery(sqlQuery, Usuario.class);
            List<Usuario> usuarios = query.getResultList();
            model.addAttribute("usuariosEncontrados", usuarios);
        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            model.addAttribute("usuariosEncontrados", List.of());
        }

        return "admin-panel";
    }
}