package com.ruanbianca.redesocial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import com.ruanbianca.redesocial.utils.ManipuladorDeArquivos;

public class RepositorioDePostagensFile implements IRepositorioDePostagens {


    public ArrayList<Postagem> getPostagens(){

        String pathPosts = getCaminhoDoBancoDeDados("Postagem");
        ArrayList<Postagem> postagens = new ArrayList<>();

        if(ManipuladorDeArquivos.arquivoExiste(pathPosts)){ 
                
            try{
                ArrayList <String> conteudo = ManipuladorDeArquivos.lerLinhas(pathPosts);

                for(String linha : conteudo)
                    postagens.add(resgatarPostagem(linha));
                    
            }
            catch(RuntimeException e){
                System.out.println("O erro ocorreu em resgatar postagens+"+e.getMessage());
            }
        }return postagens;
    }


    public ArrayList<PostagemAvancada> getPostagensAvancadas() {

        ArrayList<PostagemAvancada> avancadas = new ArrayList<>();
        Stream <Postagem> postagens = getPostagens().stream();
        postagens = postagens.filter(post -> post instanceof PostagemAvancada);
        postagens.forEach(post -> avancadas.add((PostagemAvancada)post));
        return avancadas;
    }


    public void incluir(Postagem postagem) throws NullObjectAsArgumentException, PostAlreadyExistsException {

        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se postagem for nunla
        if(postagemJaExiste(postagem.getId()))
                throw new PostAlreadyExistsException();

        String pathPosts = getCaminhoDoBancoDeDados("Postagem");
        String pathDb = getCaminhoDoBancoDeDados("DB");
        if(! ManipuladorDeArquivos.arquivoExiste(pathDb)){
            File myDir = new File(getCaminhoDoBancoDeDados("DB"));
            if(!myDir.mkdir())
                System.err.println("Erro durante a criação do diretório DB");
        }

        try{
            ManipuladorDeArquivos.gravarArquivo(pathPosts, salvarPostagem(postagem), true);

        }catch(IOException e){
            System.err.println("Os arquivos não estão funcionando no momento, por favor tente novamente com outro tipo de persistência...");
            e.printStackTrace();
            System.err.flush();
            System.exit(1);
        }
    }


    public Optional<Postagem> consultarPostagem(UUID id) {

        Optional<Postagem> saida = Optional.empty();

        for(Postagem post : getPostagens()){
            if(post.getId() == id){
                saida = Optional.of(post);
                break;
            }
        }return saida;
    }

    //Todo: ajeitar a lógica dessa função
    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag){

        Stream <Postagem> filtrados = getPostagens().stream();

        if(Optional.ofNullable(perfil).isPresent()){
            filtrados = filtrados.filter(
                post -> post.getPerfilId().equals(perfil.getId()));
        }

        if(Optional.ofNullable(hashtag).isPresent()) {
            filtrados = filtrados.filter(
                post -> post instanceof PostagemAvancada && (
                    (PostagemAvancada)post).existeHashtag(hashtag));
        }

        if(Optional.ofNullable(texto).isPresent()) {
            filtrados = filtrados.filter(post -> post.getTexto().contains(texto));
        }

        return new ArrayList<Postagem>(filtrados.toList());
    }


    public boolean postagemJaExiste(UUID id){

        return consultarPostagem(id).isPresent();
    }


    public String salvarPostagem(Postagem postagem) throws NullAtributesException{

        if(Optional.ofNullable(postagem).isEmpty()){
            throw new NullAtributesException();
        }
        else if(postagem instanceof PostagemAvancada){

            PostagemAvancada postagemAv = (PostagemAvancada) postagem;
            StringBuilder strHashtags = new StringBuilder();
            for(int i =0; i< postagemAv.getHashtags().size(); i++){
                if(i>0)
                    strHashtags.append("#");
                strHashtags.append(postagemAv.getHashtags().get(i));
            }
            return 1 + ";" + postagem.getId().toString() + ";" + postagem.getPerfilId().toString() + ";" + 
                postagem.getData().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ";" + postagem.getTexto() + ";" + 
                String.valueOf(postagem.getCurtidas()) + ";" + String.valueOf(postagem.getDescurtidas()) + ";" +
                String.valueOf(postagemAv.getVisualizacoesRestantes()) +";" +
                (Optional.ofNullable(strHashtags.toString()).isPresent() ? strHashtags.toString() : null ) + ";" + '\n';  
        }
        else{
            return 0 + ";" + postagem.getId().toString() + ";" + postagem.getPerfilId().toString() + ";" + 
                postagem.getData().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ";" + postagem.getTexto() + ";" + 
                String.valueOf(postagem.getCurtidas()) + ";" + String.valueOf(postagem.getDescurtidas()) + ";" + "\n";  
        }
    }
    

    public Postagem resgatarPostagem(String linha){

        String[] atributos = linha.split(";");
        if(atributos[0].equals("0")){
            /* | Tipo |    IdPost   |    IdPerfil    |   Data  | Texto  | Likes | Deslikes | ViewsRestantes | Hashtags<> |*/
            
            return( new Postagem(

                UUID.fromString(atributos[1]), 
                UUID.fromString(atributos[2]), 
                LocalDateTime.parse(atributos[3],DateTimeFormatter.ISO_LOCAL_DATE_TIME), 
                atributos[4],
                Integer.parseInt(atributos[5]),
                Integer.parseInt(atributos[6])
            ));

        }
        else {

            return( new PostagemAvancada(
                UUID.fromString(atributos[1]), 
                UUID.fromString(atributos[2]), 
                LocalDateTime.parse(atributos[3],DateTimeFormatter.ISO_LOCAL_DATE_TIME), 
                atributos[4],
                Integer.parseInt(atributos[5]),
                Integer.parseInt(atributos[6]),
                Integer.valueOf(atributos[7]),
                new ArrayList<>(Arrays.asList(atributos[8].split("#")))
            ));
        }      
    }


    public String getCaminhoDoBancoDeDados(String entidade) throws BadChoiceOfEntityForDB{
        
        if(entidade.equals("Perfil"))
            return System.getProperty("user.dir")+"/db/perfis.txt";

        else if(entidade.equals("Postagem"))
            return System.getProperty("user.dir")+"/db/postagens.txt";

        else if(entidade.equals("DB"))
            return System.getProperty("user.dir")+"/db";   
        
        else 
            throw new BadChoiceOfEntityForDB();
        
    }


    //Todo: fazer acontecer
    // public void removerPostagem(String texto, Perfil perfil,String hashtag){

    //     ArrayList<Postagem> postagensARemover = consultarPostagens(texto, perfil, hashtag);
    //     if(Optional.ofNullable(postagensARemover).isPresent()){
    //         _postagens.removeAll(postagensARemover);
    //     }else{
    //         System.out.println("Não há postagens para remover");
    //     }
    // }
}