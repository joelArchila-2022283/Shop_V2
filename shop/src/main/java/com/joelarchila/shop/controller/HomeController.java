package com.joelarchila.shop.controller;

import com.joelarchila.shop.service.DetalleVentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final DetalleVentaService detalleVentaService;

    // Constructor
    public HomeController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioLogueado") != null;
    }

    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        if (rol == null) return false;
        return rol.equalsIgnoreCase("administrador") || rol.equalsIgnoreCase("ADMIN");
    }

    @GetMapping("/clientes")
    public String clientes(HttpSession session, Model model) {
        if (!isAuthenticated(session)) return "redirect:/loginShop";

        String rol = (String) session.getAttribute("rol");

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return "redirect:/clientes-gestion"; // Redireccionamos al CRUD
        } else {
            model.addAttribute("nombreUsuario", session.getAttribute("usuarioLogueado"));
            model.addAttribute("rolUsuario", rol);
            return "perfilCliente";
        }
    }
}