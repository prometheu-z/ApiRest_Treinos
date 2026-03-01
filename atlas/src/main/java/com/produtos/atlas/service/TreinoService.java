package com.produtos.atlas.service;

import com.produtos.atlas.dto.FeedTreinoItemDTO;
import com.produtos.atlas.dto.FeedTreinosDTO;
import com.produtos.atlas.dto.TreinoReqDTO;
import com.produtos.atlas.model.Exercicio;
import com.produtos.atlas.model.ItemTreino;
import com.produtos.atlas.model.Treino;
import com.produtos.atlas.service.impl.ExercicioServiceImp;
import com.produtos.atlas.service.impl.TreinoServiceImp;
import com.produtos.atlas.service.impl.UsuarioServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TreinoService {

    @Autowired
    private TreinoServiceImp treinoServiceImp;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @Autowired
    private ExercicioServiceImp exercicioServiceImp;



    @Transactional
    public Treino criarTreino(TreinoReqDTO dto, JwtAuthenticationToken token){
        var user = usuarioServiceImp.findById(Long.parseLong(token.getName())).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal não encontrado")
        ); //note retorna o nome do subject do clains do jwt


        Treino treino = new Treino();

        treino.setNome(dto.nome());
        treino.setPersonal(user);

        return treinoServiceImp.salvar(treino);
    }

    @Transactional
    public FeedTreinosDTO exibirTreinos(int pagina, int tamanho, JwtAuthenticationToken token){
        Page<FeedTreinoItemDTO> treinos = treinoServiceImp.trenosDePersonal(Long.parseLong(token.getName()), pagina, tamanho)
                .map(treino -> new FeedTreinoItemDTO(
                        treino.getId(), treino.getNome(), treino.getItemTreinos().size()
                ));
        FeedTreinosDTO feed = new FeedTreinosDTO(treinos.getContent(),
                pagina, tamanho, treinos.getTotalPages(), treinos.getTotalElements());

        return feed;
    }

    @Transactional
    public Treino alterarTreino(Long treinoId, TreinoReqDTO dto, JwtAuthenticationToken token){
        Treino treino = treinoServiceImp.findById(treinoId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado")
        );


        if(treino.getPersonal().getId().equals(Long.parseLong(token.getName()))){
            treino.setNome(dto.nome());

            treino.getItemTreinos().clear();

            if(dto.itemTreinos() != null){
                dto.itemTreinos().forEach(novo -> {


                    Exercicio exercicio = exercicioServiceImp.findById(novo.exercicioId()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercicio não encontrado")
                    );

                    ItemTreino item = new ItemTreino();
                    item.setTreino(treino);
                    item.setExercicio(exercicio);
                    item.setRepeticoes(novo.repeticoes());
                    item.setSeries(novo.serie());
                    item.setPeso(novo.peso());
                    item.setObs(novo.obs());

                    treino.getItemTreinos().add(item);

                });
            }


            treinoServiceImp.salvar(treino);


        }else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse treino pertence a outro personal");
        }

        return treino;
    }

    @Transactional
    public void deletartreino(Long treinoId, JwtAuthenticationToken token){
        Treino treino = treinoServiceImp.findById(treinoId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado")
        );

        if(treino.getPersonal().getId().equals(Long.parseLong(token.getName()))){
            treinoServiceImp.deletar(treinoId);

        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esse treino pertence a outro personal");
        }
    }
}
