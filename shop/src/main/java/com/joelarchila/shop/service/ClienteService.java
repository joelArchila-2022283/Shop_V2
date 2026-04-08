package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> getAllClientes();
    Cliente getClienteById(Integer id);
    Cliente saveCliente(Cliente cliente) throws RuntimeException;
    Cliente updateCliente(Integer id, Cliente cliente);
    void deleteCliente(Integer id);
}
