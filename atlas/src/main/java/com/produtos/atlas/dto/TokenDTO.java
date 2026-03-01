package com.produtos.atlas.dto;

import java.time.Instant;

public record TokenDTO(String token, Instant tempoExpiracao) {
}
