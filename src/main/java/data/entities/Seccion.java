package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.composite_keys.SeccionPK;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Seccion")
public class Seccion {
    @EmbeddedId
    private SeccionPK seccionPK;

    @MapsId("codCurso")
    @JoinColumn(name = "codCurso", referencedColumnName = "codCurso", columnDefinition = "text")
    @ManyToOne
    private Curso cursoSeccion;

    @ManyToMany(mappedBy = "seccionesDicta")
    @JsonIgnore
    private Set<User> docentesDicta;

    @ManyToMany(mappedBy = "seccionesCoordina")
    @JsonIgnore
    private Set<User> docentesCoordina;

    @OneToMany(mappedBy = "seccionLleva")
    @JsonIgnore
    private Set<Lleva> lleva;

    @ManyToMany(mappedBy = "seccionesRubricFinish")
    @JsonIgnore
    private Set<Rubrica> rubricasRubricFinish;

    public Seccion() {
        //Empty constructor
    }

    // Getters and setters

    public SeccionPK getSeccionPK() {
        return seccionPK;
    }

    public void setSeccionPK(SeccionPK seccionPK) {
        this.seccionPK = seccionPK;
    }

    public Curso getCursoSeccion() {
        return cursoSeccion;
    }

    public void setCursoSeccion(Curso cursoSeccion) {
        this.cursoSeccion = cursoSeccion;
    }

    public Set<User> getDocentesDicta() {
        return docentesDicta;
    }

    public void setDocentesDicta(Set<User> docentesDicta) {
        this.docentesDicta = docentesDicta;
    }

    public Set<User> getDocentesCoordina() {
        return docentesCoordina;
    }

    public void setDocentesCoordina(Set<User> docentesCoordina) {
        this.docentesCoordina = docentesCoordina;
    }

    public Set<Lleva> getLleva() {
        return lleva;
    }

    public void setLleva(Set<Lleva> lleva) {
        this.lleva = lleva;
    }

    public String getSemestre() { return seccionPK.getSemestre(); }

    public Set<Rubrica> getRubricasRubricFinish() {
        return rubricasRubricFinish;
    }

    public void setRubricasRubricFinish(Set<Rubrica> rubricasRubricFinish) {
        this.rubricasRubricFinish = rubricasRubricFinish;
    }
}
