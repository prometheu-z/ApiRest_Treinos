package com.produtos.atlas.dto;

import com.produtos.atlas.model.Exercicio;
import com.produtos.atlas.model.Treino;

public record ItemTreinoReqDTO (Long exercicioId, int serie, String repeticoes, Double peso, int descanso, String obs){

}
