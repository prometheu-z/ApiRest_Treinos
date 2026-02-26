package com.produtos.atlas.dto;

import java.util.List;

public record FeedTreinosDTO(List<FeedTreinoItemDTO> treinoItens, int pagina, int tamanho, int total, Long elementosTotais ){
}
