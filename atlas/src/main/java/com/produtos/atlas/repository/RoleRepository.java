package com.produtos.atlas.repository;

import com.produtos.atlas.model.Role;
import com.produtos.atlas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByNome(String nome);
}
