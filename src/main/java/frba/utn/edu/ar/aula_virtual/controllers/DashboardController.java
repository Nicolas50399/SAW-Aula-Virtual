package frba.utn.edu.ar.aula_virtual.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        // Obtenemos el rol y lo pasamos al modelo
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        model.addAttribute("username", username);
        model.addAttribute("role", role); // <-- AÑADIR ESTA LÍNEA

        return "dashboard";
    }

    // Creamos un controlador para la página de login personalizada
    @GetMapping("/login")
    public String showLogin() {
        return "login"; // Esto buscará un archivo llamado login.html
    }
}
