package data.entities.composite_keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

@Embeddable
public class EvaluaPK implements Serializable {
    @Column(name = "codAlumno", length = COD_ALUMNO_LENGTH)
    private String codAlumno;

    private RubricaPK rubricaPK;

    // Getters and setters

    public String getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(String codAlumno) {
        this.codAlumno = codAlumno;
    }

    public RubricaPK getRubricaPK() {
        return rubricaPK;
    }

    public void setRubricaPK(RubricaPK rubricaPK) {
        this.rubricaPK = rubricaPK;
    }

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluaPK evaluaPK = (EvaluaPK) o;
        return codAlumno.equals(evaluaPK.codAlumno) && rubricaPK.equals(evaluaPK.rubricaPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAlumno, rubricaPK);
    }
}
