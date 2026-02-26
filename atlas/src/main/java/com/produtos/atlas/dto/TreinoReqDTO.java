package com.produtos.atlas.dto;

import com.produtos.atlas.model.ItemTreino;

import java.util.ArrayList;
import java.util.List;

public record TreinoReqDTO(String nome, List<ItemTreinoReqDTO> itemTreinos){

    public TreinoReqDTO {
        if (itemTreinos == null) {
            itemTreinos = new ArrayList<>();
        }
    }
}
