package data.entities;

import javax.persistence.*;
import java.io.Serializable;

import static config.GlobalConstants.COD_ALUMNO_LENGTH;

class AlumnoCompositeKey implements Serializable {
    private Long id;
    private Carrera carrera;
}

@Entity
@Table(name = "Alumno")
@IdClass(AlumnoCompositeKey.class)
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alumno_id")
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Column(name = "codcurso", nullable = false, length = COD_ALUMNO_LENGTH)
    private String codalumno;

    @Column(name = "nombre", nullable = false, columnDefinition = "text")
    private String nombre;

    public Alumno() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getCodalumno() {
        return codalumno;
    }

    public void setCodalumno(String codalumno) {
        this.codalumno = codalumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
