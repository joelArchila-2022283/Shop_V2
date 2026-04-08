package com.joelarchila.shop.controller;


import com.joelarchila.shop.entity.Producto;
import com.joelarchila.shop.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @PostMapping
    public ResponseEntity<Object> createProducto(@Valid @RequestBody Producto producto) {
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            return new ResponseEntity<>("Error: El nombre del producto es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>("Error: El precio debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
        }

        try {
            Producto created = productoService.saveProducto(producto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        if (id <= 0) return new ResponseEntity<>("ID inválido", HttpStatus.BAD_REQUEST);

        Producto actualizado = productoService.updateProducto(id, producto);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Integer id) {
        Producto p = productoService.getProductoById(id);
        if (p != null) {
            productoService.deleteProducto(id);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        }
        return new ResponseEntity<>("No encontrado", HttpStatus.NOT_FOUND);
    }
}
