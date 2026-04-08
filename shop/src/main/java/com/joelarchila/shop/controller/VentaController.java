package com.joelarchila.shop.controller;


import com.joelarchila.shop.entity.Venta;
import com.joelarchila.shop.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> getAll() {
        return ventaService.getAllVentas();
    }

    @PostMapping
    public ResponseEntity<Object> createVenta(@RequestBody Venta venta) {
        if (venta.getCliente() == null || venta.getUsuario() == null) {
            return new ResponseEntity<>("Error: Cliente y Usuario son obligatorios.", HttpStatus.BAD_REQUEST);
        }
        try {
            Venta nueva = ventaService.saveVenta(venta);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
