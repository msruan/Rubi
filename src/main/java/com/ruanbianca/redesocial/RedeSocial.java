package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;

public class RedeSocial {
    private RepositorioDePerfis _perfis;
    private RepositorioDePostagens _postagens;

    public RedeSocial() {
        this._perfis = new RepositorioDePerfis();
        this._postagens = new RepositorioDePostagens();
    }

    public RedeSocial(RepositorioDePerfis perfis, RepositorioDePostagens postagens) {

        this._perfis = (Optional.of(perfis).isEmpty()) ? new RepositorioDePerfis() : perfis;
        this._postagens = (Optional.of(postagens).isEmpty()) ? new RepositorioDePostagens() : postagens;

    }

    public RepositorioDePerfis getRepositorioDePerfis() {
        return _perfis;
    }

    public RepositorioDePostagens getRepositorioDePostagens() {
        return _postagens;
    }

    public void incluirPerfil(Perfil perfil){

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se postagem for nunla

        if(perfil.temAtributosNulos())
            throw new NullAtributesException();

        boolean taDuplicado = getRepositorioDePerfis().usuarioJaExiste(perfil.getId(), perfil.getUsername(), perfil.getEmail());

        if(!taDuplicado)
            getRepositorioDePerfis().incluir(perfil);
        else 
            throw new UserAlreadyExistsException();
        //aqui a gente precisa retornar o que q ta duplicado, se é o username, email ou id;

    }

    public boolean usuarioJaExite(Integer id, String nome, String email){  
        return getRepositorioDePerfis().usuarioJaExiste(id,nome,email);
            
    }

    public void incluirPostagem(Postagem postagem) throws NullAtributesException{

        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se postagem for nunla
        
        if(postagem.temAtributosNulos())
            throw new NullAtributesException();
            
        boolean taRepetido = _postagens.consultar(postagem.getId()).isEmpty() ? false : true;

        if(taRepetido){
           throw new UserAlreadyExistsException(); 
        }
        getRepositorioDePostagens().incluir(postagem);
        //aqui a gente precisa retornar que o id tá duplicado;
    }

    public ArrayList<Postagem> consultarPostagens(String texto,Perfil perfil, String hashtag){//a gente deveria poder passar várias hashtags

        return getRepositorioDePostagens().consultar(texto,perfil,hashtag);
    }

    public void curtir(int id) throws PostNotFoundException{

        Optional <Postagem> post = getRepositorioDePostagens().consultar(id);
        post.orElseThrow(PostNotFoundException::new);//aqui ele lanca uma excecao se tiver vazio
        post.get().curtir();
    
    }

    public void descurtir(int id) throws PostNotFoundException{
        
        Optional <Postagem> post = getRepositorioDePostagens().consultar(id);
        post.orElseThrow(PostNotFoundException::new);
        post.get().descurtir();
    
    }

    public void decrementarVisualizacoes(PostagemAvancada postagem) throws NullObjectAsArgumentException{
        
        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);
        postagem.decrementarVisualizacoes();
    }
    
    public ArrayList<Postagem> exibirPostagensPorPerfil(Integer idPerfil) { 
        return null;
    }
}








