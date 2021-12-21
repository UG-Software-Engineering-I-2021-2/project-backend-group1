package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private Set<RubricaBase> rubricas;

    public Competencia() {
        //Empty constructor
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

    public Set<RubricaBase> getRubricas() {
        return rubricas;
    }

    public void setRubricas(Set<RubricaBase> rubricas) {
        this.rubricas = rubricas;
    }
}
