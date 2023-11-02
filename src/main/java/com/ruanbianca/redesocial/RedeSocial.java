package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;

public class RedeSocial {
    private RepositorioDePerfis _perfis;
    private RepositorioDePostagens _postagens;

    public RedeSocial() {
        this._perfis = new RepositorioDePerfis();
        this._postagens = new RepositorioDePostagens();
    }

    public RedeSocial(RepositorioDePerfis perfis, RepositorioDePostagens postagens) {

        this._perfis = (Optional.of(perfis).isEmpty()) ? new RepositorioDePerfis() : perfis;
        this._postagens = (Optional.of(postagens).isEmpty()) ? new RepositorioDePostagens() : postagens;

    }

    public RepositorioDePerfis getRepositorioDePerfis() {
        return _perfis;
    }

    public RepositorioDePostagens getRepositorioDePostagens() {
        return _postagens;
    }

    public void incluirPerfil(Perfil perfil){

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se postagem for nunla

        if(perfil.temAtributosNulos())
            throw new NullAtributesException();

        boolean taDuplicado = getRepositorioDePerfis().usuarioJaExiste(perfil.getId(), perfil.getUsername(), perfil.getEmail());

        if(!taDuplicado)
            getRepositorioDePerfis().incluir(perfil);
        else 
            throw new UserAlreadyExistsException();
        //aqui a gente precisa retornar o que q ta duplicado, se é o username, email ou id;

    }

    public boolean usuarioJaExite(Integer id, String nome, String email){  
        return getRepositorioDePerfis().usuarioJaExiste(id,nome,email);
            
    }

    public void incluirPostagem(Postagem postagem) throws NullAtributesException{

        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se postagem for nunla
        
        if(postagem.temAtributosNulos())
            throw new NullAtributesException();
            
        boolean taRepetido = _postagens.consultar(postagem.getId()).isEmpty() ? false : true;

        if(taRepetido){
           throw new UserAlreadyExistsException(); 
        }
        getRepositorioDePostagens().incluir(postagem);
        //aqui a gente precisa retornar que o id tá duplicado;
    }

    public ArrayList<Postagem> consultarPostagens(String texto,Perfil perfil, Hashtag hashtag){//a gente deveria poder passar várias hashtags

        return getRepositorioDePostagens().consultar(texto,perfil,hashtag);
    }

    public Optional<Perfil> consultarPerfil(Integer id){//a gente deveria poder passar várias hashtags
        return getRepositorioDePerfis().consultarPorId(id);
    }

    public void curtir(int id) throws PostNotFoundException{

        Optional <Postagem> post = getRepositorioDePostagens().consultar(id);
        post.orElseThrow(PostNotFoundException::new);//aqui ele lanca uma excecao se tiver vazio
        post.get().curtir();
    }

    public void descurtir(int id) throws PostNotFoundException{
        
        Optional <Postagem> post = getRepositorioDePostagens().consultar(id);
        post.orElseThrow(PostNotFoundException::new);
        post.get().descurtir();
    }

    public void decrementarVisualizacoes(PostagemAvancada postagem) throws NullObjectAsArgumentException{
        
        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);
        postagem.decrementarVisualizacoes();
    }
    
    public ArrayList<Postagem> exibirPostagensPorPerfil(Integer idPerfil) { 

        Optional <Perfil> perfil = consultarPerfil(idPerfil);
        if(perfil.isEmpty())
            return null;
        Stream <Postagem> filtrados = perfil.get().getPostagens().stream();
        filtrados = filtrados.filter(post -> {
            if(!(post instanceof PostagemAvancada))
                return true;
            else if(((PostagemAvancada)post).ehExibivel()){
                ((PostagemAvancada)post).decrementarVisualizacoes();
                return true;
            }return false;
        });
        List<Postagem> saida = (filtrados.sorted( (o1, o2)->o2.getData().compareTo(o1.getData()) ).toList());
        return new ArrayList<>(saida);
    }

    public ArrayList<PostagemAvancada> exibirPostagensPorHashtag(Hashtag hashtag){
        
        Stream <Postagem> filtrados = getRepositorioDePostagens().getPostagens().stream();
        filtrados = filtrados.filter(post -> {
            if(((PostagemAvancada)post).ehExibivel() && ((PostagemAvancada)post).existeHashtag(hashtag)){
                ((PostagemAvancada)post).decrementarVisualizacoes();
                return true;
            }return false;
        }); 
        List <PostagemAvancada> saida = new ArrayList<>();
        filtrados.sorted( (o1, o2)->o2.getData().compareTo(o1.getData()) ).forEach(p -> saida.add((PostagemAvancada)p));
        return new ArrayList<>(saida);
    }
    
    //a. Exibir as postagens populares que ainda podem ser exibidas; 
    //b. Exibir as hashtags mais populares, ou seja, as que estão presentes em mais postagens;
    
    public ArrayList<Postagem> exibirPostagensPopulares(){
        Stream <Postagem> filtrados = getRepositorioDePostagens().getPostagens().stream();
        filtrados = filtrados.filter(post ->  {
            if( !(post instanceof PostagemAvancada) || ((PostagemAvancada)post).ehExibivel()){
                return post.ehPopular();
            }return false;
        });
        List <Postagem> saida = filtrados.sorted( (o1, o2)->o2.getData().compareTo(o1.getData()) ).toList();
        return new ArrayList<>(saida);
    }

    public ArrayList<Hashtag> exibirHashtagsPopulares(){
        return null;
    }
}




