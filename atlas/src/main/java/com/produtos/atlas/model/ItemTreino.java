package com.produtos.atlas.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exercicio exercicio;

    @ManyToOne
    private Treino treino;

    private Double peso;

    private String repeticoes;

    private int series;

    private int desacnso;

    private String obs;

}
