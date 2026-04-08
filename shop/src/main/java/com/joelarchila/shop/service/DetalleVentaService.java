package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.DetalleVenta;

import java.util.List;

public interface DetalleVentaService {
    List<DetalleVenta> getAllDetalles();
    DetalleVenta getDetalleById(Integer id);
    DetalleVenta saveDetalle(DetalleVenta detalle) throws RuntimeException;
    void deleteDetalle(Integer id);
}
