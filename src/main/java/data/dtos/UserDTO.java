package data.dtos;

import data.entities.User;

public class UserDTO {
    Long codEmpleado;
    String username;
    User.Role rol;

    public UserDTO() {}

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

    public User.Role getRol() {
        return rol;
    }

    public void setRol(User.Role rol) {
        this.rol = rol;
    }
}

