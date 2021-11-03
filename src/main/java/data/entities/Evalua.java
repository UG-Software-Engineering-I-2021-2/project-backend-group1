package data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class EvaluaPK implements Serializable {
    @Column
    private AlumnoPK alumnoPK;

    @Column
    private RubricaPK rubricaPK;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluaPK evaluaPK = (EvaluaPK) o;
        return alumnoPK.equals(evaluaPK.alumnoPK) && rubricaPK.equals(evaluaPK.rubricaPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alumnoPK, rubricaPK);
    }
}

@Entity
@Table(name = "EvaluaAlumnoRubrica")
public class Evalua {
    @EmbeddedId
    private EvaluaPK evaluaPK;

    @Column(name = "calificacion", columnDefinition = "text", nullable = false)
    private String calificacion;

    @JoinColumns(value = {
            @JoinColumn(
                    name = "codAlumno",
                    referencedColumnName = "codAlumno",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "carreraId",
                    referencedColumnName = "carreraId",
                    insertable = false,
                    updatable = false
            )
    })
    @ManyToOne
    private Alumno alumnoEvalua;

    @JoinColumns(value = {
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
