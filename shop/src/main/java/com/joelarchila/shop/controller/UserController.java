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

        // BUSCAMOS EN LA BASE DE DATOS USANDO EL SERVICIO
        Usuario userFound = loginService.login(usuario, password);

        if (userFound != null) {
            // Guardamos el OBJETO completo o al menos el USERNAME y el ROL
            session.setAttribute("usuarioLogueado", userFound.getUsername());
            session.setAttribute("rol", userFound.getRol()); // ¡IMPORTANTE PARA LA DISTINCIÓN!

            return "redirect:/index";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "loginShop";
        }
    }

    @GetMapping("/index")
    public String mostrarIndex(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/loginShop";
        }
        return "index";
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

        // Usamos el servicio que ya tienes configurado
        Usuario nuevo = loginService.registrar(usuario, password);

        if (nuevo != null) {
            model.addAttribute("exito", "¡Usuario creado! Ya puedes iniciar sesión");
            return "loginShop"; // Lo mandamos al login
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