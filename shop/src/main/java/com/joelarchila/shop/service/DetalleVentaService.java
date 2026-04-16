package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.DetalleVenta;

import java.util.List;

public interface DetalleVentaService {
    List<DetalleVenta> getAllDetalleVentas();
    DetalleVenta getDetalleVentaById(Integer id);
    DetalleVenta saveDetalleVenta(DetalleVenta detalleVenta);
    void deleteDetalleVenta(Integer id);
}
