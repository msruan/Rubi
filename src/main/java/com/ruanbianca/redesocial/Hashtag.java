package com.ruanbianca.redesocial;

import java.util.Optional;
import lombok.Getter;

public class Hashtag {


    private @Getter String hashtag;
    private Integer _contadorDeUsos;


    @Override
    public boolean equals (Object hashtag) {
        if(hashtag instanceof Hashtag)
            return this.hashtag.equals(((Hashtag)hashtag).getHashtag());
        return false;
    }
    

    public Hashtag(String hashtag, int contadorDeUsos) throws NullAtributesException{
        Optional<String> temHash = Optional.ofNullable(hashtag);
        temHash.orElseThrow(NullAtributesException::new); 
        this.hashtag = hashtag;
        this._contadorDeUsos = contadorDeUsos;
    }


    public Integer getContadorDeUsos() {
        return _contadorDeUsos;
    }


    public void usarHashtag() {
        _contadorDeUsos++;
    }
}