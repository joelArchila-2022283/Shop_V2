package com.joelarchila.shop.controller;


import com.joelarchila.shop.entity.DetalleVenta;
import com.joelarchila.shop.service.DetalleVentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    public DetalleVentaController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    // Listar todos los detalles
    @GetMapping
    public List<DetalleVenta> getAllDetalles() {
        return detalleVentaService.getAllDetalles();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetalleById(@PathVariable Integer id) {
        if (id <= 0) {
            return new ResponseEntity<>("Error: ID inválido.", HttpStatus.BAD_REQUEST);
        }
        DetalleVenta detalle = detalleVentaService.getDetalleById(id);
        if (detalle != null) {
            return new ResponseEntity<>(detalle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Detalle no encontrado con el ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // Crear un detalle
    @PostMapping
    public ResponseEntity<Object> createDetalle(@Valid @RequestBody DetalleVenta detalle) {

        // Validar que la cantidad sea positiva
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            return new ResponseEntity<>("Error: La cantidad debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
        }

        // Validar que el precio unitario sea válido
        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>("Error: El precio unitario debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
        }

        // Validar que tenga un producto y una venta asociada
        if (detalle.getProducto() == null || detalle.getVenta() == null) {
            return new ResponseEntity<>("Error: El producto y la venta son obligatorios.", HttpStatus.BAD_REQUEST);
        }

        try {
            DetalleVenta created = detalleVentaService.saveDetalle(detalle);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar el detalle: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar un detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalle(@PathVariable Integer id) {
        if (id <= 0) {
            return new ResponseEntity<>("Error: ID inválido.", HttpStatus.BAD_REQUEST);
        }

        DetalleVenta detalle = detalleVentaService.getDetalleById(id);
        if (detalle != null) {
            detalleVentaService.deleteDetalle(id);
            return new ResponseEntity<>("El detalle de venta ha sido eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró el detalle con el ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
