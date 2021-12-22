package data.entities.composite_keys;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RubricaBasePK implements Serializable {
    @Column(name = "codRubrica", columnDefinition = "text")
    private String codRubrica;

    @Column(name = "codCurso", columnDefinition = "text")
    private String codCurso;

    @Column(name = "codCompetencia", columnDefinition = "text")
    private String codCompetencia;

    // Getters and setters

    public String getCodRubrica() {
        return codRubrica;
    }

    public void setCodRubrica(String codRubrica) {
        this.codRubrica = codRubrica;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public String getCodCompetencia() {
        return codCompetencia;
    }

    public void setCodCompetencia(String codCompetencia) {
        this.codCompetencia = codCompetencia;
    }
}