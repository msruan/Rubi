package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class PostagemAvancada extends Postagem {

    private final Integer numeroPadraoVisualizacoesRestantes = 10;

    private Integer _visualizacoesRestantes;
    private ArrayList<Hashtag> _hashtags;
    
    public PostagemAvancada(String texto, Perfil perfil, ArrayList<Hashtag> hashtags) {
        
        super(texto, perfil);
        this._visualizacoesRestantes = numeroPadraoVisualizacoesRestantes;
        this._hashtags = (Optional.ofNullable(hashtags).isEmpty()) ? new ArrayList<>() : hashtags;
    }

    public PostagemAvancada(String texto, Perfil perfil, String ... hashtags) {

        this(texto, perfil, Hashtag.gerarHashtags(hashtags));
    }

    public void adicionarHashtag(String hashtag) {
        if(Optional.ofNullable(hashtag).isPresent())
            _hashtags.add(new Hashtag(hashtag));
    }

     // public boolean existeHashtag(Hashtag hashtag) {
    //     return _hashtags.contains(hashtag);
    // }

    public boolean ehExibivel(){
        return _visualizacoesRestantes > 0;
    }

    public boolean existeHashtag(Hashtag hashtag) {
        
        Stream <Hashtag> hashs = _hashtags.stream();

        return hashs.anyMatch(h -> h.equals(hashtag));
    }

    public void decrementarVisualizacoes() {
        if(_visualizacoesRestantes>0)
            _visualizacoesRestantes--;
    }

    public ArrayList<Hashtag> getHashtags (){
        return _hashtags;
    }

    public Integer getVisualizacoesRestantes() {
        return _visualizacoesRestantes;
    }

}
