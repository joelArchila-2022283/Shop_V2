package com.joelarchila.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    private Integer codigoUsuario;

    @Column(name = "username")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @Column(name = "email")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(name = "rol")
    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    @Column(name = "estado")
    @NotNull(message = "El estado es obligatorio")
    private Integer estado;


    public Integer getCodigoUsuario() { return codigoUsuario; }
    public void setCodigoUsuario(Integer codigoUsuario) { this.codigoUsuario = codigoUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
}
