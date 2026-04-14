package com.joelarchila.shop.service;

import com.joelarchila.shop.entity.Usuario;
import com.joelarchila.shop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImplements implements LoginService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public Usuario registrar(String username, String password) {
        // CORRECCIÓN: Usamos findByUsername porque 'username' es String
        if (repo.findByUsername(username) != null) {
            return null;
        }

        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(username.toLowerCase() + "@shop.com"); // Obligatorio por tu Entity
        u.setEstado(1); // Obligatorio por tu Entity

        // Lógica de roles
        if (username.equalsIgnoreCase("admin")) {
            u.setRol("ADMIN");
        } else {
            u.setRol("CLIENTE");
        }

        return repo.save(u);
    }

    @Override
    public Usuario login(String username, String password) {
        // CORRECCIÓN: No puedes usar findById(String), usamos findByUsername(String)
        Usuario u = repo.findByUsername(username);

        if (u != null && u.getPassword().equals(password)) {
            return u;
        }

        return null;
    }

    @Override
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(int id) {
        repo.deleteById(id);
    }

    // --- Métodos de la interfaz UsuarioService ---

    @Override
    public List<Usuario> getAllUsuarios() {
        return repo.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getRol() == null) usuario.setRol("CLIENTE");
        if (usuario.getEstado() == null) usuario.setEstado(1);
        return repo.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {
        Usuario existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setUsername(usuario.getUsername());
            existing.setPassword(usuario.getPassword());
            existing.setEmail(usuario.getEmail());
            existing.setRol(usuario.getRol());
            existing.setEstado(usuario.getEstado());
            return repo.save(existing);
        }
        return null;
    }

    @Override
    public void deleteUsuario(Integer id) {
        repo.deleteById(id);
    }
}