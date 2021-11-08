package data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

@Embeddable
class EvaluaPK implements Serializable {
    @Column(name = "codAlumno", length = COD_ALUMNO_LENGTH)
    private String codAlumno;

    private RubricaPK rubricaPK;

    // Getters and setters

    public String getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(String codAlumno) {
        this.codAlumno = codAlumno;
    }

    public RubricaPK getRubricaPK() {
        return rubricaPK;
    }

    public void setRubricaPK(RubricaPK rubricaPK) {
        this.rubricaPK = rubricaPK;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluaPK evaluaPK = (EvaluaPK) o;
        return codAlumno.equals(evaluaPK.codAlumno) && rubricaPK.equals(evaluaPK.rubricaPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAlumno, rubricaPK);
    }
}

@Entity
@Table(name = "EvaluaAlumnoRubrica")
public class Evalua {
    @EmbeddedId
    private EvaluaPK evaluaPK;

    @Column(name = "calificacion", columnDefinition = "text", nullable = false)
    private String calificacion;

    @MapsId("codAlumno")
    @JoinColumn(name = "codAlumno", referencedColumnName = "codAlumno")
    @ManyToOne
    private Alumno alumnoEvalua;

    @JoinColumns(value = {
            @JoinColumn(
                    name = "semestre",
                    referencedColumnName = "semestre",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "codRubrica",
                    referencedColumnName = "codRubrica",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "codCurso",
                    referencedColumnName = "codCurso",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "codCompetencia",
                    referencedColumnName = "codCompetencia",
                    insertable = false,
                    updatable = false
            )
    })
    @ManyToOne
    private Rubrica rubricaEvalua;

    public Evalua() {
    }

    // Getters and setters

    public EvaluaPK getEvaluaPK() {
        return evaluaPK;
    }

    public void setEvaluaPK(EvaluaPK evaluaPK) {
        this.evaluaPK = evaluaPK;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public Alumno getAlumnoEvalua() {
        return alumnoEvalua;
    }

    public void setAlumnoEvalua(Alumno alumnoEvalua) {
        this.alumnoEvalua = alumnoEvalua;
    }

    public Rubrica getRubricaEvalua() {
        return rubricaEvalua;
    }

    public void setRubricaEvalua(Rubrica rubricaEvalua) {
        this.rubricaEvalua = rubricaEvalua;
    }
}
