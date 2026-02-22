package com.produtos.atlas.model;

import jakarta.persistence.*;
import lombok.*;

import javax.sound.midi.Soundbank;
import java.security.PrivateKey;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    private Double peso;

    private String repeticoes;

    private int series;

    private int desacnso;

    private String obs;

}
