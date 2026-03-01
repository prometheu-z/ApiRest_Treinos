package com.produtos.atlas.service;

import com.produtos.atlas.dto.*;
import com.produtos.atlas.model.Role;
import com.produtos.atlas.model.Usuario;
import com.produtos.atlas.service.impl.RoleServiceImp;
import com.produtos.atlas.service.impl.TokenServiceImp;
import com.produtos.atlas.service.impl.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleServiceImp roleService;

    @Autowired
    private TokenServiceImp tokenServiceImp;


    public UsuarioResDTO criarUsuario(UsuarioReqDTO dto){

        var role = roleService.findByNome(Role.Values.PERSONAL.name());

        var user = usuarioServiceImp.findByEmail(dto.email());

        if(user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email de usuario já cadastrado");

        };
        var usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setSenha(Objects.requireNonNull(bCryptPasswordEncoder.encode(dto.senha())));
        usuario.setEmail(dto.email());

        usuario.setRoles(Set.of(role));

        usuarioServiceImp.salvar(usuario);

        var token = tokenServiceImp.gerarToken(usuario);


        return new UsuarioResDTO(usuario.getId() ,usuario.getNome(), usuario.getEmail(),
                token.token(), token.tempoExpiracao());


    }

    public List<Usuario> listarUsuarios(){
        return usuarioServiceImp.findAll();

    }

    public FeedAlunosDTO listarAlunos(JwtAuthenticationToken token, int pagina, int tamanho){

        Page<AlunoResDTO> alunos = usuarioServiceImp.findByPersonal(Long.parseLong(token.getName()), pagina, tamanho)
                .map(usuario -> new AlunoResDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTreinosAtribuidos().size()));


        return new FeedAlunosDTO(alunos.getContent(), pagina, tamanho, alunos.getTotalPages(), alunos.getTotalElements());


    }


    public UsuarioResDTO validarUsuario(LoginDTO dto, BCryptPasswordEncoder bCryptPasswordEncoder){
        var user = usuarioServiceImp.findByEmail(dto.email()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado")
        );

        if(!user.isLoginCorreto(dto.senha(), bCryptPasswordEncoder)){
            throw new BadCredentialsException("Senha incorreta");
        }

        var token = tokenServiceImp.gerarToken(user);


        return new UsuarioResDTO(user.getId() ,user.getNome(), user.getEmail(),
                token.token(), token.tempoExpiracao());

    }
}
