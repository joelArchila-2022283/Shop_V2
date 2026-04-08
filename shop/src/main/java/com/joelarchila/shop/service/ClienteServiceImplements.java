package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Cliente;
import com.joelarchila.shop.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClienteServiceImplements implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImplements(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cliente> getAllClientes() {
        return repository.findAll();
    }

    @Override
    public Cliente getClienteById(Integer id) {
        Cliente cliente = repository.findById(id).orElse(null);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        return cliente;
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Integer id, Cliente cliente) {
        Cliente existente = repository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombreCliente(cliente.getNombreCliente());
            existente.setApellidoCliente(cliente.getApellidoCliente());
            existente.setDireccion(cliente.getDireccion());
            existente.setEstado(cliente.getEstado());
            return repository.save(existente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente para actualizar");
        }
    }

    @Override
    public void deleteCliente(Integer id) {
        Cliente cliente = repository.findById(id).orElse(null);
        if (cliente != null) {
            repository.delete(cliente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente para eliminar");
        }
    }
}
