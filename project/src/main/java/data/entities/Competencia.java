package data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Competencia")
public class Competencia {
    @Id
    @Column(name = "codCompetencia", columnDefinition = "text")
    private String codCompetencia;

    @JoinColumn(name = "carreraId", referencedColumnName = "carreraId", nullable = false)
    @ManyToOne
    private Carrera carreraCompetencia;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    @OneToMany(mappedBy = "competenciaRubrica")
    private Set<Rubrica> rubricas;

    public Competencia() {
    }

    // Getters and setters

    public String getCodCompetencia() {
        return codCompetencia;
    }

    public void setCodCompetencia(String codCompetencia) {
        this.codCompetencia = codCompetencia;
    }

    public Carrera getCarreraCompetencia() {
        return carreraCompetencia;
    }

    public void setCarreraCompetencia(Carrera carreraCompetencia) {
        this.carreraCompetencia = carreraCompetencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Rubrica> getRubricas() {
        return rubricas;
    }

    public void setRubricas(Set<Rubrica> rubricas) {
        this.rubricas = rubricas;
    }
}
