package com.produtos.atlas.repository;

import com.produtos.atlas.model.ItemTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTreinoRepository extends JpaRepository<ItemTreino, Long> {
}
