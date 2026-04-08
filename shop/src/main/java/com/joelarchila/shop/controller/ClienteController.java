package com.joelarchila.shop.controller;


import com.joelarchila.shop.entity.Cliente;
import com.joelarchila.shop.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @PostMapping
    public ResponseEntity<Object> createCliente(@Valid @RequestBody Cliente cliente) {
        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty()) {
            return new ResponseEntity<>("Error: El nombre es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        try {
            Cliente created = clienteService.saveCliente(cliente);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        if (id <= 0) return new ResponseEntity<>("Error: ID inválido.", HttpStatus.BAD_REQUEST);

        Cliente actualizado = clienteService.updateCliente(id, cliente);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cliente no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Integer id) {
        if (id <= 0) return new ResponseEntity<>("Error: ID inválido.", HttpStatus.BAD_REQUEST);

        Cliente cliente = clienteService.getClienteById(id);
        if (cliente != null) {
            clienteService.deleteCliente(id);
            return new ResponseEntity<>("Cliente eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró el cliente", HttpStatus.NOT_FOUND);
        }
    }
}