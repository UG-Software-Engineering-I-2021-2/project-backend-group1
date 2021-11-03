package data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Usuario")
public class User {
    public static enum Role {Docente, Calidad};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioId")
    private Long id;

    @Column(name = "codEmpleado", nullable = false)
    private Long codEmpleado;

    @Column(name = "rol", nullable = false, columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private Role rol;

    @Column(name = "username", unique = true, nullable = false, columnDefinition = "text")
    private String username;

    @ManyToMany
    @JoinTable(
            name = "DictaDocenteSeccion",
            joinColumns = @JoinColumn(
                    name = "usuarioId",
                    referencedColumnName = "usuarioId"
            ),
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
    private Set<Seccion> seccionesDocente;

    public User() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Long codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Seccion> getSeccionesDocente() {
        return seccionesDocente;
    }

    public void setSeccionesDocente(Set<Seccion> seccionesDocente) {
        this.seccionesDocente = seccionesDocente;
    }
}
