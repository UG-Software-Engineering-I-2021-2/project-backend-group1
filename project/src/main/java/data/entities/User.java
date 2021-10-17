package data.entities;

import javax.persistence.*;

@Entity
@Table(name = "GEN_EMPLEADO")
public class User {
    @Id
    @Column(name = "CODEMPLEADO")
    private Long codEmpleado;

    @Column(name = "IDEMPLEADO", length = 10)
    private String idEmpleado;

    public User() {}
}
