package com.produtos.atlas.dto;

import java.util.List;

public record FeedExercicioDTO (List<ExercicioResDTO> exercicio, int pagina, int tamanho, int total, Long ElementosTotais){
}
