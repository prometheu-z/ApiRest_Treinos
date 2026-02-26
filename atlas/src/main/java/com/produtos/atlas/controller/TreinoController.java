package com.produtos.atlas.controller;

import com.produtos.atlas.dto.*;
import com.produtos.atlas.model.ItemTreino;
import com.produtos.atlas.model.Treino;
import com.produtos.atlas.service.TreinoService;
import com.produtos.atlas.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/treinos")
public class TreinoController {

    @Autowired
    private TreinoService treinoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyAuthority('SCOPE_PERSONAL', 'SCOPE_ADMIN')")
    public ResponseEntity<TreinoResDTO> criarTreino(@RequestBody TreinoReqDTO dto,
                                                       JwtAuthenticationToken token){
        var user = usuarioService.findById(Long.parseLong(token.getName())).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal não encontrado")
        ); //note retorna o nome do subject do clains do jwt


        Treino treino = new Treino();

        treino.setNome(dto.nome());
        treino.setPersonal(user);

        treinoService.salvar(treino);

        return ResponseEntity.ok(new TreinoResDTO(treino.getNome(), treino.getItemTreinos().size()));

    }

    @GetMapping
    public ResponseEntity<FeedTreinosDTO> treinos(@RequestParam(value = "pagina", defaultValue = "0") int pagina,
                                                  @RequestParam(value = "tamanho", defaultValue = "10") int tamanho,
                                                  JwtAuthenticationToken token){

        Page<FeedTreinoItemDTO> treinos = treinoService.trenosDePersonal(Long.parseLong(token.getName()), pagina, tamanho)
                .map(treino -> new FeedTreinoItemDTO(
                        treino.getId(), treino.getNome(), treino.getItemTreinos().size()
                ));


        return ResponseEntity.ok(new FeedTreinosDTO(treinos.getContent(), pagina, tamanho, treinos.getTotalPages(), treinos.getTotalElements()));



    }

    @PutMapping("/{id}")
    public ResponseEntity<TreinoResDTO> alterarTreino(@RequestBody TreinoReqDTO dto, @PathVariable("id") Long treinoId, JwtAuthenticationToken token){
        Treino treino = treinoService.findById(treinoId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado")
        );

        if(treino.getPersonal().getId().equals(Long.parseLong(token.getName()))){
            treino.setNome(dto.nome());

            treino.getItemTreinos().clear();

            if(dto.itemTreinos() != null){
                dto.itemTreinos().forEach(novo -> {
                    ItemTreino item = new ItemTreino();
                    item.setTreino(treino);
                    item.setExercicio(novo.exercicio());
                    item.setRepeticoes(novo.repeticoes());
                    item.setSeries(novo.serie());
                    item.setPeso(novo.peso());
                    item.setObs(novo.obs());

                    treino.getItemTreinos().add(item);

                });
            }


            treinoService.salvar(treino);


        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esse treino pertence a outro personal");
        }

        return ResponseEntity.ok(new TreinoResDTO(treino.getNome(), treino.getItemTreinos().size()));

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreino(@PathVariable("id") Long treinoId, JwtAuthenticationToken token){

        Treino treino = treinoService.findById(treinoId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado")
        );

        if(treino.getPersonal().getId().equals(Long.parseLong(token.getName()))){
            treinoService.deletar(treinoId);

        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esse treino pertence a outro personal");
        }

        return ResponseEntity.ok().build();
    }


}
