package com.produtos.atlas.repository;

import com.produtos.atlas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    Page<Usuario> findByPersonal(Long personal, Pageable pageable);

    Optional<Usuario> findByEmail(String email);
}
