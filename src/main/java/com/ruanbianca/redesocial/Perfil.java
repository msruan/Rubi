package com.ruanbianca.redesocial;

import java.util.Optional;
import java.util.UUID;

import lombok.Getter;

public class Perfil{

    @Getter
    private String username;
    @Getter
    private String nome;
    @Getter
    private String email;
    @Getter
    private UUID id;

    @Override
    public String toString() {
        return id.toString() + ";" + username + ";" + nome +  ";" + email + '\n';
    }

    public Perfil(String perfil) { 
        
        // *  IdPerfil  |    Username |  Nome  |    Email        |
        String []atributos = perfil.split(";");
        this.id = UUID.fromString(atributos[0]);
        this.username = atributos[1];
        this.nome = atributos[2];
        this.email = atributos[3];
    }
    
    public Perfil(String username, String nome, String email) throws NullAtributesException{

        if(Optional.ofNullable(username).isEmpty() || Optional.ofNullable(nome).isEmpty() || Optional.ofNullable(email).isEmpty())
            throw new NullAtributesException();

        this.username = username;
        this.nome = nome;
        this.email = email;
        this.id = UUID.randomUUID();
    }


    public boolean temAtributosNulos(){
    
        return (Optional.ofNullable(getId()).isEmpty() || Optional.ofNullable(getUsername()).isEmpty() || 
            Optional.ofNullable(getNome()).isEmpty() || Optional.ofNullable(getEmail()).isEmpty()
        );
    }

    public void setUsername(String username) {
        if(Optional.ofNullable(username).isPresent())
            this.username = username;
    }

    public void setNome(String nome) {
        if(Optional.ofNullable(nome).isPresent())
            this.nome = nome;
    }

    public void setEmail(String email) {
        if(Optional.ofNullable(email).isPresent())
            this.email = email;
    }
}
