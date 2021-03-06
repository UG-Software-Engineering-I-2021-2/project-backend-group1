package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.composite_keys.RubricaBasePK;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "tituloBase", columnDefinition = "text", nullable = false)
    private String tituloBase;

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
        //Empty constructor
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

    public Set<Rubrica> getRubricas() { return rubricas; }

    public Set<Rubrica> getRubricas(String semester) {
        Set<Rubrica> response = new HashSet<>();
        for(Rubrica rubrica : rubricas){
            if(rubrica.getSemestre().equals(semester)){
                response.add(rubrica);
            }
        }
        return response;
    }

    public Rubrica getRubrica(String semester){
        Rubrica response = null;
        for(Rubrica rubrica : rubricas){
            if(rubrica.getSemestre().equals(semester)){
                response = rubrica;
            }
        }
        return response;
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

    public String getTituloBase() {
        return tituloBase;
    }

    public void setTituloBase(String tituloBase) {
        this.tituloBase = tituloBase;
    }
}
