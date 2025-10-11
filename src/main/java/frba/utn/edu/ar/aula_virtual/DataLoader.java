package frba.utn.edu.ar.aula_virtual;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired // Agregamos el repositorio de cursos
    private CursoRepository cursoRepository;

    // Inyectamos los valores del application.properties
    @Value("${app.security.hash-algorithm}")
    private String HASH_ALGORITHM;

    @Value("${app.security.salt}")
    private String SALT;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // --- 1. CREACIÓN DE USUARIOS CON ROLES ---

        // Administrador
        Usuario adminUser = new Usuario();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin123"); // Contraseña insegura
        adminUser.setRole("ADMIN");

        // Profesor
        Usuario profesorUser = new Usuario();
        profesorUser.setUsername("profe.garcia");
        profesorUser.setPassword(hashPassword("profesor123")); // Contraseña insegura
        profesorUser.setRole("PROFESOR");

        // Estudiantes
        Usuario estudiante1User = new Usuario();
        estudiante1User.setUsername("juan.perez");
        estudiante1User.setPassword("juan123"); // Contraseña insegura
        estudiante1User.setRole("ESTUDIANTE");

        Usuario estudiante2User = new Usuario();
        estudiante2User.setUsername("ana.lopez");
        estudiante2User.setPassword(hashPassword("ana123")); // Contraseña insegura
        estudiante2User.setRole("ESTUDIANTE");

        // Guardamos todos los usuarios en la BD
        usuarioRepository.saveAll(List.of(adminUser, profesorUser, estudiante1User, estudiante2User));


        // --- 2. CREACIÓN DE CURSOS Y ASIGNACIÓN DE ROLES ---

        Curso cursoProgramacion = new Curso();
        cursoProgramacion.setNombre("Programación I");

// Asignamos el profesor al curso
        cursoProgramacion.setProfesor(profesorUser);

// Inscribimos al estudiante al curso (estableciendo la relación en AMBAS entidades)
        cursoProgramacion.getEstudiantes().add(estudiante1User);             // Lado del Curso
        estudiante1User.getCursosInscriptos().add(cursoProgramacion); // Lado del Usuario (ESTA ES LA LÍNEA NUEVA Y CLAVE)

// Guardamos el curso (JPA se encarga de las relaciones)
        cursoRepository.save(cursoProgramacion);


        // --- 3. MENSAJE DE CONFIRMACIÓN EN CONSOLA ---
        System.out.println("----------------------------------------------------");
        System.out.println(">>> Base de datos inicializada con datos de prueba <<<");
        System.out.println("Usuarios creados:");
        System.out.println("  - " + adminUser.getUsername() + " (Rol: " + adminUser.getRole() + ")");
        System.out.println("  - " + profesorUser.getUsername() + " (Rol: " + profesorUser.getRole() + ")");
        System.out.println("  - " + estudiante1User.getUsername() + " (Rol: " + estudiante1User.getRole() + ")");
        System.out.println("  - " + estudiante2User.getUsername() + " (Rol: " + estudiante2User.getRole() + ")");
        System.out.println("Cursos creados:");
        System.out.println("  - '" + cursoProgramacion.getNombre() + "' dictado por " + profesorUser.getUsername());
        System.out.println("    -> Estudiantes inscriptos: " + cursoProgramacion.getEstudiantes().size());
        System.out.println("----------------------------------------------------");
    }

    /**
     * Método helper para hashear contraseñas usando el mecanismo vulnerable.
     * @param password Contraseña en texto plano.
     * @return Contraseña hasheada en Base64.
     */
    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        md.update(SALT.getBytes(StandardCharsets.UTF_8));
        byte[] hashedPasswordBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPasswordBytes);
    }
}
