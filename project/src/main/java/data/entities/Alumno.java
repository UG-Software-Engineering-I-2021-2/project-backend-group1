package data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

@Embeddable
class AlumnoPK implements Serializable {
    @Column(name = "codAlumno", length = COD_ALUMNO_LENGTH)
    private String codAlumno;

    @Column(name = "carreraId")
    private Long carreraId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlumnoPK alumnoPK = (AlumnoPK) o;
        return codAlumno.equals(alumnoPK.codAlumno) && carreraId.equals(alumnoPK.carreraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAlumno, carreraId);
    }
}

@Entity
@Table(name = "Alumno")
public class Alumno {
    @EmbeddedId
    private AlumnoPK alumnoPK;

    @MapsId("carreraId")
    @JoinColumn(name = "carreraId", referencedColumnName = "carreraId")
    @ManyToOne
    private Carrera carreraAlumno;

    @Column(name = "nombre", nullable = false, columnDefinition = "text")
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "LLevaAlumnoSeccion",
            joinColumns = {
                    @JoinColumn(
                            name = "codAlumno",
                            referencedColumnName = "codAlumno"
                    ),
                    @JoinColumn(
                            name = "carreraId",
                            referencedColumnName = "carreraId"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "codSeccion",
                            referencedColumnName = "codSeccion",
                            columnDefinition = "text"
                    ),
                    @JoinColumn(
                            name = "codCurso",
                            referencedColumnName = "codCurso",
                            columnDefinition = "text"
                    )
            }
    )
    private Set<Seccion> seccionesAlumno;

    @OneToMany(mappedBy = "alumnoEvalua")
    private Set<Evalua> evalua;

    public Alumno() {
    }

    // Getters and setters

    public AlumnoPK getAlumnoPK() {
        return alumnoPK;
    }

    public void setAlumnoPK(AlumnoPK alumnoPK) {
        this.alumnoPK = alumnoPK;
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
