package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoginService {

    Usuario registrar(String usuario, String password);

    Usuario  login(String usuario, String password);

    List<Usuario> listar();

    void eliminar(int id);

    List<Usuario> getAllUsuarios();

    Usuario getUsuarioById(Integer id);

    Usuario saveUsuario(Usuario usuario);

    Usuario updateUsuario(Integer id, Usuario usuario);

    void deleteUsuario(Integer id);
}
