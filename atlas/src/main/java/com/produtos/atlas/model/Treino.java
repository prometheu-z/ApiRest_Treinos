package com.produtos.atlas.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "treino")
    private List<ItemTreino> itemTreinos;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private User personal;

    private String nome;

    private List<Date> dias;
}
