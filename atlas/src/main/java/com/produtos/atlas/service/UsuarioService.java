package com.produtos.atlas.service;

import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository userRepository;

    public Optional<Usuario> findByNome(String nome){
        Optional<Usuario> user = userRepository.findByNome(nome);


        return userRepository.findByNome(nome);
    }
    public Optional<Usuario> findById(Long id){
        return userRepository.findById(id);
    }
    public List<Usuario> findAll(){

        return userRepository.findAll();
    }

    public Optional<Usuario> findByPersonal(Long personal_id){
        return userRepository.findByPersonal(personal_id);
    }
    public Usuario salvar(Usuario usuario){
        return userRepository.save(usuario);
    }



}
