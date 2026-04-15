package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Cliente;
import com.joelarchila.shop.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes-gestion")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // LISTAR CLIENTES
    @GetMapping
    public String listar(Model model, HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        if (session.getAttribute("usuarioLogueado") == null || !"ADMIN".equalsIgnoreCase(rol)) {
            return "redirect:/loginShop";
        }
        model.addAttribute("listaClientes", clienteService.getAllClientes());
        return "clientes";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "formCliente"; // Debes crear este HTML
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.getClienteById(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "formCliente";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.saveCliente(cliente);
        return "redirect:/clientes-gestion";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return "redirect:/clientes-gestion";
    }
}