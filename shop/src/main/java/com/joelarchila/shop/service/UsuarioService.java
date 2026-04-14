package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario registrar(String usuario, String password);

    Usuario login(String usuario, String password);

    List<Usuario> listar();

    void eliminar(int id);

    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Integer id);
    Usuario saveUsuario(Usuario usuario) throws RuntimeException;
    Usuario updateUsuario(Integer id, Usuario usuario);
    void deleteUsuario(Integer id);
}
