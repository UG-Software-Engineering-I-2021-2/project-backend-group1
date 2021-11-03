package data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Embeddable
class SeccionPK implements Serializable {
    @Column(name = "codSeccion", columnDefinition = "text")
    private String codSeccion;

    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeccionPK seccionPK = (SeccionPK) o;
        return codSeccion.equals(seccionPK.codSeccion) && codCurso.equals(seccionPK.codCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codSeccion, codCurso);
    }
}

@Entity
@Table(name = "Seccion")
public class Seccion {
    @EmbeddedId
    private SeccionPK seccionPK;

    @MapsId("codCurso")
    @JoinColumn(name = "codCurso", referencedColumnName = "codCurso", columnDefinition = "text")
    @ManyToOne
    private Curso cursoSeccion;

    @ManyToMany(mappedBy = "seccionesDocente")
    private Set<User> docentes;

    @ManyToMany(mappedBy = "seccionesAlumno")
    private Set<Alumno> alumnos;

    public Seccion() {
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

    public Set<User> getDocentes() {
        return docentes;
    }

    public void setDocentes(Set<User> docentes) {
        this.docentes = docentes;
    }

    public Set<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
