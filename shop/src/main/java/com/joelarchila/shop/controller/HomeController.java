package com.joelarchila.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

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

        if ("ADMIN".equals(rol)) {
            // Aquí podrías cargar la lista de todos los clientes para el admin
            // model.addAttribute("listaClientes", clienteService.getAll());
            return "clientes";
        } else {
            // LÓGICA PARA EL PERFIL DEL CLIENTE
            // Pasamos el nombre del usuario logueado para que la vista tenga algo que mostrar
            model.addAttribute("nombreUsuario", session.getAttribute("usuarioLogueado"));
            model.addAttribute("rolUsuario", rol);

            return "perfilCliente";
        }
    }

    @GetMapping("/ventas")
    public String ventas(HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/loginShop";

        if (esAdmin(session)) {
            return "ventas";
        }
        return "ventasCliente";
    }

    @GetMapping("/detalle-ventas")
    public String detalleVentas(HttpSession session) {
        if (!isAuthenticated(session) || !esAdmin(session)) {
            return "redirect:/index";
        }
        return "detalleVentas";
    }
}