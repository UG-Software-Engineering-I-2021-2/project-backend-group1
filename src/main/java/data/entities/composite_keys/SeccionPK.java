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
}
