package com.ruanbianca.redesocial;

import java.time.LocalDateTime;

public class Postagem {

    private Integer _id;
    private String _texto;
    private int _curtidas;
    private int _descurtidas;
    private LocalDateTime _data;
    private Perfil _perfil;//guardar só o id do usuario?


    public Postagem(Integer id, String texto, int curtidas, int descurtidas, LocalDateTime data, Perfil perfil) {
        this._id = id;
        this._texto = texto;
        this._curtidas = curtidas;
        this._descurtidas = descurtidas;
        this._data = data;
        this._perfil = perfil;
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

    public void curtir(){
        _curtidas++;
    }

    public void descurtir(){
        _descurtidas++;
    }

    public boolean ehPopular(){
        return (_curtidas - _descurtidas) >= 0.5 * _descurtidas;
    }

}
