package com.produtos.atlas.model;


import com.produtos.atlas.dto.UsuarioReqDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private String Senha;

    @ManyToOne
    private Usuario personal;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_usuario_roles",
    joinColumns = @JoinColumn(name = "usuario"),
    inverseJoinColumns = @JoinColumn(name = "role"))
    private  Set<Role> roles;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioTreino> treinosAtribuidos;

    public boolean isLoginCorreto(UsuarioReqDTO usuarioReqDTO, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(usuarioReqDTO.senha(), this.Senha);
    }




}
