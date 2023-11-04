package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
                List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
                for(String linha : linhas){
                    System.out.println("pegou");
                    Perfil perfil = new Perfil(linha);
                    incluirPerfil(perfil);
                }
                    
            System.out.println("saiu de resgata perfis");

            }catch(IOException e){
                System.out.println("Erro durante o resgate dos arquivos!");
            }catch(RuntimeException e){
                System.out.println("O erro tá na funcao resgatarPerfis");
            }
    }
    public void resgatarPerfis2(String nomeArquivo){
        try(Scanner input = new Scanner(new File(nomeArquivo))) {
            while (input.hasNextLine()) {
                incluirPerfil(new Perfil(input.nextLine()));
            }
        }catch(IOException e){
            System.out.println("errei fui mlk nas resgPerf2");
        }
    }
    public void resgatarPostagens0(String nomeArquivo){
        String conteudo = lerArquivo(nomeArquivo);
        for(String linha : conteudo.split("\n")){
            String[] atributos = linha.split(";");
            Postagem post;//vaelu
            if(atributos[0] == "0"){
                post = new Postagem(consultarPerfil(UUID.fromString(atributos[2])).get(),linha);
            }else {
                    post = new PostagemAvancada(consultarPerfil(UUID.fromString(atributos[2])).get(),linha);
                }incluirPostagem(post);
            }
    }
    public void resgatarPerfis0(String nomeArquivo){
        String conteudo = lerArquivo(nomeArquivo);
        for(String linha : conteudo.split("\n")){
            incluirPerfil(new Perfil(linha));
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

    public void resgatarPostagens2(String nomeArquivo){
        try(Scanner input = new Scanner(new File(nomeArquivo))) {
            while (input.hasNextLine()) {
                String linha =  input.nextLine();
                String[] atributos = linha.split(";");
                Postagem post;//vaelu
                if(atributos[0] == "0"){
                    post = new Postagem(consultarPerfil(UUID.fromString(atributos[2])).get(),linha);
                }else {
                    post = new PostagemAvancada(consultarPerfil(UUID.fromString(atributos[2])).get(),linha);
                }incluirPostagem(post);
            }
        }catch(IOException e){
            System.out.println("errei, fui mlk na resgPosts2");
        }
    }
    

    public void resgatarPostagens3(String nomeArquivo){
       
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            System.out.println("entrou em resgatar postagens");
            for(String linha : linhas){
                String[] atributos = linha.split(";");
                Postagem post;
                if(atributos[0].equals("0")){
                    post = new Postagem(consultarPerfil(UUID.fromString(atributos[2])).get(),linha);
                }else {
                    post = new PostagemAvancada(consultarPerfil(UUID.fromString(atributos[2])).get(),linha);

                }incluirPostagem(post);
            }
            System.out.println("saiu resgatar postagens");


        } catch (IOException e){
            System.out.println("Erro durante o resgate dos arquivos!");
        } catch(java.util.NoSuchElementException e) {
            System.out.println("consultarPorId não funcionou!");
        }catch(RuntimeException e){
                System.out.println("O erro tá na funcao resgatarPostagens");
            }
    }
}



