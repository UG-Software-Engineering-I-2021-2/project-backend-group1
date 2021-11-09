package data.entities.composite_keys;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeccionPK implements Serializable {
    @Column(name = "codSeccion", columnDefinition = "text")
    private String codSeccion;

    @Column(name = "semestre", columnDefinition = "text")
    private String semestre;

    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    // Getters and setters

    public String getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(String codSeccion) {
        this.codSeccion = codSeccion;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeccionPK seccionPK = (SeccionPK) o;
        return codSeccion.equals(seccionPK.codSeccion) && semestre.equals(seccionPK.semestre) && codCurso.equals(seccionPK.codCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codSeccion, semestre, codCurso);
    }
}
