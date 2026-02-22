package com.produtos.atlas.repository;

import com.produtos.atlas.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
}
