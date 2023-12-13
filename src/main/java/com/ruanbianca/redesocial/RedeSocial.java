package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.ruanbianca.redesocial.utils.ConsoleColors.*;

public class RedeSocial {


    private IRepositorioDePerfis _perfis;
    private IRepositorioDePostagens _postagens;


    public RedeSocial(IRepositorioDePerfis perfis, IRepositorioDePostagens postagens) throws NullObjectAsArgumentException{

        Optional.ofNullable(perfis).orElseThrow(NullObjectAsArgumentException::new);
        Optional.ofNullable(postagens).orElseThrow(NullObjectAsArgumentException::new);

        this._perfis = perfis;
        this._postagens = postagens;
    }


    public ArrayList<Postagem> consultarPostagens(String texto,Perfil perfil, String hashtag) throws Exception{//a gente deveria poder passar várias hashtags

        return _postagens.consultarPostagens(texto,perfil,hashtag);
    }


    public Optional<Perfil> consultarPerfil(UUID id) throws Exception{
        
        return _perfis.consultar(id, null, null);
    }

    public Optional<Perfil> consultarPerfilPorUsername(String username) throws Exception{
        
        return _perfis.consultar(null, username, null);
    }


    public boolean usuarioJaExite(UUID id, String username, String email) throws Exception{  
        
        return _perfis.usuarioJaExite(id, username, email);     
    }


    public void incluirPerfil(Perfil perfil) throws Exception{
        
        _perfis.incluir(perfil);
    }
    

    public void incluirPostagem(Postagem postagem) throws Exception{
        
        _postagens.incluir(postagem);
    }


    public void decrementarVisualizacoes(UUID id) throws Exception{

        Optional <Postagem> post = _postagens.consultarPostagem(id);
        if(post.isEmpty())
            throw new PostNotFoundException("Ocorreu na linha 70 de Rede Social!");
        ((PostagemAvancada)post.get()).decrementarVisualizacoes();
        _postagens.atualizarPostagem(post.get()); 
    }


    public void curtir(UUID id) throws Exception{

        Optional <Postagem> post = _postagens.consultarPostagem(id);
        if(post.isEmpty())
            throw new PostNotFoundException("Ocorreu na linha 70 de Rede Social!");
        post.get().curtir();
        _postagens.atualizarPostagem(post.get()); 
    }    


    public void descurtir(UUID id) throws Exception{
        
        Optional <Postagem> post = _postagens.consultarPostagem(id);
        post.orElseThrow(PostNotFoundException::new);
        post.get().descurtir();
        _postagens.atualizarPostagem(post.get());
    }


    public void decrementarVisualizacoes(PostagemAvancada postagem) throws NullObjectAsArgumentException{
        
        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);
        postagem.decrementarVisualizacoes();
    }


     public void removerPerfil(String username) throws Exception{
        Perfil per_fil = consultarPerfilPorUsername(username).orElseThrow(UserNotFoundException::new);
        _postagens.removerPostPorPerfil(per_fil);
        _perfis.removerPerfil(username);
    }


    public static String limitarBio(String bio){

        if(bio.length() > 22){
            bio = bio.substring(0,23) + "\n  " + bio.substring(23, bio.length());
        }
        return bio;
    }


    //Todo: tirar daqui e por em RedeSocial
    public static String exibirPerfil(Perfil perfil) {
  
        StringBuilder result = new StringBuilder();
        result.append("╭───────────────────────────────────╮\n");
        result.append("│" + CYAN_BOLD + "           Perfil Info" + RESET + "             │\n");
        result.append("├───────────────────────────────────┤\n");
        result.append("  " + YELLOW + "Username:" + RESET + " " + perfil.getUsername() + "\n");
        result.append("  " + YELLOW + "Nome:" + RESET + " " + perfil.getNome() + "\n");
        result.append("  " + YELLOW + "Email:" + RESET + " " + perfil.getEmail() + "\n");
        result.append("  " + YELLOW + "Biografia:" + RESET + " " + limitarBio(perfil.getBiografia()) + "\n");
        result.append("╰───────────────────────────────────╯");

        return result.toString();
    }
    

    public String exibirPostagem(Postagem postagem) throws Exception{
        
        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);

        Optional<Perfil> opPerfil = consultarPerfil(postagem.getPerfilId());

        if(opPerfil.isPresent()){

            Perfil perfil = opPerfil.get();

            if(postagem instanceof PostagemAvancada){

                PostagemAvancada postagemAv = (PostagemAvancada) postagem;//
                
                StringBuilder strHashtags = new StringBuilder();
                for(String hash:  postagemAv.getHashtags()){
                    strHashtags.append("#"+hash+" ");
                }
                return PURPLE_BOLD+"╔══════════════════════════════════════════════════════\n"+
                "║    "+postagem.getId()+""+perfil.getNome()+RESET+PURPLE_BRIGHT+" @"+ perfil.getUsername() + RESET+"\n║\n║    "+
                    postagem.getTexto()+"\n║    "+GREEN_BOLD_BRIGHT+strHashtags+RESET+"\n║\n║    "
                    +RED_BOLD_BRIGHT+postagem.getCurtidas()+" ❤️   " +RESET + YELLOW_BOLD_BRIGHT + postagem.getDescurtidas() +" 👎   "
                    +RESET+BLUE_BOLD_BRIGHT +postagemAv.getVisualizacoesRestantes()+ " 👀      "+RESET+"•" +postagem.mostrarData() + BLUE_BOLD+
                    "\n╚══════════════════════════════════════════════════════\n"+RESET;
            }

            else{
                return PURPLE_BOLD_BRIGHT+"╔══════════════════════════════════════════════════════\n"+
            "║    "+perfil.getId()+perfil.getNome()+RESET+PURPLE_BRIGHT+" @"+perfil.getUsername()+RESET+"\n║\n║    "+  
                postagem.getTexto()+"\n║\n║    "
                +RED_BOLD_BRIGHT+postagem.getCurtidas()+" ❤️   " +RESET + YELLOW_BOLD_BRIGHT + postagem.getDescurtidas() + " 👎"+RESET+"            •" +postagem.mostrarData() + YELLOW_BOLD
                + "\n╚══════════════════════════════════════════════════════\n"+RESET;

            }
        }
        throw new NullAtributesException();
    }
    

    public ArrayList<Postagem> exibirPostagensPorPerfil(String username) throws Exception { 
    
        Optional <Perfil> perfil = consultarPerfilPorUsername(username);
        if(perfil.isEmpty()){
            //Todo: remover esse print
            System.out.println("Username não encontrado!");
            return null;
        }else{
            System.out.println("Perfil encontrado");
        }
        
        Stream <Postagem> filtrados = _postagens.getPostagens().stream();
        filtrados = filtrados.filter(post -> {
            return post.getPerfilId().equals(perfil.get().getId());
        });
        
        filtrados = filtrados.filter(post -> {
            if(!(post instanceof PostagemAvancada))
                return true;
            else if(((PostagemAvancada)post).ehExibivel()){
                return true;
            }return false;
        });
        List<Postagem> saida = (filtrados.sorted( (o1, o2)->o2.getData().compareTo(o1.getData()) ).toList());
        return new ArrayList<>(saida);
    }
 

    public ArrayList<PostagemAvancada> exibirPostagensPorHashtag(String hashtag) throws Exception{
        
        Stream <PostagemAvancada> filtrados = _postagens.getPostagensAvancadas().stream();
        filtrados = filtrados.filter(post -> {
            if(post.ehExibivel() && post.existeHashtag(hashtag)){
                return true;
            }return false;
        }); 
        List <PostagemAvancada> saida = new ArrayList<>();
        filtrados.sorted( (o1, o2)->o2.getData().compareTo(o1.getData()) ).forEach(p -> saida.add((PostagemAvancada)p));
        return new ArrayList<>(saida);
    }


    public ArrayList<Postagem> exibirPostagensPopulares() throws Exception{
        
        Stream <Postagem> filtrados = _postagens.getPostagens().stream();
        filtrados = filtrados.filter(post ->  {
            if( !(post instanceof PostagemAvancada) || ((PostagemAvancada)post).ehExibivel()){
                return post.ehPopular();
            }return false;
        });
        List <Postagem> saida = filtrados.sorted( (o1, o2)->o2.getData().compareTo(o1.getData()) ).toList();
        return new ArrayList<>(saida);
    }


    public ArrayList<Hashtag> exibirHashtagsPopulares() throws Exception{

        Map<String,Integer> mapaHashtags = new HashMap<>();
        Stream <PostagemAvancada> postagens = _postagens.getPostagensAvancadas().stream();
        postagens.forEach(post -> {
            for(String hashtag : post.getHashtags()){

                if(Optional.ofNullable(hashtag).isEmpty())
                    continue;
                    
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


    public void atualizarPerfil(String username, String novoAtributo, String nomeAtributo) throws Exception{

        _perfis.atualizarPerfil(username, novoAtributo, nomeAtributo);
    }
}