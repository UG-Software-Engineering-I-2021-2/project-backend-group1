package data.entities;

import data.entities.composite_keys.EvaluaPK;

import javax.persistence.*;

@Entity
@Table(name = "EvaluaAlumnoRubrica")
public class Evalua {
    @EmbeddedId
    private EvaluaPK evaluaPK;

    @Column(name = "calificacionAlumno", columnDefinition = "text")
    private String calificacionAlumno;

    @Column(name = "calificacionCompetencia", columnDefinition = "text")
    private String calificacionCompetencia;

    @Column(name = "evaluacionTotal", nullable = false)
    private boolean evaluacionTotal;

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

    public String getCalificacionAlumno() {
        return calificacionAlumno;
    }

    public void setCalificacionAlumno(String calificacionAlumno) {
        this.calificacionAlumno = calificacionAlumno;
    }

    public String getCalificacionCompetencia() {
        return calificacionCompetencia;
    }

    public void setCalificacionCompetencia(String calificacionCompetencia) {
        this.calificacionCompetencia = calificacionCompetencia;
    }

    public boolean isEvaluacionTotal() {
        return evaluacionTotal;
    }

    public void setEvaluacionTotal(boolean evaluacionTotal) {
        this.evaluacionTotal = evaluacionTotal;
    }
}
