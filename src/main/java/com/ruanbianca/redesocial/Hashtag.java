package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;

public class Hashtag {

    @Override
    public boolean equals (Object hashtag) {
        if(hashtag instanceof Hashtag)
            return this._hashtag.equals(((Hashtag)hashtag).getHashtag());
        return false;
    }
    
    private int _contadorDeUsos;
    private String _hashtag;

    public Hashtag(String hashtag) throws NullAtributesException{
        Optional<String> temHash = Optional.ofNullable(hashtag);
        temHash.orElseThrow(NullAtributesException::new); 
        this._hashtag = hashtag;
        usarHashtag();
    }

    public String getHashtag() {
        return this._hashtag;
    }

    public int getContadorDeUsos() {
        return _contadorDeUsos;
    }
    public void usarHashtag() {
        _contadorDeUsos++;
    }

    public static ArrayList<Hashtag> gerarHashtags(String[] hashtags) {
        ArrayList<Hashtag> arrHashs = new ArrayList<>();
        for(String hash : hashtags){
            if(Optional.ofNullable(hash).isPresent())
                arrHashs.add(new Hashtag(hash));
        }return arrHashs;
    }
}
