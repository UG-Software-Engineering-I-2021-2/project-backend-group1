package data.entities;

import data.entities.composite_keys.LlevaPK;

import javax.persistence.*;

@Entity
@Table(name = "LLevaAlumnoSeccion")
public class Lleva {
    @EmbeddedId
    private LlevaPK llevaPK;

    @Column(name = "ciclo", nullable = false)
    private Short ciclo;

    @MapsId("codAlumno")
    @JoinColumn(name = "codAlumno", referencedColumnName = "codAlumno")
    @ManyToOne
    private Alumno alumnoLleva;

    @JoinColumns(value = {
            @JoinColumn(
                    name = "codSeccion",
                    referencedColumnName = "codSeccion",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "semestre",
                    referencedColumnName = "semestre",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "codCurso",
                    referencedColumnName = "codCurso",
                    insertable = false,
                    updatable = false
            )
    })
    @ManyToOne
    private Seccion seccionLleva;

    // Getters and setters

    public LlevaPK getLlevaPK() {
        return llevaPK;
    }

    public void setLlevaPK(LlevaPK llevaPK) {
        this.llevaPK = llevaPK;
    }

    public Short getCiclo() {
        return ciclo;
    }

    public void setCiclo(Short ciclo) {
        this.ciclo = ciclo;
    }

    public Alumno getAlumnoLleva() {
        return alumnoLleva;
    }

    public void setAlumnoLleva(Alumno alumnoLleva) {
        this.alumnoLleva = alumnoLleva;
    }

    public Seccion getSeccionLleva() {
        return seccionLleva;
    }

    public void setSeccionLleva(Seccion seccionLleva) {
        this.seccionLleva = seccionLleva;
    }
}
