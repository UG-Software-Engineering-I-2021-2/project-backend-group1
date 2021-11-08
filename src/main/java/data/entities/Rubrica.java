package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Embeddable
class RubricaPK implements Serializable {
    @Column(name = "semestre", columnDefinition = "text")
    private String semestre;

    private RubricaBasePK rubricaBasePK;

    // Getters and setters

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public RubricaBasePK getRubricaBasePK() {
        return rubricaBasePK;
    }

    public void setRubricaBasePK(RubricaBasePK rubricaBasePK) {
        this.rubricaBasePK = rubricaBasePK;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubricaPK rubricaPK = (RubricaPK) o;
        return semestre.equals(rubricaPK.semestre) && rubricaBasePK.equals(rubricaPK.rubricaBasePK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semestre, rubricaBasePK);
    }
}

@Entity
@Table(name = "Rubrica")
public class Rubrica {
    @EmbeddedId
    private RubricaPK rubricaPK;

    @Column(name = "dimensiones", nullable = false)
    private Short dimensiones;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "estado", columnDefinition = "text", nullable = false)
    private String estado;

    @Column(name = "descriptores", columnDefinition = "text", nullable = false)
    private String descriptores;

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
    private RubricaBase rubricaBase;

    @OneToMany(mappedBy = "rubricaEvalua")
    @JsonIgnore
    private Set<Evalua> evalua;

    // Getters and setters

    public RubricaPK getRubricaPK() {
        return rubricaPK;
    }

    public void setRubricaPK(RubricaPK rubricaPK) {
        this.rubricaPK = rubricaPK;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescriptores() {
        return descriptores;
    }

    public void setDescriptores(String descriptores) {
        this.descriptores = descriptores;
    }

    public RubricaBase getRubricaBase() {
        return rubricaBase;
    }

    public void setRubricaBase(RubricaBase rubricaBase) {
        this.rubricaBase = rubricaBase;
    }

    public Set<Evalua> getEvalua() {
        return evalua;
    }

    public void setEvalua(Set<Evalua> evalua) {
        this.evalua = evalua;
    }
}
