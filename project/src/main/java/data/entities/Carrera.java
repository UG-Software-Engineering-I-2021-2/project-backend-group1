package data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrera_id")
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false, columnDefinition = "text")
    private String nombre;

    @OneToMany(mappedBy = "carrera")
    private Set<Alumno> alumnos;

    public Carrera() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
