package com.joelarchila.shop.service;


import com.joelarchila.shop.entity.Venta;
import com.joelarchila.shop.repository.VentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class VentaServiceImplements implements VentaService {

    private final VentaRepository repository;

    public VentaServiceImplements(VentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Venta> getAllVentas() {
        return repository.findAll();
    }

    @Override
    public Venta getVentaById(Integer id) {
        Venta venta = repository.findById(id).orElse(null);
        if (venta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada");
        }
        return venta;
    }

    @Override
    public Venta saveVenta(Venta venta) {
        return repository.save(venta);
    }

    @Override
    public Venta updateVenta(Integer id, Venta venta) {
        Venta existente = repository.findById(id).orElse(null);
        if (existente != null) {
            existente.setFechaVenta(venta.getFechaVenta());
            existente.setTotal(venta.getTotal());
            existente.setEstado(venta.getEstado());
            existente.setCliente(venta.getCliente());
            existente.setUsuario(venta.getUsuario());
            return repository.save(existente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la venta para actualizar");
        }
    }

    @Override
    public void deleteVenta(Integer id) {
        Venta venta = repository.findById(id).orElse(null);
        if (venta != null) {
            repository.delete(venta);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la venta para eliminar");
        }
    }
}
