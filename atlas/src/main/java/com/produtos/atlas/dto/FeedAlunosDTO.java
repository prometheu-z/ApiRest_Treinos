package com.produtos.atlas.dto;

import java.util.List;

public record FeedAlunosDTO(List<AlunoResDTO> alunos, int pagina, int tamanho, int total, Long totalElementos) {
}
