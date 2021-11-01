package data.entities;

import javax.persistence.*;

@Entity
@Table(name = "Curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curso_id")
    private Long id;

    @Column(name = "codcurso", nullable = false, columnDefinition = "text")
    private String codcurso;

    @Column(name = "nombre", nullable = false, columnDefinition = "text")
    private String nombre;

    @Column(name = "ciclo", nullable = false)
    private Short ciclo;

    @Column(name = "semestre", nullable = false, columnDefinition = "text")
    private String semestre;

    @Column(name = "seccion", nullable = false, columnDefinition = "text")
    private String seccion;

    public Curso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodcurso() {
        return codcurso;
    }

    public void setCodcurso(String codcurso) {
        this.codcurso = codcurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getCiclo() {
        return ciclo;
    }

    public void setCiclo(Short ciclo) {
        this.ciclo = ciclo;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
