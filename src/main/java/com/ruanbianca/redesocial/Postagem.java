package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.util.Optional;

public class Postagem {

    private static int contadorDePostagens = 0;

    private String _texto;
    private Perfil _perfil;   
    private LocalDateTime _data;
    private int _curtidas;
    private int _descurtidas;
    private Integer _id;

    public Postagem(String texto, Perfil perfil) throws NullAtributesException{

        if(Optional.ofNullable(texto).isEmpty() || Optional.ofNullable(perfil).isEmpty())
            throw new NullAtributesException();

        this._texto = texto;
        this._perfil = perfil;
        this._curtidas = 0;
        this._descurtidas = 0;
        this._data = LocalDateTime.now();
        this._id = contadorDePostagens;

        contadorDePostagens++;
        this._perfil.getPostagens().add(this);
    }

    public boolean ehPopular(){
        return (_curtidas - _descurtidas) >= 0.5 * _descurtidas;
    }

    public void curtir(){
        _curtidas++;
    }

    public void descurtir(){
        _descurtidas++;
    }

    public boolean temAtributosNulos () {
        return(Optional.ofNullable(_id).isEmpty() || Optional.ofNullable(_texto).isEmpty() || 
            Optional.ofNullable(_data).isEmpty() || Optional.ofNullable(_perfil).isEmpty() || 
            Optional.ofNullable(_curtidas).isEmpty() || Optional.ofNullable(_descurtidas).isEmpty());
    }

    public Integer getId() {
        return this._id;
    }

    public String getTexto() {
        return this._texto;
    }

    public int getCurtidas() {
        return this._curtidas;
    }

    public int getDescurtidas() {
        return this._descurtidas;
    }

    public LocalDateTime getData() {
        return this._data;
    }

    public Perfil getPerfil() {
        return this._perfil;
    }

}
