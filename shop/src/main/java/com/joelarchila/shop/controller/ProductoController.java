package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Producto;
import com.joelarchila.shop.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return rol != null && (rol.equalsIgnoreCase("administrador") || rol.equalsIgnoreCase("ADMIN"));
    }

    @GetMapping
    public String getAllProductos(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/loginShop";
        }

        List<Producto> productos = productoService.getAllProductos();
        model.addAttribute("listaProductos", productos);

        if (esAdmin(session)) {
            return "productos"; // Vista de Admin
        } else {
            return "productosCliente"; // Vista de Cliente
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/productos";
        model.addAttribute("producto", new Producto());
        return "formProducto";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/productos";
        Producto producto = productoService.getProductoById(id);
        model.addAttribute("producto", producto);
        return "formProducto";
    }

    @PostMapping("/guardar")
    public String saveProducto(@ModelAttribute Producto producto, HttpSession session) {
        if (!esAdmin(session)) {
            return "redirect:/productos";
        }
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteProducto(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) {
            return "redirect:/productos";
        }
        try {
            productoService.deleteProducto(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return "redirect:/productos";
    }
}