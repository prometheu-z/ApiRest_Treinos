package com.produtos.atlas.service.impl;

import com.produtos.atlas.dto.TokenDTO;
import com.produtos.atlas.model.Role;
import com.produtos.atlas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenServiceImp {

    @Autowired
    private JwtEncoder jwtEncoder;


    public TokenDTO gerarToken(Usuario user) {
        Instant agora = Instant.now();

        Instant tempoDeExpiracao = agora.plus(5, ChronoUnit.MINUTES);

        var escopo = user.getRoles()
                .stream().map(Role::getNome)
                .collect(Collectors.joining(" "));

        JwtClaimsSet clains = JwtClaimsSet.builder()
                .issuer("Atlas")
                .subject(user.getId().toString())
                .issuedAt(agora)
                .expiresAt(tempoDeExpiracao)
                .claim("scope", escopo)
                .build();

        String jwtvalue = jwtEncoder.encode(JwtEncoderParameters.from(clains)).getTokenValue();

        return new TokenDTO(jwtvalue, tempoDeExpiracao);
    }
}
