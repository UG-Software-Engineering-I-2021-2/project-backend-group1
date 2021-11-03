package data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Curso")
public class Curso {
    @Id
    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    @Column(name = "nombre", nullable = false, columnDefinition = "text", unique = true)
    private String nombre;

    @Column(name = "ciclo", nullable = false)
    private Short ciclo;

    @Column(name = "semestre", nullable = false, columnDefinition = "text")
    private String semestre;

    @OneToMany(mappedBy = "cursoSeccion")
    private Set<Seccion> secciones;

    @OneToMany(mappedBy = "cursoRubrica")
    private Set<Rubrica> rubricas;

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

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Set<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(Set<Seccion> secciones) {
        this.secciones = secciones;
    }

    public Set<Rubrica> getRubricas() {
        return rubricas;
    }

    public void setRubricas(Set<Rubrica> rubricas) {
        this.rubricas = rubricas;
    }
}
