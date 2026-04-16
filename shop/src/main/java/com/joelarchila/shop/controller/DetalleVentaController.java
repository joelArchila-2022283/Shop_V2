package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.DetalleVenta;
import com.joelarchila.shop.service.DetalleVentaService;
import com.joelarchila.shop.service.ProductoService;
import com.joelarchila.shop.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/detalles")
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;
    private final ProductoService productoService;
    private final VentaService ventaService;

    public DetalleVentaController(DetalleVentaService service, ProductoService pService, VentaService vService) {
        this.detalleVentaService = service;
        this.productoService = pService;
        this.ventaService = vService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<DetalleVenta> detalles = detalleVentaService.getAllDetalleVentas();

        if (buscar != null && !buscar.isEmpty()) {
            detalles = detalles.stream()
                    .filter(d ->
                            d.getProducto().getNombreProducto().toLowerCase().contains(buscar.toLowerCase()) ||
                                    d.getVenta().getCodigoVenta().toString().contains(buscar) ||
                                    d.getId().toString().contains(buscar)
                    )
                    .collect(Collectors.toList());
        }

        model.addAttribute("detalles", detalles);
        model.addAttribute("detalleObj", new DetalleVenta());
        model.addAttribute("productos", productoService.getAllProductos());
        model.addAttribute("ventas", ventaService.getAllVentas());
        model.addAttribute("buscar", buscar);
        return "Detalles";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(@RequestParam(required = false) Integer idVenta, Model model) {
        model.addAttribute("detalle", new DetalleVenta());
        model.addAttribute("idVenta", idVenta);
        model.addAttribute("listaProductos", productoService.getAllProductos());
        return "formDetalleVenta";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute DetalleVenta detalleVenta,
                          @RequestParam("ventaId") Integer ventaId,
                          @RequestParam("productoId") Integer productoId) {

        detalleVenta.setVenta(ventaService.getVentaById(ventaId));
        detalleVenta.setProducto(productoService.getProductoById(productoId));

        // Cálculo del subtotal antes de guardar
        if (detalleVenta.getCantidad() != null && detalleVenta.getPrecio() != null) {
            BigDecimal cantidad = new BigDecimal(detalleVenta.getCantidad());
            detalleVenta.setSubtotal(detalleVenta.getPrecio().multiply(cantidad));
        }

        detalleVentaService.saveDetalleVenta(detalleVenta);
        return "redirect:/detalles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        detalleVentaService.deleteDetalleVenta(id);
        return "redirect:/detalles";
    }

}