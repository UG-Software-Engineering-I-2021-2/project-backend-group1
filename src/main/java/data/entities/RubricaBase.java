package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Embeddable
class RubricaBasePK implements Serializable {
    @Column(name = "codRubrica", columnDefinition = "text")
    private String codRubrica;

    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    @Column(name = "codCompetencia", columnDefinition = "text")
    private String codCompetencia;

    // Getters and setters

    public String getCodRubrica() {
        return codRubrica;
    }

    public void setCodRubrica(String codRubrica) {
        this.codRubrica = codRubrica;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public String getCodCompetencia() {
        return codCompetencia;
    }

    public void setCodCompetencia(String codCompetencia) {
        this.codCompetencia = codCompetencia;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubricaBasePK that = (RubricaBasePK) o;
        return codRubrica.equals(that.codRubrica) && codCurso.equals(that.codCurso) && codCompetencia.equals(that.codCompetencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codRubrica, codCurso, codCompetencia);
    }
}

@Entity
@Table(name = "RubricaBase")
public class RubricaBase {
    @EmbeddedId
    private RubricaBasePK rubricaPK;

    @Column(name = "actividadBase", columnDefinition = "text", nullable = false)
    private String actividadBase;

    @Column(name = "semana", nullable = false)
    private Short semana;

    @Column(name = "nivel", nullable = false)
    private Short nivel;

    @Column(name = "criterioDesempeno", columnDefinition = "text", nullable = false)
    private String criterioDesempeno;

    @Column(name = "evaluacion", columnDefinition = "text")
    private String evaluacion;

    @Column(name = "evidencia", columnDefinition = "text")
    private String evidencia;

    @MapsId("codCurso")
    @JoinColumn(name = "codCurso", referencedColumnName = "codCurso", columnDefinition = "text")
    @ManyToOne
    private Curso cursoRubrica;

    @MapsId("codCompetencia")
    @JoinColumn(name = "codCompetencia", referencedColumnName = "codCompetencia", columnDefinition = "text")
    @ManyToOne
    private Competencia competenciaRubrica;

    @OneToMany(mappedBy = "rubricaBase")
    @JsonIgnore
    private Set<Rubrica> rubricas;

    public RubricaBase() {
    }

    // Getters and setters

    public RubricaBasePK getRubricaPK() {
        return rubricaPK;
    }

    public void setRubricaPK(RubricaBasePK rubricaPK) {
        this.rubricaPK = rubricaPK;
    }

    public String getActividadBase() {
        return actividadBase;
    }

    public void setActividadBase(String actividadBase) {
        this.actividadBase = actividadBase;
    }

    public Short getSemana() {
        return semana;
    }

    public void setSemana(Short semana) {
        this.semana = semana;
    }

    public Short getNivel() {
        return nivel;
    }

    public void setNivel(Short nivel) {
        this.nivel = nivel;
    }

    public String getCriterioDesempeno() {
        return criterioDesempeno;
    }

    public void setCriterioDesempeno(String criterioDesempeno) {
        this.criterioDesempeno = criterioDesempeno;
    }

    public Curso getCursoRubrica() {
        return cursoRubrica;
    }

    public void setCursoRubrica(Curso cursoRubrica) {
        this.cursoRubrica = cursoRubrica;
    }

    public Competencia getCompetenciaRubrica() {
        return competenciaRubrica;
    }

    public void setCompetenciaRubrica(Competencia competenciaRubrica) {
        this.competenciaRubrica = competenciaRubrica;
    }

    public Set<Rubrica> getRubricas() {
        return rubricas;
    }

    public void setRubricas(Set<Rubrica> rubricas) {
        this.rubricas = rubricas;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getCodRubrica() { return this.rubricaPK.getCodRubrica(); }
}
