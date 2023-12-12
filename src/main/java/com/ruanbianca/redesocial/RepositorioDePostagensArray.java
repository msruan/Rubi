package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


public class RepositorioDePostagensArray implements IRepositorioDePostagens{


    private ArrayList<Postagem> _postagens;


    public RepositorioDePostagensArray() {
        this._postagens = new ArrayList<>();
    }


    public RepositorioDePostagensArray(ArrayList<Postagem> postagens) {

        if(Optional.ofNullable(postagens).isEmpty())
            this._postagens = new ArrayList<>();
        else
            this._postagens = postagens;
    }


    public RepositorioDePostagensArray(Postagem[] postagens) {
        this(new ArrayList<>(Arrays.asList(postagens)));
    }


    public ArrayList<Postagem> getPostagens() {
        return _postagens;
    }

    
    public ArrayList<PostagemAvancada> getPostagensAvancadas() {

        ArrayList<PostagemAvancada> avancadas = new ArrayList<>();
        Stream <Postagem> postagens = getPostagens().stream();
        postagens = postagens.filter(post -> post instanceof PostagemAvancada);
        postagens.forEach(post -> avancadas.add((PostagemAvancada)post));
        return avancadas;
    }


    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag){

        Stream <Postagem> filtrados = getPostagens().stream();

        if(Optional.ofNullable(perfil).isPresent()){
            filtrados = filtrados.filter(
                post -> post.getPerfilId().equals(perfil.getId()));
        }

        if(Optional.ofNullable(hashtag).isPresent()) {
            filtrados = filtrados.filter(
                post -> post instanceof PostagemAvancada && (
                    (PostagemAvancada)post).existeHashtag(hashtag));
        }

        if(Optional.ofNullable(texto).isPresent()) {
            filtrados = filtrados.filter(post -> post.getTexto().contains(texto));
        }

        return new ArrayList<Postagem>(filtrados.toList());
    }


    public Optional<Postagem> consultarPostagem(UUID id) {

        Optional<Postagem> saida = Optional.empty();

        for(Postagem post : _postagens){
            if(post.getId() == id){
                saida = Optional.of(post);
                break;
            }
        }return saida;
    }


    public void incluir(Postagem postagem) throws NullObjectAsArgumentException, UserAlreadyExistsException{

        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se postagem for nunla
            
        if(postagemJaExiste(postagem.getId()))
           throw new UserAlreadyExistsException(); 
        
        _postagens.add(postagem);
    }
   

    public boolean postagemJaExiste(UUID id){

        return consultarPostagem(id).isPresent();
    }

    public void removerPostPorPerfil(Perfil perfil){
            
        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
    
        Stream<Postagem> filtrados = getPostagens().stream();
        filtrados = filtrados.filter(post -> post.getPerfilId().equals(perfil.getId()));
        filtrados.forEach(post -> _postagens.remove(post));
    }
    //acho q incluisve, ele na vdd so nao vai ter copro. pq a gente ja altera direatmente. so deixa vaizo mesmo
    public void atualizarPostagem(Postagem post){
        return;
        //eh q como a gente ta trablahanod a nivel de array, so de fazer um crutir no objeto post, ja afeta
        //d
    }
}