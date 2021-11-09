package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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
    private Set<Seccion> seccionesDicta;

    @ManyToMany
    @JoinTable(
            name = "CoordinaDocenteSeccion",
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
    private Set<Seccion> seccionesCoordina;

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

    public Set<Seccion> getSeccionesDicta() {
        return seccionesDicta;
    }

    public Set<Seccion> getSeccionesDicta(String semestre) {
        Set<Seccion> result = new HashSet<>();
        for(Seccion seccion : seccionesDicta){
            if(seccion.getSemestre().equals(semestre)){
                result.add(seccion);
            }
        }
        return result;
    }

    public void setSeccionesDicta(Set<Seccion> seccionesDicta) {
        this.seccionesDicta = seccionesDicta;
    }
}
