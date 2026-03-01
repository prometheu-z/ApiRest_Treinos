package com.produtos.atlas.controller;

import com.produtos.atlas.dto.*;
import com.produtos.atlas.model.Treino;
import com.produtos.atlas.service.ExercicioService;
import com.produtos.atlas.service.TreinoService;
import com.produtos.atlas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/treinos")
public class TreinoController {

    @Autowired
    private TreinoService treinoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ExercicioService exercicioService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_PERSONAL', 'SCOPE_ADMIN')")
    public ResponseEntity<TreinoResDTO> criarTreino(@RequestBody TreinoReqDTO dto,
                                                       JwtAuthenticationToken token){
        Treino treino = treinoService.criarTreino(dto, token);

        return ResponseEntity.ok(new TreinoResDTO(treino.getNome(), treino.getItemTreinos().size()));

    }

    @GetMapping("/exercicios")
    public ResponseEntity<FeedExercicioDTO> listarExercicios(@RequestParam(value = "nome", defaultValue = "") String nome,
                                                   @RequestParam(value = "grupoMuscular", defaultValue = "") String grupoMuscular,
                                                   @RequestParam(value = "pagina", defaultValue = "0") int pagina,
                                                   @RequestParam(value = "tamanho", defaultValue = "10") int tamanho){

        FeedExercicioDTO exercicios = exercicioService.exibirExercicios(grupoMuscular, nome, pagina, tamanho);

        return ResponseEntity.ok(exercicios);

    }

    @GetMapping
    public ResponseEntity<FeedTreinosDTO> treinos(@RequestParam(value = "pagina", defaultValue = "0") int pagina,
                                                  @RequestParam(value = "tamanho", defaultValue = "10") int tamanho,
                                                  JwtAuthenticationToken token){

        var treinos = treinoService.exibirTreinos(pagina, tamanho, token);

        return ResponseEntity.ok(treinos);



    }

    @PutMapping("/{id}")
    public ResponseEntity<TreinoResDTO> alterarTreino(@RequestBody TreinoReqDTO dto, @PathVariable("id") Long treinoId, JwtAuthenticationToken token){

        Treino treino = treinoService.alterarTreino(treinoId, dto, token);

        return ResponseEntity.ok(new TreinoResDTO(treino.getNome(), treino.getItemTreinos().size()));

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreino(@PathVariable("id") Long treinoId, JwtAuthenticationToken token){

       treinoService.deletartreino(treinoId, token);

        return ResponseEntity.ok().build();
    }


}
