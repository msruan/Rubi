package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface IRepositorioDePostagens {
   

    public ArrayList<Postagem> getPostagens() throws Exception;


    public ArrayList<PostagemAvancada> getPostagensAvancadas() throws Exception;


    public Optional<Postagem> consultarPostagem(UUID id) throws Exception;

    
    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag) throws Exception;


    public boolean postagemJaExiste(UUID id) throws Exception;


    public void incluir(Postagem postagem) throws Exception;


    public void removerPostPorPerfil(Perfil perfil) throws Exception;
    
    
    public void atualizarPostagem(Postagem post) throws Exception;
}
