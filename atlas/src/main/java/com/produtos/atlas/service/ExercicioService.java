package com.produtos.atlas.service;

import com.produtos.atlas.dto.ExercicioReqDTO;
import com.produtos.atlas.dto.ExercicioResDTO;
import com.produtos.atlas.dto.FeedExercicioDTO;
import com.produtos.atlas.model.Exercicio;
import com.produtos.atlas.service.impl.ExercicioServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ExercicioService {

    @Autowired
    private ExercicioServiceImp exercicioServiceImp;


    @Transactional
    public ExercicioResDTO criarExercicio(ExercicioReqDTO dto){
        if(exercicioServiceImp.findByNome(dto.nome()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Exercicio com esse nome já existe");
        };

        Exercicio exercicio = new Exercicio();

        exercicio.setNome(dto.nome());
        exercicio.setVideo(dto.video());
        exercicio.setGrupoMuscular(dto.GrupoMuscular());

        exercicioServiceImp.salvar(exercicio);

        return new ExercicioResDTO(exercicio.getId(),
                exercicio.getGrupoMuscular(),
                exercicio.getNome(),
                exercicio.getVideo());

    }

    public FeedExercicioDTO exibirExercicios(String grupoMuscular, String nome, int pagina, int tamanho){
        Page<ExercicioResDTO> exercicios = exercicioServiceImp.buscarExeercicios(grupoMuscular, nome, pagina, tamanho)
                .map(exercicio -> new ExercicioResDTO(exercicio.getId(),exercicio.getGrupoMuscular(), exercicio.getNome(),exercicio.getVideo()));

        return new FeedExercicioDTO(exercicios.getContent(),
                pagina,
                tamanho,
                exercicios.getTotalPages(),
                exercicios.getTotalElements());

    }


    @Transactional
    public ExercicioResDTO alterarExercicio(Long exercicioId, ExercicioReqDTO dto){
        Exercicio exercicio = exercicioServiceImp.findById(exercicioId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercicio não existe")
        );

        exercicio.setNome(dto.nome());
        exercicio.setVideo(dto.video());
        exercicio.setGrupoMuscular(dto.GrupoMuscular());

        exercicioServiceImp.salvar(exercicio);

        return new ExercicioResDTO(exercicio.getId(),
                exercicio.getGrupoMuscular(),
                exercicio.getNome(),
                exercicio.getVideo());

    }

    @Transactional
    public void deleterExercicio(Long id){
        Exercicio exercicio = exercicioServiceImp.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercicio não existe")
        );

        exercicioServiceImp.deleter(exercicio);


    }



}
