package com.ruanbianca.redesocial;

import java.io.File;
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

    public ArrayList<Postagem> getPostagens() {

        String pathPosts = getCaminhoDoBancoDeDados("Postagem");
        ArrayList<Postagem> postagens = new ArrayList<>();

        if (ManipuladorDeArquivos.arquivoExiste(pathPosts)) {

            ArrayList<String> conteudo = ManipuladorDeArquivos.lerLinhas(pathPosts);

            for (String linha : conteudo)
                postagens.add(resgatarPostagem(linha));
        }
        return postagens;
    }

    public ArrayList<PostagemAvancada> getPostagensAvancadas() {

        String pathPosts = getCaminhoDoBancoDeDados("Postagem");
        ArrayList<PostagemAvancada> postagens = new ArrayList<>();

        if (ManipuladorDeArquivos.arquivoExiste(pathPosts)) {

            ArrayList<String> conteudo = ManipuladorDeArquivos.lerLinhas(pathPosts);

            for (String linha : conteudo){

                if(linha.split(";")[0].equals("1"))

                    postagens.add((PostagemAvancada)resgatarPostagem(linha));
            }
        }return postagens;
    }
    

    public Optional<Postagem> consultarPostagem(UUID id) {

        Optional<Postagem> postagem = Optional.empty();

        for (Postagem post : getPostagens()) {

            if (post.getId().equals(id)) {
                System.out.println("achou");
                postagem = Optional.ofNullable(post);
                break;
            }
        }return postagem;
    }


    public ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag) {

        Stream<Postagem> filtrados = getPostagens().stream();

        if (Optional.ofNullable(perfil).isPresent()) {
            filtrados = filtrados.filter(
                    post -> post.getPerfilId().equals(perfil.getId()));
        }

        if (Optional.ofNullable(hashtag).isPresent()) {
            filtrados = filtrados.filter(
                    post -> post instanceof PostagemAvancada && ((PostagemAvancada) post).existeHashtag(hashtag));
        }

        if (Optional.ofNullable(texto).isPresent()) {
            filtrados = filtrados.filter(post -> post.getTexto().contains(texto));
        }

        return new ArrayList<Postagem>(filtrados.toList());
    }

    
    public boolean postagemJaExiste(UUID id) {

        return consultarPostagem(id).isPresent();
    }


    public void incluir(Postagem postagem) throws NullObjectAsArgumentException, PostAlreadyExistsException, IOException {

        Optional.ofNullable(postagem).orElseThrow(NullObjectAsArgumentException::new);

        if (postagemJaExiste(postagem.getId()))
            throw new PostAlreadyExistsException();

        String pathPosts = getCaminhoDoBancoDeDados("Postagem");
        String pathDb = getCaminhoDoBancoDeDados("DB");
        if (!ManipuladorDeArquivos.arquivoExiste(pathDb)) {
            File myDir = new File(getCaminhoDoBancoDeDados("DB"));
            if (!myDir.mkdir())
                System.err.println("Erro durante a criação do diretório DB");
        }

        try {
            ManipuladorDeArquivos.gravarArquivo(pathPosts, salvarPostagem(postagem), true);

        } catch (IOException e) {
            throw new IOException("Erro durante inclusão do perfil");
        }
    }
    

    public void removerPostPorPerfil(Perfil perfil) throws IOException{

        ArrayList<Postagem> postagens = getPostagens();
        postagens.removeIf(post -> post.getPerfilId().equals(perfil.getId()));

        String pathPosts = getCaminhoDoBancoDeDados("Postagem");
        try {
            ManipuladorDeArquivos.gravarArquivo(pathPosts, "", false);
            for (Postagem post : postagens)
                ManipuladorDeArquivos.gravarArquivo(pathPosts, salvarPostagem(post), true);

        } catch (IOException e) {
            throw new IOException(
                    "Erro durante a remoção das postagens do perfil!");
        }
    }

    public void atualizarPostagem(Postagem postagem) throws NullObjectAsArgumentException, PostNotFoundException, IOException{

        Optional.ofNullable(postagem).orElseThrow(NullAtributesException::new);

        String pathDb = getCaminhoDoBancoDeDados("DB");
        String pathPosts = getCaminhoDoBancoDeDados("Postagem");

        if(! ManipuladorDeArquivos.arquivoExiste(pathDb) || ! ManipuladorDeArquivos.arquivoExiste(pathPosts))
            throw new PostNotFoundException("O erro ocorreu na linha 214 de RepoPostsFile!");//sera q quebrou aqui?
        
        ArrayList<String> posts = ManipuladorDeArquivos.lerLinhas(pathPosts);
        StringBuilder novoConteudo = new StringBuilder();
     
        UUID id = postagem.getId();
    
        for(String post: posts){
         
            if(! post.split(";")[1].equals(id.toString())){
                novoConteudo.append(post+"\n");
                
            }else{
                novoConteudo.append(salvarPostagem(postagem));
            } 
        }
            try{
            ManipuladorDeArquivos.gravarArquivo(pathPosts, novoConteudo.toString(), false);
            }catch(IOException e){
                throw new IOException("Erro durante a atualização de postagem!");
            }
    }


    public String salvarPostagem(Postagem postagem) throws NullAtributesException {

        if (Optional.ofNullable(postagem).isEmpty()) 
            throw new NullAtributesException();

        else if (postagem instanceof PostagemAvancada) {

            PostagemAvancada postagemAv = (PostagemAvancada) postagem;

            return 1 + ";" + postagem.getId().toString() + ";" + postagem.getPerfilId().toString() + ";" +
                    postagem.getData().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ";" + postagem.getTexto() + ";" +
                    String.valueOf(postagem.getCurtidas()) + ";" + String.valueOf(postagem.getDescurtidas()) + ";" +
                    String.valueOf(postagemAv.getVisualizacoesRestantes()) + ";" +
                    (Optional.ofNullable(postagemAv.getHashtagsParaDb()).isPresent() ? postagemAv.getHashtagsParaDb()
                            : null)
                    + ";" + '\n';
        }
        else {

            return 0 + ";" + postagem.getId().toString() + ";" + postagem.getPerfilId().toString() + ";" +
                    postagem.getData().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ";" + postagem.getTexto() + ";" +
                    String.valueOf(postagem.getCurtidas()) + ";" + String.valueOf(postagem.getDescurtidas()) + ";"
                    + "\n";
        }
    }


    public Postagem resgatarPostagem(String linha) {

        String[] atributos = linha.split(";");
        if (atributos[0].equals("0")) {
            /*
             * | Tipo | IdPost | IdPerfil | Data | Texto | Likes | Deslikes | ViewsRestantes
             * | Hashtags<> |
             */

            return (new Postagem(

                    UUID.fromString(atributos[1]),
                    UUID.fromString(atributos[2]),
                    LocalDateTime.parse(atributos[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    atributos[4],
                    Integer.parseInt(atributos[5]),
                    Integer.parseInt(atributos[6])));

        } else {

            return (new PostagemAvancada(
                    UUID.fromString(atributos[1]),
                    UUID.fromString(atributos[2]),
                    LocalDateTime.parse(atributos[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    atributos[4],
                    Integer.parseInt(atributos[5]),
                    Integer.parseInt(atributos[6]),
                    Integer.valueOf(atributos[7]),
                    new ArrayList<>(Arrays.asList(atributos[8].split("#")))));
        }
    }


    public String getCaminhoDoBancoDeDados(String entidade) throws BadChoiceOfEntityForDB {

        if (entidade.equals("Perfil"))
            return System.getProperty("user.dir") + "/db/perfis.txt";

        else if (entidade.equals("Postagem"))
            return System.getProperty("user.dir") + "/db/postagens.txt";

        else if (entidade.equals("DB"))
            return System.getProperty("user.dir") + "/db";

        else
            throw new BadChoiceOfEntityForDB();

    }
}