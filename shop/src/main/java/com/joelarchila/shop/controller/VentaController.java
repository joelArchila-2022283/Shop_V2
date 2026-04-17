package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Venta;
import com.joelarchila.shop.service.VentaService;
import com.joelarchila.shop.service.ClienteService;
import com.joelarchila.shop.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public VentaController(VentaService ventaService, ClienteService clienteService, UsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    // 1. LEER (Listar)
    @GetMapping
    public String listarVentas(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/loginShop";

        model.addAttribute("listaVentas", ventaService.getAllVentas());
        return "ventas";
    }

    // 2. CREAR
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Venta venta = new Venta();

        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "formVenta";
    }

    // 3. GUARDAR
    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute("venta") Venta venta,
                               @RequestParam("clienteId") Integer clienteId,
                               @RequestParam("usuarioId") Integer usuarioId) {

        venta.setCliente(clienteService.getClienteById(clienteId));
        venta.setUsuario(usuarioService.getUsuarioById(usuarioId));

        if (venta.getTotal() == null) {
            venta.setTotal(java.math.BigDecimal.ZERO);
        }
        ventaService.saveVenta(venta);
        return "redirect:/ventas";
    }

    // 4. ACTUALIZAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Venta venta = ventaService.getVentaById(id);
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "formVenta";
    }

    // 5. ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Integer id) {
        ventaService.deleteVenta(id);
        return "redirect:/ventas";
    }
}