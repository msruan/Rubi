package com.ruanbianca.redesocial;
import java.util.ArrayList;
import java.util.Optional;

public class Perfil{

    private static int numeroDePerfis = 0;
    private String _username;
    private String _nome;
    private String _email;
    private ArrayList<Postagem> _postagens;
    private Integer _id;
    

    public Perfil(String username, String nome, String email) throws NullAtributesException{

        if(Optional.ofNullable(username).isEmpty() || Optional.ofNullable(nome).isEmpty() || Optional.ofNullable(email).isEmpty())
            throw new NullAtributesException();

        this._username = username;
        this._nome = nome;
        this._email = email;
        this._postagens = new ArrayList<>();
        this._id = numeroDePerfis;
        numeroDePerfis++;
    }

    public void setUsername(String username) {
        if(Optional.ofNullable(username).isPresent())
            this._username = username;
    }

    public void setNome(String nome) {
        if(Optional.ofNullable(nome).isPresent())
            this._nome = nome;
    }

    public void setEmail(String email) {
        if(Optional.ofNullable(email).isPresent())
            this._email = email;
    }

    public String getUsername() {
        return this._username;
    }

    public String getNome() {
        return this._nome;
    }

    public String getEmail() {
        return this._email;
    }

    public ArrayList<Postagem> getPostagens() {
        return this._postagens;
    }

    public Integer getId() {
        return this._id;
    }

    public boolean temAtributosNulos(){
    
        return (Optional.ofNullable(getId()).isEmpty() || Optional.ofNullable(getUsername()).isEmpty() || 
            Optional.ofNullable(getNome()).isEmpty() || Optional.ofNullable(getEmail()).isEmpty()
        );
    }

  

}
