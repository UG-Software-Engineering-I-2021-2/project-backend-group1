package data.entities;

import javax.persistence.*;
import static config.GlobalConstants.DB_CHAR_LENGTH;

@Entity
@Table(name = "users")
public class User {
    public static enum Role {Docente, Calidad};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codempleado")
    private Long codEmpleado;

    @Column(name = "username", length = DB_CHAR_LENGTH, unique = true)
    private String username;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private Role rol;

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
