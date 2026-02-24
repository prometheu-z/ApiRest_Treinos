package com.produtos.atlas.service;

import com.produtos.atlas.model.Role;
import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByNome(String nome){

        return roleRepository.findByNome(nome);
    }
}
