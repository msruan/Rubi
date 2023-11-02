package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.Optional;
import java.util.stream.Stream;

public class RepositorioDePostagens {
    private ArrayList<Postagem> _postagens;

    public ArrayList<Postagem> getPostagens() {
        return _postagens;
    }

    public RepositorioDePostagens(ArrayList<Postagem> postagens) {
        this._postagens = postagens;
    }

    public RepositorioDePostagens(Postagem ... postagens) {
        this._postagens = new ArrayList<>();
        for(Postagem post : postagens){
            incluir(post);
        }
    }

    public void incluir(Postagem postagem){
        _postagens.add(postagem);
    }

    public ArrayList<Postagem> consultar(Integer id,String texto, String hashtag, Perfil perfil){

        Stream <Postagem> postsFiltrados = _postagens.stream();

        if(perfil!= null)

            postsFiltrados = postsFiltrados.filter(
                post -> post.getPerfil().equals(perfil));

        if(hashtag != null) {
            // adicionar Optional

            postsFiltrados = postsFiltrados.filter(
                post -> post instanceof PostagemAvancada && (
                    (PostagemAvancada)post).getHashtags().contains(hashtag) );

        }

        if(id != null) {
            postsFiltrados = postsFiltrados.filter(
                post -> post.getId().toString().contains(id.toString()) );
        }

        if(texto != null) {
            postsFiltrados = postsFiltrados.filter(post -> post.getTexto().contains(texto));
        }
        return new ArrayList<Postagem>(postsFiltrados.toList());
        

    }

    public Postagem consultar(int id) {
        for(Postagem post : _postagens){
            if(post.getId().equals(id)){
                return post;
            }
        }return null;
    }

    public static void main(String[] args) {
        RepositorioDePostagens posts = new RepositorioDePostagens(new Postagem(12346789,"só bebo coca",5,7,LocalDateTime.now(),null));
        PostagemAvancada post2 = new PostagemAvancada(3389, "ouviram do ipiranga as margens placiadas", 0, 20,null,null,1000,"euqfiz","omg");
        posts.incluir(post2);
                System.out.println(posts.consultar(89,null,null,null).size());
    
                        System.out.println(posts.consultar(89,"ipiranga","omg",null).size());

    }
}
