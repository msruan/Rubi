package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class RepositorioDePostagens {

    private ArrayList<Postagem> _postagens;

    public ArrayList<Postagem> getPostagens() {
        return _postagens;
    }

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

    public ArrayList<Postagem> consultar(String texto, Perfil perfil, String hashtag){

        Stream <Postagem> filtrados = _postagens.stream();

        if(Optional.ofNullable(perfil).isPresent()){
            filtrados = filtrados.filter(
                post -> post.getPerfil().equals(perfil));
        }

        if(Optional.ofNullable(hashtag).isPresent()) {
            filtrados = filtrados.filter(
                post -> post instanceof PostagemAvancada && (
                    (PostagemAvancada)post).getHashtags().contains(hashtag) );
        }

        if(Optional.ofNullable(texto).isPresent()) {
            filtrados = filtrados.filter(post -> post.getTexto().contains(texto));
        }

        return new ArrayList<Postagem>(filtrados.toList());
    }

    public Optional<Postagem> consultar(Integer id) {

        Optional<Postagem> saida = Optional.empty();

        for(Postagem post : _postagens){
            if(post.getId().equals(id)){
                saida = Optional.of(post);
                break;
            }
        }return saida;

    }
}