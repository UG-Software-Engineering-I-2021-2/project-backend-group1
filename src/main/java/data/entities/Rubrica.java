package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import config.enums.State;
import data.entities.composite_keys.RubricaPK;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Rubrica")
public class Rubrica {
    @EmbeddedId
    private RubricaPK rubricaPK;

    @Column(name = "dimensiones", nullable = false)
    private Short dimensiones;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "estado", columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    private State estado;

    @Column(name = "descriptores", columnDefinition = "text", nullable = false)
    private String descriptores;

    @Column(name = "actividad", columnDefinition = "text", nullable = false)
    private String actividad;

    @Column(name = "titulo", columnDefinition = "text", nullable = false)
    private String titulo;

    @JoinColumns(value = {
            @JoinColumn(
                    name = "codRubrica",
                    referencedColumnName = "codRubrica",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "codCurso",
                    referencedColumnName = "codCurso",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "codCompetencia",
                    referencedColumnName = "codCompetencia",
                    insertable = false,
                    updatable = false
            )
    })
    @ManyToOne
    private RubricaBase rubricaBase;

    @OneToMany(mappedBy = "rubricaEvalua")
    @JsonIgnore
    private Set<Evalua> evalua;

    @ManyToMany
    @JoinTable(
            name = "RubricFinish",
            joinColumns = {
                    @JoinColumn(
                            name = "semestreRubrica",
                            referencedColumnName = "semestre",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "codRubrica",
                            referencedColumnName = "codRubrica",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "codCursoRubrica",
                            referencedColumnName = "codCurso",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "codCompetencia",
                            referencedColumnName = "codCompetencia",
                            columnDefinition = "text"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "codSeccion",
                            referencedColumnName = "codSeccion",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "semestreSeccion",
                            referencedColumnName = "semestre",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "codCursoSeccion",
                            referencedColumnName = "codCurso",
                            columnDefinition = "text"
                    )
            }
    )
    @JsonIgnore
    private Set<Seccion> seccionesRubricFinish;

    // Getters and setters

    public RubricaPK getRubricaPK() {
        return rubricaPK;
    }

    public void setRubricaPK(RubricaPK rubricaPK) {
        this.rubricaPK = rubricaPK;
    }

    public Short getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Short dimensiones) {
        this.dimensiones = dimensiones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public State getEstado() {
        return estado;
    }

    public void setEstado(State  estado) {
        this.estado = estado;
    }

    public String getDescriptores() {
        return descriptores;
    }

    public void setDescriptores(String descriptores) {
        this.descriptores = descriptores;
    }

    public RubricaBase getRubricaBase() {
        return rubricaBase;
    }

    public void setRubricaBase(RubricaBase rubricaBase) {
        this.rubricaBase = rubricaBase;
    }

    public Set<Evalua> getEvalua() {
        return evalua;
    }

    public void setEvalua(Set<Evalua> evalua) {
        this.evalua = evalua;
    }

    public String getSemestre() { return this.rubricaPK.getSemestre(); }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Seccion> getSeccionesRubricFinish() {
        return seccionesRubricFinish;
    }

    public void setSeccionesRubricFinish(Set<Seccion> seccionesRubricFinish) {
        this.seccionesRubricFinish = seccionesRubricFinish;
    }
}
