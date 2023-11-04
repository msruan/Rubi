package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;

public class PostagemAvancada extends Postagem {

    private final Integer numeroPadraoVisualizacoesRestantes = 10;
    @Getter
    private Integer visualizacoesRestantes;
    @Getter
    private ArrayList<String> hashtags;
    
    public PostagemAvancada(String texto, Perfil perfil, ArrayList<String> hashtags) {
        
        super(texto, perfil);
        this.visualizacoesRestantes = numeroPadraoVisualizacoesRestantes;
        this.hashtags = (Optional.ofNullable(hashtags).isEmpty()) ? new ArrayList<>() : hashtags;
    }

    public PostagemAvancada(String texto, Perfil perfil, String ... hashtags) {

        this(texto, perfil, new ArrayList<>(Arrays.asList(hashtags)));
    }

    public void adicionarHashtag(String hashtag) {
        if(Optional.ofNullable(hashtag).isPresent())
            hashtags.add(hashtag);
    }

    public boolean ehExibivel(){
        return visualizacoesRestantes > 0;
    }

    public boolean existeHashtag(String hashtag) {
        
        Stream <String> hashs = hashtags.stream();

        return hashs.anyMatch(h -> h.equals(hashtag));
    }

    public void decrementarVisualizacoes() {
        if(ehExibivel())
            visualizacoesRestantes--;
    }

}
