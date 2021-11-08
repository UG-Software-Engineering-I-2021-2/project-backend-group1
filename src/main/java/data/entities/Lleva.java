package data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

@Embeddable
class LlevaPK implements Serializable {
    @Column(name = "codAlumno", length = COD_ALUMNO_LENGTH)
    private String codAlumno;

    private SeccionPK seccionPK;

    // Getters and setters

    public String getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(String codAlumno) {
        this.codAlumno = codAlumno;
    }

    public SeccionPK getSeccionPK() {
        return seccionPK;
    }

    public void setSeccionPK(SeccionPK seccionPK) {
        this.seccionPK = seccionPK;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LlevaPK llevaPK = (LlevaPK) o;
        return codAlumno.equals(llevaPK.codAlumno) && seccionPK.equals(llevaPK.seccionPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAlumno, seccionPK);
    }
}

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
