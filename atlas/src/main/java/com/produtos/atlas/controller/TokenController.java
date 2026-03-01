package com.produtos.atlas.controller;


import com.produtos.atlas.dto.LoginDTO;
import com.produtos.atlas.dto.UsuarioReqDTO;
import com.produtos.atlas.dto.UsuarioResDTO;
import com.produtos.atlas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TokenController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")

    public ResponseEntity<UsuarioResDTO> login (@RequestBody @Valid LoginDTO dto){

        var user = usuarioService.validarUsuario(dto, bCryptPasswordEncoder);

        return ResponseEntity.ok(user);


    }
}
