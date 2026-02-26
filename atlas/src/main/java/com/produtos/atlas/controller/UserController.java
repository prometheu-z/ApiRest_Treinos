package com.produtos.atlas.controller;

import com.produtos.atlas.dto.UsuarioReqDTO;
import com.produtos.atlas.model.Role;
import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.service.RoleService;
import com.produtos.atlas.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/usuarios")
    @Transactional
    public ResponseEntity<Void> newUser(@RequestBody UsuarioReqDTO dto){

        var role = roleService.findByNome(Role.Values.PERSONAL.name());

        var userDB = usuarioService.findByNome(dto.nome());

        if(userDB.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        var user = new Usuario();

        user.setNome(dto.nome());
        user.setSenha(bCryptPasswordEncoder.encode(dto.senha()));

        user.setRoles(Set.of(role));

        usuarioService.salvar(user);


        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasAuthority('SCOPE_PERSONAL')")
    public ResponseEntity<List<Usuario>> listUser(){
        var users = usuarioService.findAll();

        return ResponseEntity.ok(users);

    }
}
