package com.joelarchila.shop.controller;

import com.joelarchila.shop.entity.Usuario;
import com.joelarchila.shop.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @PostMapping
    public ResponseEntity<Object> createUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            return new ResponseEntity<>("Error: El username es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("Error: La contraseña es obligatoria.", HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario created = usuarioService.saveUsuario(usuario);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear usuario: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        if (id <= 0) {
            return new ResponseEntity<>("Error: El ID debe ser positivo.", HttpStatus.BAD_REQUEST);
        }

        Usuario actualizado = usuarioService.updateUsuario(id, usuario);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró el usuario con ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id) {
        if (id <= 0) return new ResponseEntity<>("Error: ID inválido.", HttpStatus.BAD_REQUEST);

        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario != null) {
            usuarioService.deleteUsuario(id);
            return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró el usuario ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
