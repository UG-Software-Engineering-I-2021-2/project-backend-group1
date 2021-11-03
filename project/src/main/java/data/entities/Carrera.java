package data.entities;

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
    private Set<Alumno> alumnos;

    @OneToMany(mappedBy = "carreraCompetencia")
    private Set<Competencia> competencias;

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
}
