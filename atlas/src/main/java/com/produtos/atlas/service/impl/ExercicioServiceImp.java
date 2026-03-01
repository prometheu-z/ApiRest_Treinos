package com.produtos.atlas.service.impl;

import com.produtos.atlas.model.Exercicio;
import com.produtos.atlas.repository.ExercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExercicioServiceImp {

    @Autowired
    private ExercicioRepository exercicioRepository;

    public Optional<Exercicio> findById(Long id){
        return exercicioRepository.findById(id);
    }

    public Page<Exercicio> buscarExeercicios(String grupoMuscular, String nome, int pagina, int tamanho){
        return exercicioRepository.findByGrupoMuscularContainingIgnoreCaseOrNomeContainingIgnoreCase(grupoMuscular, nome, PageRequest.of(pagina,tamanho));
    }

    public Exercicio salvar(Exercicio exercicio){
        return exercicioRepository.save(exercicio);
    }


    public Optional<Exercicio> findByNome(String nome){
        return exercicioRepository.findByNome(nome);
    }

    public void deleter(Exercicio exercicio){
        exercicioRepository.delete(exercicio);
        return;
    }

}
