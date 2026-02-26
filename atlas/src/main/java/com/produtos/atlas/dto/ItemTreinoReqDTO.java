package com.produtos.atlas.dto;

import com.produtos.atlas.model.Exercicio;
import com.produtos.atlas.model.Treino;

public record ItemTreinoReqDTO (Treino treino, Exercicio exercicio, int serie, String repeticoes, Double peso, int descanso, String obs){

}
