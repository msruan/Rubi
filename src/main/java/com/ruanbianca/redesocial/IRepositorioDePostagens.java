package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface IRepositorioDePostagens {
   

    public ArrayList<Postagem> getPostagens();


    public ArrayList<PostagemAvancada> getPostagensAvancadas();


    public Optional<Postagem> consultarPostagem(UUID id);

    
    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag);


    public boolean postagemJaExiste(UUID id);


    public void incluir(Postagem postagem) throws NullObjectAsArgumentException, PostAlreadyExistsException;


    public void removerPostPorPerfil(Perfil perfil);
    
    
    public void atualizarPostagem(Postagem post);
}
