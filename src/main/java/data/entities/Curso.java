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

    @OneToMany(mappedBy = "cursoSeccion")
    @JsonIgnore
    private Set<Seccion> secciones;

    @OneToMany(mappedBy = "cursoRubrica")
    @JsonIgnore
    private Set<RubricaBase> rubricasBase;

    @ManyToMany(mappedBy = "cursosPertenece")
    @JsonIgnore
    private Set<Carrera> carrerasPertenece;

    public Curso() {
        //Empty constructor
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

    public Set<Carrera> getCarrerasPertenece() {
        return carrerasPertenece;
    }

    public void setCarrerasPertenece(Set<Carrera> carrerasPertenece) {
        this.carrerasPertenece = carrerasPertenece;
    }
}
