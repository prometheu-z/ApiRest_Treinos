package com.produtos.atlas.service.impl;

import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImp {

    @Autowired
    private UsuarioRepository userRepository;

    public Optional<Usuario> findByNome(String nome){
        return userRepository.findByNome(nome);
    }

    public Optional<Usuario> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id){
        return userRepository.findById(id);
    }

    public List<Usuario> findAll(){

        return userRepository.findAll();
    }

    public Page<Usuario> findByPersonal(Long personal_id, int pagina, int tamanho){
        return userRepository.findByPersonal(personal_id, PageRequest.of(pagina, tamanho));
    }

    public void salvar(Usuario usuario){
        userRepository.save(usuario);
    }


}
