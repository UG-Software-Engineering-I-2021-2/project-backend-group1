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

    @ManyToMany
    @JoinTable(
            name = "LLevaAlumnoSeccion",
            joinColumns = @JoinColumn(
                    name = "codAlumno",
                    referencedColumnName = "codAlumno"
            ),
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "codSeccion",
                            referencedColumnName = "codSeccion",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "semestre",
                            referencedColumnName = "semestre",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "codCurso",
                            referencedColumnName = "codCurso",
                            columnDefinition = "text"
                    )
            }
    )
    @JsonIgnore
    private Set<Seccion> seccionesAlumno;

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

    public Set<Seccion> getSeccionesAlumno() {
        return seccionesAlumno;
    }

    public void setSeccionesAlumno(Set<Seccion> seccionesAlumno) {
        this.seccionesAlumno = seccionesAlumno;
    }

    public Set<Evalua> getEvalua() {
        return evalua;
    }

    public void setEvalua(Set<Evalua> evalua) {
        this.evalua = evalua;
    }
}
