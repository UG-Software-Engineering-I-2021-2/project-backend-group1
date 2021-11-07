package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Embeddable
class RubricaPK implements Serializable {
    @Column(name = "codRubrica", columnDefinition = "text")
    private String codRubrica;

    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    @Column(name = "codCompetencia", columnDefinition = "text")
    private String codCompetencia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubricaPK rubricaPK = (RubricaPK) o;
        return codRubrica.equals(rubricaPK.codRubrica) && codCurso.equals(rubricaPK.codCurso) && codCompetencia.equals(rubricaPK.codCompetencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codRubrica, codCurso, codCompetencia);
    }
}

@Entity
@Table(name = "Rubrica")
public class Rubrica {
    @EmbeddedId
    private RubricaPK rubricaPK;

    @Column(name = "actividad", columnDefinition = "text", nullable = false)
    private String actividad;

    @Column(name = "semana", nullable = false)
    private Short semana;

    @Column(name = "dimensiones", nullable = false)
    private Short dimensiones;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "nivel", nullable = false)
    private Short nivel;

    @Column(name = "estado", columnDefinition = "text", nullable = false)
    private String estado;

    @Column(name = "criterioDesempeno", columnDefinition = "text", nullable = false)
    private String criterioDesempeno;

    @Column(name = "descriptores", columnDefinition = "text", nullable = false)
    private String descriptores;

    @MapsId("codCurso")
    @JoinColumn(name = "codCurso", referencedColumnName = "codCurso", columnDefinition = "text")
    @ManyToOne
    private Curso cursoRubrica;

    @MapsId("codCompetencia")
    @JoinColumn(name = "codCompetencia", referencedColumnName = "codCompetencia", columnDefinition = "text")
    @ManyToOne
    private Competencia competenciaRubrica;

    @OneToMany(mappedBy = "rubricaEvalua")
    @JsonIgnore
    private Set<Evalua> evalua;

    public Rubrica() {
    }

    // Getters and setters

    public RubricaPK getRubricaPK() {
        return rubricaPK;
    }

    public void setRubricaPK(RubricaPK rubricaPK) {
        this.rubricaPK = rubricaPK;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Short getSemana() {
        return semana;
    }

    public void setSemana(Short semana) {
        this.semana = semana;
    }

    public Short getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Short dimensiones) {
        this.dimensiones = dimensiones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Short getNivel() {
        return nivel;
    }

    public void setNivel(Short nivel) {
        this.nivel = nivel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCriterioDesempeno() {
        return criterioDesempeno;
    }

    public void setCriterioDesempeno(String criterioDesempeno) {
        this.criterioDesempeno = criterioDesempeno;
    }

    public String getDescriptores() {
        return descriptores;
    }

    public void setDescriptores(String descriptores) {
        this.descriptores = descriptores;
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

    public Set<Evalua> getEvalua() {
        return evalua;
    }

    public void setEvalua(Set<Evalua> evalua) {
        this.evalua = evalua;
    }
}
