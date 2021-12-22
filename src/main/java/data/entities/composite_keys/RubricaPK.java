package data.entities.composite_keys;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RubricaPK implements Serializable {
    @Column(name = "semestre", columnDefinition = "text")
    private String semestre;

    private RubricaBasePK rubricaBasePK;

    // Getters and setters

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public RubricaBasePK getRubricaBasePK() {
        return rubricaBasePK;
    }

    public void setRubricaBasePK(RubricaBasePK rubricaBasePK) {
        this.rubricaBasePK = rubricaBasePK;
    }
}
