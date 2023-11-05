package com.ruanbianca.redesocial;

import com.ruanbianca.redesocial.utils.ManipuladorDeArquivos;//pera
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;//
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class RedeSocial {
    private RepositorioDePerfis _perfis;
    private RepositorioDePostagens _postagens;

    public RedeSocial() {
        this._perfis = new RepositorioDePerfis();
        this._postagens = new RepositorioDePostagens();
    }//:OOOOO
    public String getCaminhoDoBancoDeDados(String entidade) throws BadChoiceOfEntityForDB{
        if(entidade.equals("Perfil"))
            return System.getProperty("user.dir")+"/db/perfis.txt";
        else if(entidade.equals("Postagem"))
            return System.getProperty("user.dir")+"/db/postagens.txt";
        else {
            throw new BadChoiceOfEntityForDB();
        }

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
    public Optional<Perfil> consultarPerfilPorUsername(String username){//a gente deveria poder passar várias hashtags
        return getRepositorioDePerfis().consultarPorUsername(username);
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
    
    public ArrayList<Postagem> exibirPostagensPorPerfil(String username) { 

        Optional <Perfil> perfil = consultarPerfilPorUsername(username);
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
   
    public void salvarPerfis(String nomeArquivo) {
        
        try ( BufferedWriter buffwriter = new BufferedWriter(new FileWriter(nomeArquivo))){
            for(Perfil perfil : _perfis.getPerfis()){
                buffwriter.write(perfil.toString());
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch(RuntimeException e){
                System.out.println("O erro tá na funcao salvarPerfis");
            }
        }    
    public void salvarPostagens(String nomeArquivo) {
        try ( BufferedWriter buffwriter = new BufferedWriter(new FileWriter(nomeArquivo))){
            for(Postagem post : _postagens.getPostagens()){
                String postagem = (post instanceof PostagemAvancada) ? ((PostagemAvancada)post).toString() : post.toString();
                buffwriter.write(postagem);
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch(RuntimeException e){
                System.out.println("O erro tá na funcao salvarPostagens");
            }
    }

    public void resgatarPerfis(String nomeArquivo){
            System.out.println("entrou em resgatar perfis");

            try {
                ArrayList<String> linhas = ManipuladorDeArquivos.lerLinhas(nomeArquivo);
                System.out.println(linhas.size());
                for(String linha: linhas){//frescura
                    incluirPerfil(new Perfil(linha));
                    
                }
            System.out.println("saiu de resgata perfis");

            }catch(RuntimeException e){
                System.out.println("O erro tá na funcao resgatarPerfis");
            }
    }

    public void resgatarPostagens(String nomeArquivo){//amg, mas n tá funcionadno nao? sei q tá pegandp uma excecao
        ArrayList <String> conteudo = ManipuladorDeArquivos.lerLinhas(nomeArquivo);
        for(String linha : conteudo){//vou olhar
            
            String[] atributos = linha.split(";");
                if(atributos[0] == "0"){
                    incluirPostagem(new Postagem(consultarPerfil(UUID.fromString(atributos[2])).get(),linha));
                }else {
                    System.out.println(atributos[2]);
                    incluirPostagem(new PostagemAvancada(consultarPerfil(UUID.fromString(atributos[2])).get(),linha));
                }
        }
    }
    
    public static String lerArquivo(String caminho){
        StringBuilder conteudo = new StringBuilder();
        BufferedReader leitor;
        try {
            leitor = new BufferedReader(new FileReader(caminho));
            try{
                String linha = leitor.readLine();
                for(; linha != null; linha = leitor.readLine())
                    conteudo.append(linha);
                leitor.close();
            }catch(IOException e){
                System.out.println("Problema durante leitura do arquivo!");
            }
        }catch (FileNotFoundException e){
            System.out.println("Arquivo não encontrado!");
        } 
    
        return conteudo.toString();
    }

}