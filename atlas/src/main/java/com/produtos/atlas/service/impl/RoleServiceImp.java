package com.produtos.atlas.service.impl;

import com.produtos.atlas.model.Role;
import com.produtos.atlas.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByNome(String nome){

        return roleRepository.findByNome(nome);
    }
}
