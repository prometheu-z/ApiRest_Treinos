package com.produtos.atlas.controller;


import com.produtos.atlas.dto.UsuarioReqDTO;
import com.produtos.atlas.dto.UsuarioResDTO;
import com.produtos.atlas.model.Role;
import com.produtos.atlas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<UsuarioResDTO> login (@RequestBody UsuarioReqDTO usuarioReqDTO){
        var user = usuarioService.findByNome(usuarioReqDTO.nome());

        if(user.isEmpty() || !user.get().isLoginCorreto(usuarioReqDTO, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Senha incorreta");
        }

        Instant agora = Instant.now();

        Instant tempoDeExpiracao = agora.plus(5, ChronoUnit.MINUTES);

        var escopo = user.get().getRoles()
                .stream().map(Role::getNome)
                .collect(Collectors.joining(" "));

        JwtClaimsSet clains = JwtClaimsSet.builder()
                .issuer("Atlas")
                .subject(user.get().getId().toString())
                .issuedAt(agora)
                .expiresAt(tempoDeExpiracao)
                .claim("scope", escopo)
                .build();

        var jwtvalue = jwtEncoder.encode(JwtEncoderParameters.from(clains)).getTokenValue();

        return  ResponseEntity.ok(new UsuarioResDTO(jwtvalue, tempoDeExpiracao.toEpochMilli()));


    }
}
