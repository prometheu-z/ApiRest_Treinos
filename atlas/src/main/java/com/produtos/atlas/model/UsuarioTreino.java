package com.produtos.atlas.model;

import com.produtos.atlas.model.Treino;
import com.produtos.atlas.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UsuarioTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Treino treino;

    private LocalDate data;

    private String status;

}