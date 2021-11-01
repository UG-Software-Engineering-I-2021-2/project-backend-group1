package data.entities;

import javax.persistence.*;

@Entity
@Table(name = "Usuario")
public class User {
    public static enum Role {Docente, Calidad};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "codempleado", nullable = false)
    private Long codEmpleado;

    @Column(name = "rol", nullable = false, columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private Role rol;

    @Column(name = "username", unique = true, nullable = false, columnDefinition = "text")
    private String username;

    public User() {}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }
}
