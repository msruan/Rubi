package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PostagemAvancada extends Postagem {

    private int _visualizacoesRestantes;

    private ArrayList<String> _hashtags;

    public PostagemAvancada(Integer id, String texto, int curtidas, int descurtidas, LocalDateTime data, Perfil perfil, int visualizacoesRestantes, ArrayList<String> hashtags) {
        super(id, texto, curtidas, descurtidas, data, perfil);
        this._visualizacoesRestantes = visualizacoesRestantes;
                this._hashtags = hashtags;

    }

    public PostagemAvancada(Integer id, String texto, int curtidas, int descurtidas, LocalDateTime data, Perfil perfil, int visualizacoesRestantes, String ... hashtags) {
        super(id, texto, curtidas, descurtidas, data, perfil);
        this._visualizacoesRestantes = visualizacoesRestantes;
                this._hashtags = new ArrayList<>(Arrays.asList(hashtags));

    }

    public ArrayList<String> getHashtags (){
        return _hashtags;
    }

    public void adicionarHashtag(String hashtag) {
        _hashtags.add(hashtag);
    }

    public boolean existeHashtag(String hashtag) {
        return _hashtags.contains(hashtag);
    }
    public void decrementarVisualizacoes() {
        if(_visualizacoesRestantes>0)
            _visualizacoesRestantes--;
    }

}
