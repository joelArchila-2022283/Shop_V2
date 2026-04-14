package com.joelarchila.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Verifica si hay una sesión activa.
     * Es un método utilitario para no repetir código.
     */
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioLogueado") != null;
    }

    /**
     * Verifica si el usuario tiene rol de ADMIN.
     */
    private boolean isAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return "ADMIN".equals(rol);
    }

    // --- RUTAS ACCESIBLES POR AMBOS (ADMIN Y CLIENTE) ---

    @GetMapping("/productos")
    public String productos(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }
        return "productos";
    }

    @GetMapping("/clientes")
    public String clientes(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }
        return "clientes";
    }

    // --- RUTAS PROTEGIDAS: SOLO PARA ADMIN ---

    @GetMapping("/usuarios")
    public String usuarios(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }

        if (!isAdmin(session)) {
            // Si es un cliente normal, lo mandamos al index porque no tiene permiso
            return "redirect:/index";
        }

        return "usuarios";
    }

    @GetMapping("/ventas")
    public String ventas(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }

        if (!isAdmin(session)) {
            return "redirect:/index";
        }

        return "ventas";
    }

    @GetMapping("/detalle-ventas")
    public String detalleVentas(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/loginShop";
        }

        if (!isAdmin(session)) {
            return "redirect:/index";
        }

        return "detalleVentas";
    }
}