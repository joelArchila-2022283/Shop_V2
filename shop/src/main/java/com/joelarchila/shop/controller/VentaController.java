package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Venta;
import com.joelarchila.shop.service.VentaService;
import com.joelarchila.shop.service.ClienteService;
import com.joelarchila.shop.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return rol != null && (rol.equalsIgnoreCase("administrador") || rol.equalsIgnoreCase("ADMIN"));
    }

    // 1. LISTAR
    @GetMapping
    public String listarVentas(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/loginShop";

        if (esAdmin(session)) {
            model.addAttribute("listaVentas", ventaService.getAllVentas());
            return "ventas";
        } else {
            // Filtrar solo las ventas del usuario logueado
            String username = (String) session.getAttribute("usuarioLogueado");
            List<Venta> misVentas = ventaService.getAllVentas().stream()
                    .filter(v -> v.getUsuario() != null &&
                            v.getUsuario().getUsername().equalsIgnoreCase(username))
                    .collect(Collectors.toList());
            model.addAttribute("misVentas", misVentas);
            return "ventasCliente";
        }
    }

    // 2. CREAR — solo admin
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/ventas";
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "formVenta";
    }

    // 3. GUARDAR — solo admin
    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute("venta") Venta venta,
                               @RequestParam("clienteId") Integer clienteId,
                               @RequestParam("usuarioId") Integer usuarioId,
                               HttpSession session) {
        if (!esAdmin(session)) return "redirect:/ventas";
        venta.setCliente(clienteService.getClienteById(clienteId));
        venta.setUsuario(usuarioService.getUsuarioById(usuarioId));
        if (venta.getTotal() == null) {
            venta.setTotal(java.math.BigDecimal.ZERO);
        }
        ventaService.saveVenta(venta);
        return "redirect:/ventas";
    }

    // 4. EDITAR — solo admin
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/ventas";
        Venta venta = ventaService.getVentaById(id);
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "formVenta";
    }

    // 5. ELIMINAR — solo admin
    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/ventas";
        ventaService.deleteVenta(id);
        return "redirect:/ventas";
    }
}