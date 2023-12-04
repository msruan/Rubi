package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class RepositorioDePostagensSql implements IRepositorioDePostagens{

    public ArrayList<Postagem> getPostagens(){
        return null;
    }


    public ArrayList<PostagemAvancada> getPostagensAvancadas(){
        return null;
    }


    public void incluir(Postagem postagem) throws NullObjectAsArgumentException, PostAlreadyExistsException{}


    public Optional<Postagem> consultarPostagem(UUID id){
        return Optional.empty();
    }

    
    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag){
        return null;
    }

    public boolean postagemJaExiste(UUID id){
        return false;
    }
   
}
