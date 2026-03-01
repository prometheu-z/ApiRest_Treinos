package com.produtos.atlas.controller;

import com.produtos.atlas.dto.ExercicioReqDTO;
import com.produtos.atlas.dto.ExercicioResDTO;
import com.produtos.atlas.dto.FeedExercicioDTO;
import com.produtos.atlas.service.ExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercicios")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    @GetMapping
    public ResponseEntity<FeedExercicioDTO> exercicios(@RequestParam(value = "nome", defaultValue = "") String nome,
                                                       @RequestParam(value = "grupoMuscular", defaultValue = "") String grupoMuscular,
                                                       @RequestParam(value = "pagina", defaultValue = "0") int pagina,
                                                       @RequestParam(value = "tamanho", defaultValue = "10") int tamanho){

        FeedExercicioDTO exercicios = exercicioService.exibirExercicios(grupoMuscular, nome, pagina, tamanho);

        return ResponseEntity.ok(exercicios);
    }

    @PostMapping
    public ResponseEntity<ExercicioResDTO> criarExercicio(@RequestBody ExercicioReqDTO dto){

        var exercicio = exercicioService.criarExercicio(dto);

        return ResponseEntity.ok(exercicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExercicioResDTO> alterarExercicio(@PathVariable(name = "id") Long id ,@RequestBody ExercicioReqDTO dto){

        ExercicioResDTO exercicio = exercicioService.alterarExercicio(id, dto);

        return ResponseEntity.ok(exercicio);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleterExercicio(@PathVariable(name = "id") Long id){

        exercicioService.deleterExercicio(id);

        return ResponseEntity.ok().build();
    }
}
