package com.produtos.atlas.controller;

import com.produtos.atlas.dto.ExercicioReqDTO;
import com.produtos.atlas.dto.ExercicioResDTO;
import com.produtos.atlas.dto.FeedExercicioDTO;
import com.produtos.atlas.model.Exercicio;
import com.produtos.atlas.service.ExercicioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        Page<ExercicioResDTO> exercicios = exercicioService.buscarExeercicios(grupoMuscular, nome, pagina, tamanho)
                .map(exercicio -> new ExercicioResDTO(exercicio.getId(), exercicio.getGrupoMuscular(), exercicio.getNome(), exercicio.getVideo()));

        FeedExercicioDTO feed = new FeedExercicioDTO(exercicios.getContent(),
                pagina,
                tamanho,
                exercicios.getTotalPages(),
                exercicios.getTotalElements());

        return ResponseEntity.ok(feed);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ExercicioResDTO> criarExercicio(@RequestBody ExercicioReqDTO dto){

        if(exercicioService.findByNome(dto.nome()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Exercicio com esse nome já existe");
        };

        Exercicio exercicio = new Exercicio();

        exercicio.setNome(dto.nome());
        exercicio.setVideo(dto.video());
        exercicio.setGrupoMuscular(dto.GrupoMuscular());

        exercicioService.salvar(exercicio);

        return ResponseEntity.ok(new ExercicioResDTO(exercicio.getId(), exercicio.getGrupoMuscular(), exercicio.getNome(), exercicio.getVideo()));

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ExercicioResDTO> alterarExercicio(@PathVariable(name = "id") Long id ,@RequestBody ExercicioReqDTO dto){

        Exercicio exercicio = exercicioService.findById(id).orElseThrow(
                () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercicio não existe");
                }
        );

        exercicio.setNome(dto.nome());
        exercicio.setVideo(dto.video());
        exercicio.setGrupoMuscular(dto.GrupoMuscular());

        exercicioService.salvar(exercicio);

        return ResponseEntity.ok(new ExercicioResDTO(exercicio.getId(), exercicio.getGrupoMuscular(), exercicio.getNome(), exercicio.getVideo()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleterExercicio(@PathVariable(name = "id") Long id){
        Exercicio exercicio = exercicioService.findById(id).orElseThrow(
                () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercicio não existe");
                }
        );

        exercicioService.deleter(exercicio);

        return ResponseEntity.ok().build();
    }
}
