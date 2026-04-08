package com.joelarchila.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    // Redirección inicial

    @GetMapping("/")
    public String inicio() {
        return "redirect:/loginShop";
    }

    // Muestra la vista del Login

    @GetMapping("/loginShop")
    public String mostrarLogin() {
        return "loginShop";
    }

    // Procesa el formulario de Login: Verifica las credenciales y maneja la sesión del usuario.

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // Credenciales de acceso
        String userCorrecto = "admin";
        String passCorrecto = "1234";

        if (userCorrecto.equals(usuario) && passCorrecto.equals(password)) {
            // Guardamos el usuario en la sesión para proteger otras rutas
            session.setAttribute("usuarioLogueado", usuario);
            // Redirigimos a la RUTA
            return "redirect:/index";
        } else {
            // Si falla, mandamos un mensaje de error al HTML
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "loginShop";
        }
    }

    //Muestra la Home (index.html): Esta ruta está protegida. Si no hay sesión, rebota al login.

    @GetMapping("/index")
    public String mostrarIndex(HttpSession session) {
        // Validamos si el objeto existe en la sesión
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/loginShop";
        }
        // Si todo está bien
        return "index";
    }

    // Cerrar Sesión:Limpia la sesión y regresa al login.

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruye la sesión actual
        return "redirect:/loginShop";
    }
}