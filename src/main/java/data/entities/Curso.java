package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Curso")
public class Curso {
    @Id
    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    @Column(name = "nombre", nullable = false, columnDefinition = "text")
    private String nombre;

    @Column(name = "ciclo", nullable = false)
    private Short ciclo;

    @OneToMany(mappedBy = "cursoSeccion")
    @JsonIgnore
    private Set<Seccion> secciones;

    @OneToMany(mappedBy = "cursoRubrica")
    @JsonIgnore
    private Set<RubricaBase> rubricasBase;

    public Curso() {
    }

    // Getters and setters

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getCiclo() {
        return ciclo;
    }

    public void setCiclo(Short ciclo) {
        this.ciclo = ciclo;
    }

    public Set<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(Set<Seccion> secciones) {
        this.secciones = secciones;
    }

    public Set<RubricaBase> getRubricasBase() {
        return rubricasBase;
    }

    public void setRubricasBase(Set<RubricaBase> rubricasBase) {
        this.rubricasBase = rubricasBase;
    }
}
