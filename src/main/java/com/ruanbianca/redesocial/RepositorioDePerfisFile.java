package com.ruanbianca.redesocial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import com.ruanbianca.redesocial.utils.ManipuladorDeArquivos;

public class RepositorioDePerfisFile implements IRepositorioDePerfis {


    private ArrayList<Perfil> _perfis;


    public RepositorioDePerfisFile() {
        this._perfis = new ArrayList<>();
    }


    public RepositorioDePerfisFile(ArrayList<Perfil> perfis) {

        if(Optional.ofNullable(perfis).isEmpty())
            this._perfis = new ArrayList<>();
        else
            this._perfis = perfis;
    }


    public RepositorioDePerfisFile(Perfil[] perfis) {
        this(new ArrayList<>(Arrays.asList(perfis)));
    }

    public boolean usuarioJaExite(UUID id, String username, String email){  
        return consultarPerfilPorTodosOsAtributos(id,username,email).isPresent();
            
    }


    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, UserAlreadyExistsException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
        
        if(usuarioJaExite(perfil.getId(),perfil.getUsername(), perfil.getEmail()))
            throw new UserAlreadyExistsException();
        else
            _perfis.add(perfil);
        
    }
    

    public Optional<Perfil> consultarPerfilPorTodosOsAtributos(UUID id, String username, String email){

        Optional<Perfil> consultaPorId = consultarPerfilPorId(id)
        ,consultaPorUsername = consultarPerfilPorUsername(username),
        consultaPorEmail = consultarPerfilPorEmail(email);

        if(consultaPorId.isPresent())
            return consultaPorId;

        else if(consultaPorUsername.isPresent())
            return consultaPorUsername;

        else if(consultaPorEmail.isPresent())
            return consultaPorEmail;

        return Optional.empty();
    }


    public Optional<Perfil> consultarPerfilPorId(UUID id){

        if(Optional.ofNullable(id).isEmpty())
            return Optional.empty();

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getId().equals(id));
        return filtrados.findFirst();
    }


    public Optional<Perfil> consultarPerfilPorUsername(String username){

        if(Optional.ofNullable(username).isEmpty())
            return Optional.empty();

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getUsername().equals(username));
        return filtrados.findFirst();
    }


    public Optional<Perfil> consultarPerfilPorEmail(String email){

        if(Optional.ofNullable(email).isEmpty())
            return Optional.empty();

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getEmail().equals(email));       
        return filtrados.findFirst();
    }


    public ArrayList<Perfil> getPerfis(){
        return _perfis;
    }

    public void removerPerfil(String username){
        
        Optional<Perfil> perfilARemover = consultarPerfilPorUsername(username);
        if(perfilARemover.isPresent()){
            _perfis.remove(perfilARemover.get());
        }
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

    public void resgatarPerfis(){

        String pathPerfis = getCaminhoDoBancoDeDados("Perfil");

        if(ManipuladorDeArquivos.arquivoExiste(pathPerfis)){    

            try {
                ArrayList<String> linhas = ManipuladorDeArquivos.lerLinhas(pathPerfis);
                for(String linha: linhas){
                    try{
                        incluir(resgatarPerfil(linha));

                    }catch(RuntimeException e){
                        System.out.println("O erro tá na linha\n"+linha+"de resgatar perfis!"+e.getMessage());
                    }
                }
            }catch(SocialException e){
                System.out.println("O erro está em SocialException !"+e.getMessage());

            }catch(RuntimeException e){
                System.out.println("O erro tá na funcao resgatarPerfis no geral! "+e.getMessage());
            }
        }
    }

    public String salvarPerfil(Perfil perfil) throws NullObjectAsArgumentException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);

        return perfil.getId().toString()+";"+perfil.getUsername()+";"+perfil.getNome()+";"+perfil.getEmail()+";"+perfil.getBiografia()+"\n";
    }   

    
    public void salvarPerfis() throws NullObjectAsArgumentException{
       
        String pathDb = getCaminhoDoBancoDeDados("DB");

        if(! ManipuladorDeArquivos.arquivoExiste(pathDb)){

            File myDir = new File(pathDb);
            if(!myDir.mkdir())
                System.err.println("Erro durante a criação do diretório DB");
        }
        
        try ( BufferedWriter buffwriter = new BufferedWriter(new FileWriter(getCaminhoDoBancoDeDados("Perfil")))){
            for(Perfil perfil : _perfis){
                buffwriter.write(salvarPerfil(perfil));
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch(RuntimeException e){
                System.out.println("O erro tá na funcao salvarPerfis");
        }
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