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

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubricaBasePK that = (RubricaBasePK) o;
        return codRubrica.equals(that.codRubrica) && codCurso.equals(that.codCurso) && codCompetencia.equals(that.codCompetencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codRubrica, codCurso, codCompetencia);
    }
}