package com.produtos.atlas.repository;

import com.produtos.atlas.model.Exercicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    Page<Exercicio> findByGrupoMuscularContainingIgnoreCaseOrNomeContainingIgnoreCase(String grupoMuscular, String nome, Pageable pageable);
}
