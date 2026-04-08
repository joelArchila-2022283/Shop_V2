package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Integer id);
    Usuario saveUsuario(Usuario usuario) throws RuntimeException;
    Usuario updateUsuario(Integer id, Usuario usuario);
    void deleteUsuario(Integer id);
}
