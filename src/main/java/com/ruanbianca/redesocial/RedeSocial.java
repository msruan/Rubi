package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.io.BufferedWriter;
import java.io.FileWriter;

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

    public boolean usuarioJaExite(UUID id, String nome, String email){  
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

    public ArrayList<Postagem> consultarPostagens(String texto,Perfil perfil, String hashtag){//a gente deveria poder passar várias hashtags

        return getRepositorioDePostagens().consultar(texto,perfil,hashtag);
    }

    public Optional<Perfil> consultarPerfil(UUID id){//a gente deveria poder passar várias hashtags
        return getRepositorioDePerfis().consultarPorId(id);
    }

    public void curtir(UUID id) throws PostNotFoundException{

        Optional <Postagem> post = getRepositorioDePostagens().consultar(id);
        post.orElseThrow(PostNotFoundException::new);//aqui ele lanca uma excecao se tiver vazio
        post.get().curtir();
    }

    public void descurtir(UUID id) throws PostNotFoundException{
        
        Optional <Postagem> post = getRepositorioDePostagens().consultar(id);
        post.orElseThrow(PostNotFoundException::new);
        post.get().descurtir();
    }

    public void decrementarVisualizacoes(PostagemAvancada postagem) throws NullObjectAsArgumentException{
        
        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);
        postagem.decrementarVisualizacoes();
    }
    
    public ArrayList<Postagem> exibirPostagensPorPerfil(UUID idPerfil) { 

        Optional <Perfil> perfil = consultarPerfil(idPerfil);
        if(perfil.isEmpty())
            return null;
        
        Stream <Postagem> filtrados = getRepositorioDePostagens().getPostagens().stream();
        filtrados = filtrados.filter(post -> post.getPerfil().getId() == perfil.get().getId());
        //Stream <Postagem> filtrados = perfil.get().getPostagens().stream();
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

    public ArrayList<PostagemAvancada> exibirPostagensPorHashtag(String hashtag){
        
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

        Map<String,Integer> mapaHashtags = new HashMap<>();
        Stream <PostagemAvancada> postagens = getRepositorioDePostagens().getPostagensAvancadas().stream();
        postagens.forEach(post -> {
            for(String hashtag : post.getHashtags()){

                if(Optional.ofNullable(hashtag).isEmpty())
                    continue;//acho q i sso aqui basta
                    
                if(mapaHashtags.containsKey(hashtag)){
                    int numeroDeUsos = mapaHashtags.get(hashtag);
                    numeroDeUsos++;
                    mapaHashtags.put(hashtag,numeroDeUsos);
                
                }else 
                    mapaHashtags.put(hashtag,1);
            }
        });
      
        ArrayList<Hashtag> asMaisHypadas = new ArrayList<>();
        for(Map.Entry<String,Integer> par : mapaHashtags.entrySet()){
            asMaisHypadas.add(new Hashtag(par.getKey(), par.getValue()));
        }
        Stream <Hashtag> streamHashs = asMaisHypadas.stream().sorted((h1,h2) -> h2.getContadorDeUsos().compareTo(h1.getContadorDeUsos()));
        return new ArrayList<>(streamHashs.toList());

    } 
    public void criarDB(){
        String diretorio_atual = System.getProperty("user.dir");
        
    }
    public void salvarRegistros(String nomeArquivo) {
        try ( BufferedWriter buffwriter = new BufferedWriter(new FileWriter(nomeArquivo))){
            for(Perfil perfil : _perfis){
                buffwriter.write(perfil.toString());
                buffwriter.newLine();
            }
        } ca
    }
}



