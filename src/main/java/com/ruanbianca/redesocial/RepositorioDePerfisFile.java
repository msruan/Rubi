package com.ruanbianca.redesocial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import com.ruanbianca.redesocial.utils.ManipuladorDeArquivos;

public class RepositorioDePerfisFile implements IRepositorioDePerfis {


    public ArrayList<Perfil> getPerfis(){

        String pathPerfis = getCaminhoDoBancoDeDados("Perfil");
        ArrayList<Perfil> perfis = new ArrayList<>();

        if(ManipuladorDeArquivos.arquivoExiste(pathPerfis)){    

            ArrayList<String> linhas = ManipuladorDeArquivos.lerLinhas(pathPerfis);
            for(String linha: linhas){
                perfis.add(resgatarPerfil(linha));
            }
            
        }return perfis;
    }


    public Optional<Perfil> consultar(UUID id, String username, String email){

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> {
            if(Optional.ofNullable(id).isPresent() && id.equals(perfil.getId()))
                return true;
            else if(Optional.ofNullable(username).isPresent() && username.equals(perfil.getUsername()))
                return true;
            else if(Optional.ofNullable(email).isPresent() && email.equals(perfil.getEmail()))
                return true;
            return false;
        });
        return filtrados.findFirst();
    }


    public boolean usuarioJaExite(UUID id, String username, String email){  
        
        return consultar(id,username,email).isPresent();    
    }
    
    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, UserAlreadyExistsException, IOException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
        
        if(usuarioJaExite(perfil.getId(), perfil.getUsername(), perfil.getEmail()))
            throw new UserAlreadyExistsException();

        String pathDb = getCaminhoDoBancoDeDados("DB");
        String pathPerfis = getCaminhoDoBancoDeDados("Perfil");

        if(! ManipuladorDeArquivos.arquivoExiste(pathDb)){

            File myDir = new File(pathDb);
            if(!myDir.mkdir())
                throw new IOException("Erro durante a criação do diretório DB!");
        }
        
        try{
            ManipuladorDeArquivos.gravarArquivo(pathPerfis, salvarPerfil(perfil), true);

        }catch(IOException e){
            throw new IOException("Erro durante a adição do perfil!");
        }
    }


    //Todo: apagar perfis.txt se nao houverem mais nenhum perfil
    public void removerPerfil(String username) throws NullAtributesException, UserNotFoundException, IOException{
        
        Optional.ofNullable(username).orElseThrow(NullAtributesException::new);

        String pathDb = getCaminhoDoBancoDeDados("DB");
        String pathPerfis = getCaminhoDoBancoDeDados("Perfil");

        if(! ManipuladorDeArquivos.arquivoExiste(pathDb) || ! ManipuladorDeArquivos.arquivoExiste(pathPerfis))
            throw new UserNotFoundException();
        
        ArrayList<String> perfis = ManipuladorDeArquivos.lerLinhas(pathPerfis);
        StringBuilder novoConteudo = new StringBuilder();
        boolean achou = false;
        for(String perfil: perfis){
            if(! perfil.split(";")[1].equals(username) ){//Lembrete: caso a ordem do banco seja alterada tem q mudar isso
                novoConteudo.append(perfil+"\n");
            }else
                achou = true;
        }
        
        if(! achou){
            throw new UserNotFoundException();
        }else{
            try{
            ManipuladorDeArquivos.gravarArquivo(pathPerfis, novoConteudo.toString(), false);
            }catch(IOException e){
                throw new IOException("Erro durante a remoção de perfil!");
            }
        }
        
    }


    public void atualizarPerfil(String username, String novoAtributo, String nomeAtributo) throws UserNotFoundException, IOException{
        
        if(!usuarioJaExite(null, username, nomeAtributo))
            throw new UserNotFoundException();

        Perfil perfil = consultar(null, username, null).get();

        if(nomeAtributo.equals("nome"))
            perfil.setNome(novoAtributo);

        else if(nomeAtributo.equals("username"))
            perfil.setUsername(novoAtributo);

        else if(nomeAtributo.equals("email"))
            perfil.setEmail(novoAtributo);

        else if(nomeAtributo.equals("biografia") || nomeAtributo.equals("bio"))
            perfil.setBiografia(novoAtributo);


        String pathPerfis = getCaminhoDoBancoDeDados("Perfil");
        ArrayList<String> perfis = ManipuladorDeArquivos.lerLinhas(pathPerfis);
        StringBuilder novoConteudo = new StringBuilder();
        
        for(String perfilLinha: perfis){

            if(! perfilLinha.split(";")[1].equals(username) )//Lembrete: caso a ordem do banco seja alterada tem q mudar isso

                novoConteudo.append(perfilLinha+"\n");
                
            else
                novoConteudo.append(salvarPerfil(perfil));
        }
        try{
            ManipuladorDeArquivos.gravarArquivo(pathPerfis, novoConteudo.toString(), false);
        }catch(IOException e){
            throw new IOException("Erro durante a atualização do perfil!");
        }
    }


    public String salvarPerfil(Perfil perfil) throws NullObjectAsArgumentException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);

        return perfil.getId().toString()+";"+perfil.getUsername()+";"+perfil.getNome()+";"+perfil.getEmail()+";"+perfil.getBiografia()+"\n";
    }  


    public Perfil resgatarPerfil(String linha){
        // *  IdPerfil  |    Username |  Nome  |    Email        | Bio
        String []atributos = linha.split(";");
        return new Perfil(
            UUID.fromString(atributos[0]),
            atributos[1],
            atributos[2],
            atributos[3],
            atributos[4]
        );
    } 
   

    public String getCaminhoDoBancoDeDados(String entidade) throws BadChoiceOfEntityForDB{
        
        if(entidade.equals("Perfil"))
            return System.getProperty("user.dir")+"/db/perfis.txt";

        else if(entidade.equals("Postagem"))
            return System.getProperty("user.dir")+"/db/postagens.txt";

        else if(entidade.equals("DB")){
            return System.getProperty("user.dir")+"/db";   
        }
        else {
            throw new BadChoiceOfEntityForDB();
        }
    }
}