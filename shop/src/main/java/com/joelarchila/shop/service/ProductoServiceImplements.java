package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Producto;
import com.joelarchila.shop.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProductoServiceImplements implements ProductoService {

    private final ProductoRepository repository;

    public ProductoServiceImplements(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Producto> getAllProductos() {
        return repository.findAll();
    }

    @Override
    public Producto getProductoById(Integer id) {
        Producto producto = repository.findById(id).orElse(null);
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        return producto;
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return repository.save(producto);
    }

    @Override
    public Producto updateProducto(Integer id, Producto producto) {
        Producto existente = repository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombreProducto(producto.getNombreProducto());
            existente.setPrecio(producto.getPrecio());
            existente.setStock(producto.getStock());
            existente.setEstado(producto.getEstado());
            return repository.save(existente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto para actualizar");
        }
    }

    @Override
    public void deleteProducto(Integer id) {
        Producto producto = repository.findById(id).orElse(null);
        if (producto != null) {
            repository.delete(producto);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto para eliminar");
        }
    }
}
