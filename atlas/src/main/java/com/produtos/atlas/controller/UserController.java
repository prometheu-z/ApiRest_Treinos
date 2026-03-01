package com.produtos.atlas.controller;

import com.produtos.atlas.dto.FeedAlunosDTO;
import com.produtos.atlas.dto.UsuarioReqDTO;
import com.produtos.atlas.dto.UsuarioResDTO;
import com.produtos.atlas.service.impl.RoleServiceImp;
import com.produtos.atlas.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleServiceImp roleService;


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UsuarioResDTO> newUser(@RequestBody @Valid UsuarioReqDTO dto){

        UsuarioResDTO user = usuarioService.criarUsuario(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasAuthority('SCOPE_PERSONAL')")
    public ResponseEntity<FeedAlunosDTO> listUser(JwtAuthenticationToken token,
                                                  @RequestParam(name = "pagina", defaultValue = "0")int pagina,
                                                  @RequestParam(name = "tamanho", defaultValue = "10") int tamanho){
         var users = usuarioService.listarAlunos(token, pagina, tamanho);

        return ResponseEntity.ok(users);

    }
}
