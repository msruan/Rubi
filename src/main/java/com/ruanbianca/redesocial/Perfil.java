package com.ruanbianca.redesocial;

import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

public class Perfil{

    private @Getter UUID id;
    private @Getter @Setter String username;
    private @Getter @Setter String nome;
    private @Getter @Setter String email;
    private @Getter @Setter String biografia;
    
    public Perfil(String username, String nome, String email, String biografia) throws NullAtributesException{

        if(Optional.ofNullable(username).isEmpty() || Optional.ofNullable(nome).isEmpty() || Optional.ofNullable(email).isEmpty() || Optional.ofNullable(biografia).isEmpty())
            throw new NullAtributesException();

        this.username = username;
        this.nome = nome;
        this.email = email;
        this.id = UUID.randomUUID();
        this.biografia = biografia;
    }


    public Perfil(UUID id, String username, String nome, String email, String biografia) throws NullAtributesException{
        
        this(username, nome, email, biografia);
        Optional.ofNullable(id).orElseThrow(NullAtributesException::new);
        this.id = id;
    }
}