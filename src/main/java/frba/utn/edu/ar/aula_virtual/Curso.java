package frba.utn.edu.ar.aula_virtual;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;

    private String nombre;

    // Relación: Muchos cursos pueden ser dictados por un profesor.
    @ManyToOne
    @JoinColumn(name = "profesor_username") // Clave foránea
    private Usuario profesor;

    // Relación: Muchos cursos pueden tener muchos estudiantes.
    // @JoinTable crea la tabla intermedia para la relación Many-to-Many.
    @ManyToMany
    @JoinTable(
            name = "curso_estudiante",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "estudiante_username")
    )
    private Set<Usuario> estudiantes = new HashSet<>();


    // --- Getters y Setters ---

    public Curso() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Usuario getProfesor() { return profesor; }
    public void setProfesor(Usuario profesor) { this.profesor = profesor; }
    public Set<Usuario> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Set<Usuario> estudiantes) { this.estudiantes = estudiantes; }
}
