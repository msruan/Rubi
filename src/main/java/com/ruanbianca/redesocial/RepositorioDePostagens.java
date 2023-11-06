package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class RepositorioDePostagens {

    private ArrayList<Postagem> _postagens;

    public RepositorioDePostagens() {
        this._postagens = new ArrayList<>();
    }

    public RepositorioDePostagens(ArrayList<Postagem> postagens) {

        if(Optional.ofNullable(postagens).isEmpty())
            this._postagens = new ArrayList<>();
        else
            this._postagens = postagens;
    }

    public RepositorioDePostagens(Postagem[] postagens) {
        this(new ArrayList<>(Arrays.asList(postagens)));
    }

    public void incluir(Postagem postagem){
        if(Optional.ofNullable(postagem).isPresent())
            _postagens.add(postagem);
    }

    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag){

        Stream <Postagem> filtrados = _postagens.stream();

        if(Optional.ofNullable(perfil).isPresent()){
            filtrados = filtrados.filter(
                post -> post.getPerfil().equals(perfil));
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

    public Optional<Postagem> consultarPostagemPorId(UUID id) {

        Optional<Postagem> saida = Optional.empty();

        for(Postagem post : _postagens){
            if(post.getId() == id){
                saida = Optional.of(post);
                break;
            }
        }return saida;
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

    public void removerPostagem(String texto, Perfil perfil,String hashtag){
        ArrayList<Postagem> postagensARemover = consultarPostagens(texto, perfil, hashtag);
        if(Optional.ofNullable(postagensARemover).isPresent()){
            _postagens.removeAll(postagensARemover);
        }else{
            System.out.println("Não há postagens para remover");
        }
    }
}