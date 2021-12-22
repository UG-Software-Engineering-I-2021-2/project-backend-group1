package data.entities.composite_keys;

import javax.persistence.*;
import java.io.Serializable;

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
}