package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import lombok.Getter;

public class Postagem {

    @Getter
    private String texto;
    @Getter
    private Perfil perfil; 
    @Getter  
    private LocalDateTime data;
    @Getter
    private int curtidas;
    @Getter
    private int descurtidas;
    @Getter
    private UUID id;

    public Postagem(String texto, Perfil perfil) throws NullAtributesException{

        if(Optional.ofNullable(texto).isEmpty() || Optional.ofNullable(perfil).isEmpty())
            throw new NullAtributesException();

        this.texto = texto;
        this.perfil = perfil;
        this.curtidas = 0;
        this.descurtidas = 0;
        this.data = LocalDateTime.now();
        this.id = UUID.randomUUID();
        this.perfil.getPostagens().add(this);
    }

    public boolean ehPopular(){
        return (curtidas - descurtidas) >= 0.5 * descurtidas;
    }

    public void curtir(){
        curtidas++;
    }

    public void descurtir(){
        descurtidas++;
    }

    public boolean temAtributosNulos () {
        return(Optional.ofNullable(id).isEmpty() || Optional.ofNullable(texto).isEmpty() || 
            Optional.ofNullable(data).isEmpty() || Optional.ofNullable(perfil).isEmpty() || 
            Optional.ofNullable(curtidas).isEmpty() || Optional.ofNullable(descurtidas).isEmpty());
    }
}
