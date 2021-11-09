package data.entities;

import data.entities.composite_keys.EvaluaPK;

import javax.persistence.*;

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
