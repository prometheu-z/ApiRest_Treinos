package com.produtos.atlas.service.impl;

import com.produtos.atlas.model.Treino;
import com.produtos.atlas.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreinoServiceImp {

    @Autowired
    private TreinoRepository treinoRepository;


    public Treino salvar(Treino treino){
        return treinoRepository.save(treino);
    }

    public Optional<Treino> findById(Long treino){
        return treinoRepository.findById(treino);
    }

    public void deletar(Long id){
       treinoRepository.deleteById(id);
    }

    public Page<Treino> trenosDePersonal(Long id, int pagina, int tamanho){
        return treinoRepository.findByPersonalId(id, PageRequest.of(pagina, tamanho));

    }
}
