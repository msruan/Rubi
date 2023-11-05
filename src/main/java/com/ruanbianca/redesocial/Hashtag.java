package com.ruanbianca.redesocial;

import java.util.Optional;

public class Hashtag {

    @Override
    public boolean equals (Object hashtag) {
        if(hashtag instanceof Hashtag)
            return this._hashtag.equals(((Hashtag)hashtag).getHashtag());
        return false;
    }
    
    private String _hashtag;
    private Integer _contadorDeUsos;


    public Hashtag(String hashtag, int contadorDeUsos) throws NullAtributesException{
        Optional<String> temHash = Optional.ofNullable(hashtag);
        temHash.orElseThrow(NullAtributesException::new); 
        this._hashtag = hashtag;
        this._contadorDeUsos = contadorDeUsos;
    }

    public String getHashtag() {
        return this._hashtag;
    }

    public Integer getContadorDeUsos() {
        return _contadorDeUsos;
    }
    public void usarHashtag() {
        _contadorDeUsos++;
    }
}//pq tu n coloca logo utils dentro de rede social? tadinho...queria fazer as coisas do jeito certo T-T