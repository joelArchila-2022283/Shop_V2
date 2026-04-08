package com.joelarchila.shop.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Clientes")
public class Cliente {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "dpi_cliente")
        private Integer dpiCliente;

        @Column(name = "nombre_cliente")
        @NotBlank(message = "El nombre es un campo obligatorio")
        private String nombreCliente;

        @Column(name = "apellido_cliente")
        @NotBlank(message = "El apellido es un campo obligatorio")
        private String apellidoCliente;

        @Column(name = "direccion")
        private String direccion;

        @Column(name = "estado")
        @NotNull(message = "El estado es obligatorio")
        private Integer estado;

        public Integer getDpiCliente() { return dpiCliente; }
        public void setDpiCliente(Integer dpiCliente) { this.dpiCliente = dpiCliente; }

        public String getNombreCliente() { return nombreCliente; }
        public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

        public String getApellidoCliente() { return apellidoCliente; }
        public void setApellidoCliente(String apellidoCliente) { this.apellidoCliente = apellidoCliente; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }

        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }
}

