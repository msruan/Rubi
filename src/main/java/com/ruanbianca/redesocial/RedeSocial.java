package com.ruanbianca.redesocial;

import java.util.ArrayList;

public class RedeSocial {
    private RepositorioDePerfis _perfis;
    private RepositorioDePostagens _postagens;

    public RepositorioDePerfis getRepositorioDePerfis() {
        return _perfis;
    }

    public RepositorioDePostagens getRepositorioDePostagens() {
        return _postagens;
    }

    public RedeSocial(RepositorioDePerfis perfis, RepositorioDePostagens postagens) {
        this._perfis = perfis;
        this._postagens = postagens;
    }

    public void incluirPerfil(Perfil perfil){

        boolean atributosPreenchidos = perfil.getId() != null && !perfil.getNome().isBlank() && !perfil.getEmail().isBlank();
        boolean taDuplicado = getRepositorioDePerfis().consultar(perfil.getId(), perfil.getNome(), perfil.getEmail()) == null ? false : true;
    
        if(atributosPreenchidos && !taDuplicado)
            getRepositorioDePerfis().incluir(perfil);

    }

    public Perfil consultarPerfil(Integer id, String nome, String email){  
        return getRepositorioDePerfis().consultar(id,nome,email);
            
    }

    public void incluirPostagem(Postagem postagem){
        
        boolean atributosPreenchidos = postagem.getId() != null && !postagem.getTexto().isBlank() && postagem.getData() != null && postagem.getPerfil() != null && postagem.getCurtidas() >= 0 && postagem.getDescurtidas() >= 0;
        
        if(!atributosPreenchidos)
            throw new NullAtributesException();
            
        boolean taRepetido = false;
        for(Postagem post : getRepositorioDePostagens().getPostagens()){
            if(postagem.getId() == post.getId()){
                taRepetido = true;
                break;
            }                
        }
        
        if(taRepetido){
           throw new UserAlreadyExistsException(); 
        }        
        getRepositorioDePostagens().incluir(postagem);
    }

    public ArrayList<Postagem> consultarPostagens(Integer id,String texto, String hashtag,Perfil perfil){
        return getRepositorioDePostagens().consultar(id,texto,hashtag,perfil);
    }

    public void curtir(Integer id){
        Postagem post = getRepositorioDePostagens().consultar(id);
        if(post != null)
            post.curtir();
      
    }

    public void descurtir(Integer id){
        Postagem post = getRepositorioDePostagens().consultar(id);
        if(post != null)
            post.descurtir();
                
    
    }
    public void decrementarVisualizacoes(PostagemAvancada postagem){
        postagem.decrementarVisualizacoes();
    }
    

    public ArrayList<Postagem> exibirPostagensPorPerfil(Integer idPerfil) { 
        
        return null;
    }
}








