package data.entities.composite_keys;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

@Embeddable
public class LlevaPK implements Serializable {
    @Column(name = "codAlumno", length = COD_ALUMNO_LENGTH)
    private String codAlumno;

    private SeccionPK seccionPK;

    // Getters and setters

    public String getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(String codAlumno) {
        this.codAlumno = codAlumno;
    }

    public SeccionPK getSeccionPK() {
        return seccionPK;
    }

    public void setSeccionPK(SeccionPK seccionPK) {
        this.seccionPK = seccionPK;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LlevaPK llevaPK = (LlevaPK) o;
        return codAlumno.equals(llevaPK.codAlumno) && seccionPK.equals(llevaPK.seccionPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAlumno, seccionPK);
    }
}