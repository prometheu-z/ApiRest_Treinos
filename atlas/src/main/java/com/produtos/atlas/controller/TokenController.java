package com.produtos.atlas.controller;


import com.nimbusds.jwt.JWTClaimsSet;
import com.produtos.atlas.dto.LoginRequest;
import com.produtos.atlas.dto.LoginResponse;
import com.produtos.atlas.repository.UserRepository;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@RestController
public class TokenController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest){
        var user = usuarioService.findByNome(loginRequest.nome());

        if(user.isEmpty() || user.get().isLoginCorreto(loginRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Senha incorreta");
        }

        Instant agora = Instant.now();

        Instant tempoDeExpiracao = agora.plus(5, ChronoUnit.MINUTES);

        JwtClaimsSet clains = JwtClaimsSet.builder()
                .issuer("Atlas")
                .subject(user.get().getId().toString())
                .issuedAt(agora)
                .expiresAt(tempoDeExpiracao)
                .build();

        var jwtvalue = jwtEncoder.encode(JwtEncoderParameters.from(clains)).getTokenValue();

        return  ResponseEntity.ok(new LoginResponse(jwtvalue, tempoDeExpiracao.toEpochMilli()));


    }
}
