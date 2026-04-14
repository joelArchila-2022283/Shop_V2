package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Usuario;
import com.joelarchila.shop.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplements implements UsuarioService{

    private final UsuarioRepository repository;

    public UsuarioServiceImplements(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario registrar(String usuario, String password) {
        return null;
    }

    @Override
    public Usuario login(String usuario, String password) {
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return List.of();
    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return repository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);

        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = repository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario updateUsuario = usuarioExistente.get();
            updateUsuario.setUsername(usuario.getUsername());
            updateUsuario.setPassword(usuario.getPassword());
            updateUsuario.setEmail(usuario.getEmail());
            updateUsuario.setRol(usuario.getRol());
            updateUsuario.setEstado(usuario.getEstado());
            return repository.save(updateUsuario);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo actualizar: Usuario no encontrado");
        }
    }

    @Override
    public void deleteUsuario(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo eliminar: Usuario no encontrado");
        }
    }
}
