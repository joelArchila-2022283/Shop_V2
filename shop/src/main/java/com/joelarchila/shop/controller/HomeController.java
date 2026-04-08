package com.joelarchila.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Ruta para la gestión de Clientes
    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }


}
