package com.produtos.atlas.service;

import com.produtos.atlas.dto.LoginRequest;
import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    public UserRepository userRepository;

    public Optional<Usuario> findByNome(String nome){
        Optional<Usuario> user = userRepository.findByNome(nome);


        return userRepository.findByNome(nome);
    }

    public Usuario salvar(Usuario usuario){
        return userRepository.save(usuario);
    }



}
