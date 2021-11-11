package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carreraId")
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false, columnDefinition = "text")
    private String nombre;

    @OneToMany(mappedBy = "carreraAlumno")
    @JsonIgnore
    private Set<Alumno> alumnos;

    @OneToMany(mappedBy = "carreraCompetencia")
    @JsonIgnore
    private Set<Competencia> competencias;

    @ManyToMany
    @JoinTable(
            name = "PerteneceCursoCarrera",
            joinColumns = @JoinColumn(
                    name = "carreraId",
                    referencedColumnName = "carreraId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "codCurso",
                    referencedColumnName = "codCurso",
                    columnDefinition = "text"
            )
    )
    @JsonIgnore
    private Set<Curso> cursosPertenece;

    public Carrera() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Set<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Set<Competencia> competencias) {
        this.competencias = competencias;
    }

    public Set<Curso> getCursosPertenece() {
        return cursosPertenece;
    }

    public void setCursosPertenece(Set<Curso> cursosPertenece) {
        this.cursosPertenece = cursosPertenece;
    }
}
