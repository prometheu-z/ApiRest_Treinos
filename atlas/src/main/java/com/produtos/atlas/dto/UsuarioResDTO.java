package com.produtos.atlas.dto;

import java.time.Instant;

public record UsuarioResDTO(Long id, String nome, String email, String accessToken, Instant expiredIn){

}
