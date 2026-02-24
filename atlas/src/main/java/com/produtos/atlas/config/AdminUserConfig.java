package com.produtos.atlas.config;

import com.produtos.atlas.model.Role;
import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.service.RoleService;
import com.produtos.atlas.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleService.findByNome(Role.Values.ADMIN.name());


        Optional<Usuario> userAdmin = usuarioService.findByNome("admin");

        if (userAdmin.isEmpty()) {
            var user = new Usuario();
            user.setNome("admin");
            user.setSenha(bCryptPasswordEncoder.encode("1234"));

            // Agora roleAdmin com certeza tem um valor válido! Sem NPE aqui.
            user.setRoles(Set.of(roleAdmin));

            usuarioService.salvar(user);
            System.out.println("Usuário Admin criado com sucesso!");
        } else {
            System.out.println("O usuário admin já existe. Pulando criação.");
        }



    }
}
