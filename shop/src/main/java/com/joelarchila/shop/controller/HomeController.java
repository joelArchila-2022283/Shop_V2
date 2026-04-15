package com.joelarchila.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // --- METODOS DE SEGURIDAD  ---

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioLogueado") != null;
    }

    private boolean isAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        // Usamos equalsIgnoreCase por seguridad
        return "ADMIN".equalsIgnoreCase(rol);
    }

    // --- RUTAS ADAPTATIVAS (Admin y Cliente) ---

    @GetMapping("/productos")
    public String productos(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }

        // Aqui es donde decide que vista devolver
        if (isAdmin(session)) {
            return "productos";        // (Admin)
        } else {
            return "productosCliente"; // (Cliente)
        }
    }

    @GetMapping("/clientes")
    public String clientes(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }

        if (isAdmin(session)) {
            return "clientes";        // (Admin)
        } else {
            return "perfilCliente";   // (Cliente)
        }
    }

    @GetMapping("/ventas")
    public String ventas(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }

        if (isAdmin(session)) {
            return "ventas";        // (Admin)
        } else {
            return "ventasCliente"; // (Cliente)
        }
    }

    // --- RUTAS EXCLUSIVAS (Admin) ---

    @GetMapping("/usuarios")
    public String usuarios(HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/loginShop";

        // Si un cliente intenta escribir /usuarios en la URL, lo manda al index
        if (!isAdmin(session)) return "redirect:/index";

        return "usuarios";
    }

    @GetMapping("/detalle-ventas")
    public String detalleVentas(HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/loginShop";
        if (!isAdmin(session)) return "redirect:/index";

        return "detalleVentas";
    }
}