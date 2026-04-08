package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.DetalleVenta;
import com.joelarchila.shop.repository.DetalleVentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class DetalleVentaServiceImplements implements DetalleVentaService {

    private final DetalleVentaRepository repository;

    public DetalleVentaServiceImplements(DetalleVentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DetalleVenta> getAllDetalles() {
        return repository.findAll();
    }

    @Override
    public DetalleVenta getDetalleById(Integer id) {
        DetalleVenta detalle = repository.findById(id).orElse(null);
        if (detalle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado");
        }
        return detalle;
    }

    @Override
    public DetalleVenta saveDetalle(DetalleVenta detalle) {
        return repository.save(detalle);
    }

    @Override
    public void deleteDetalle(Integer id) {
        DetalleVenta detalle = repository.findById(id).orElse(null);
        if (detalle != null) {
            repository.delete(detalle);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el detalle para eliminar");
        }
    }
}
