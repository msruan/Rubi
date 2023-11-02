package com.ruanbianca.redesocial;
import java.util.ArrayList;

public class Perfil{
    private Integer _id;
    private String _nome;
    private String _email;
    private ArrayList<Postagem> _postagens;

    public Perfil(Integer id, String nome, String email) {//pq a pessoa pode criar conta sem ter posts....
        this._id = id;
        this._nome = nome;
        this._email = email;
    }

    public Perfil(Integer id, String nome, String email, ArrayList<Postagem> postagens) {
        this._id = id;
        this._nome = nome;
        this._email = email;
        this._postagens = postagens;
    }

    public Integer getId() {
        return this._id;
    }

    public String getNome() {
        return this._nome;
    }
   

    public String getEmail() {
        return this._email;
    }

    public ArrayList<Postagem> getPosts() {
        return _postagens;
    }

}
