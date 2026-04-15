package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Usuario;
import com.joelarchila.shop.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private LoginService loginService; // Inyectamos tu lógica de DB

    @GetMapping("/")
    public String inicio() {
        return "redirect:/loginShop";
    }

    @GetMapping("/loginShop")
    public String mostrarLogin() {
        return "loginShop";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // Buscamos en la base de datos usando el service
        Usuario userFound = loginService.login(usuario, password);

        if (userFound != null) {
            // Guardamos el objeto completo o al menos el username y el rol
            session.setAttribute("usuarioLogueado", userFound.getUsername());
            session.setAttribute("rol", userFound.getRol()); 

            return "redirect:/index";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "loginShop";
        }
    }

    @GetMapping("/index")
    public String mostrarIndex(HttpSession session) {
        String username = (String) session.getAttribute("usuarioLogueado");
        String rol = (String) session.getAttribute("rol");

        // Si no hay sesión, al login
        if (username == null) {
            return "redirect:/loginShop";
        }

        // Decisión de Vista
        if ("ADMIN".equals(rol)) {
            return "index"; // Admin
        } else {
            return "indexCliente"; // Cliente
        }
    }

    @GetMapping("/registroShop")
    public String mostrarRegistro() {
        return "registroShop";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String usuario,
                                   @RequestParam String password,
                                   @RequestParam String email,
                                   Model model) {

        // Usamos el servicio que ya esta configurado
        Usuario nuevo = loginService.registrar(usuario, password);

        if (nuevo != null) {
            model.addAttribute("exito", "¡Usuario creado! Ya puedes iniciar sesión");
            return "loginShop";
        } else {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "registroShop";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginShop";
    }
}