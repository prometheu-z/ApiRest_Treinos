package com.produtos.atlas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;


    public enum Values{
        ALUNO(3L),
        PERSONAL(2L),
        ADMIN(1L);


        Long roleId;

        Values(Long roleId){
            this.roleId =  roleId;
        }

    }
}
