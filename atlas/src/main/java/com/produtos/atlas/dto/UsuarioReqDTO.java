package com.produtos.atlas.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioReqDTO(@NotBlank(message = "O campo nome, não pode estar em branco") String nome,
                            @NotBlank(message = "O campo email, não pode estar em branco") String email,
                            @NotBlank(message = "O campo senha, não pode estar em branco") String senha){


}
