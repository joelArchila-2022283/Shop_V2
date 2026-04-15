package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Usuario;
import com.joelarchila.shop.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return "ADMIN".equalsIgnoreCase(rol);
    }

    // LISTAR
    @GetMapping
    public String getAllUsuarios(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null || !esAdmin(session)) {
            return "redirect:/loginShop";
        }
        List<Usuario> lista = usuarioService.getAllUsuarios();
        model.addAttribute("listaUsuarios", lista);
        return "usuarios";
    }

    // AGREGAR
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/usuarios";

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEstado(1);
        model.addAttribute("usuario", nuevoUsuario);
        model.addAttribute("titulo", "Nuevo Usuario");
        return "formUsuario";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/usuarios";

        Usuario usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar Usuario");
        return "formUsuario";
    }

    // GUARDAR / ACTUALIZAR
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/usuarios";

        usuarioService.saveUsuario(usuario);
        return "redirect:/usuarios";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String deleteUsuario(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/usuarios";

        usuarioService.deleteUsuario(id);
        return "redirect:/usuarios";
    }
}