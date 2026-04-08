package com.joelarchila.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Ruta para la gestión de Clientes
    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }

    @GetMapping("/productos")
    public String productos(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/loginShop";
        }
        return "productos"; // Debe coincidir con productos.html
    }

    @GetMapping("/ventas")
    public String ventas(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/loginShop";
        }
        return "ventas";
    }

}
