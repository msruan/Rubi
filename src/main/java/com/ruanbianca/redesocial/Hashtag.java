package com.ruanbianca.redesocial;

import java.util.Optional;

public class Hashtag {


    private String _hashtag;
    private Integer _contadorDeUsos;


    @Override
    public boolean equals (Object hashtag) {
        if(hashtag instanceof Hashtag)
            return this._hashtag.equals(((Hashtag)hashtag).getHashtag());
        return false;
    }
    

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
}