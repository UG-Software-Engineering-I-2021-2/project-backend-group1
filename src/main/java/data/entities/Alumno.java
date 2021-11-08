package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

@Entity
@Table(name = "Alumno")
public class Alumno {
    @Id
    @Column(name = "codAlumno", length = COD_ALUMNO_LENGTH)
    private String codAlumno;

    @JoinColumn(name = "carreraId", referencedColumnName = "carreraId", nullable = false)
    @ManyToOne
    private Carrera carreraAlumno;

    @Column(name = "nombre", nullable = false, columnDefinition = "text")
    private String nombre;

    @OneToMany(mappedBy = "alumnoLleva")
    @JsonIgnore
    private Set<Lleva> lleva;

    @OneToMany(mappedBy = "alumnoEvalua")
    @JsonIgnore
    private Set<Evalua> evalua;

    public Alumno() {
    }

    // Getters and setters

    public String getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(String codAlumno) {
        this.codAlumno = codAlumno;
    }

    public Carrera getCarreraAlumno() {
        return carreraAlumno;
    }

    public void setCarreraAlumno(Carrera carreraAlumno) {
        this.carreraAlumno = carreraAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Lleva> getLleva() {
        return lleva;
    }

    public void setLleva(Set<Lleva> lleva) {
        this.lleva = lleva;
    }

    public Set<Evalua> getEvalua() {
        return evalua;
    }

    public void setEvalua(Set<Evalua> evalua) {
        this.evalua = evalua;
    }
}
