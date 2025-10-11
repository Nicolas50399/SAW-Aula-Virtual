package frba.utn.edu.ar.aula_virtual;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    private String username;
    private String password;
    private String role; // ADMIN, PROFESOR, ESTUDIANTE

    // --- NUEVAS RELACIONES ---

    // Relación: Un profesor puede tener muchos cursos.
    // 'mappedBy = "profesor"' indica que la entidad Curso es la dueña de esta relación.
    @OneToMany(mappedBy = "profesor")
    private Set<Curso> cursosDictados = new HashSet<>();

    // Relación: Un estudiante puede estar en muchos cursos, y un curso puede tener muchos estudiantes.
    // 'mappedBy' indica que la entidad Curso es la dueña de la relación.
    @ManyToMany(mappedBy = "estudiantes")
    private Set<Curso> cursosInscriptos = new HashSet<>();


    // --- Getters y Setters (actualizados) ---

    public Usuario() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Set<Curso> getCursosDictados() { return cursosDictados; }
    public void setCursosDictados(Set<Curso> cursosDictados) { this.cursosDictados = cursosDictados; }
    public Set<Curso> getCursosInscriptos() { return cursosInscriptos; }
    public void setCursosInscriptos(Set<Curso> cursosInscriptos) { this.cursosInscriptos = cursosInscriptos; }
}