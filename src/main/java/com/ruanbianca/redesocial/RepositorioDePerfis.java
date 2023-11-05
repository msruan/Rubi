package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import com.ruanbianca.redesocial.SocialException;
public class RepositorioDePerfis {

    private ArrayList<Perfil> _perfis;

    public RepositorioDePerfis() {
        this._perfis = new ArrayList<>();
    }

    public RepositorioDePerfis(ArrayList<Perfil> perfis) {

        if(Optional.ofNullable(perfis).isEmpty())
            this._perfis = new ArrayList<>();
        else
            this._perfis = perfis;
    }

    public RepositorioDePerfis(Perfil[] perfis) {
        this(new ArrayList<>(Arrays.asList(perfis)));
    }

    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, NullAtributesException, UserAlreadyExistsException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);//lanca uma excecao se perfil for nulo
        
        if(perfil.temAtributosNulos()){
            throw new NullAtributesException();
        }
        if(usuarioJaExiste(perfil.getId(), perfil.getUsername(), perfil.getEmail(), perfil.getBiografia()))
            throw new UserAlreadyExistsException();
        else
            _perfis.add(perfil);
        
    }
    
//ei, resgatar perfis deu pau


    public boolean usuarioJaExiste(UUID id, String username, String email, String biografia){

        return (consultarPorId(id).isPresent() || consultarPorUsername(username).isPresent() || consultarPorEmail(email).isPresent() ||  consultarPorBio(biografia).isPresent());
    }

    public Optional<Perfil> consultarPorId(UUID id){

        if(Optional.ofNullable(id).isEmpty())
            return Optional.empty();

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getId().equals(id));
        return filtrados.findFirst();
    }

    public Optional<Perfil> consultarPorUsername(String username){

        if(Optional.ofNullable(username).isEmpty())
            return Optional.empty();

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getUsername().equals(username));
        return filtrados.findFirst();
    }

    public Optional<Perfil> consultarPorEmail(String email){

        if(Optional.ofNullable(email).isEmpty())
            return Optional.empty();

        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getEmail().equals(email));       
        return filtrados.findFirst();
    }

    public Optional<Perfil> consultarPorBio(String bio){
        if(Optional.ofNullable(bio).isEmpty())
            return Optional.empty();
        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados = filtrados.filter(perfil -> perfil.getBiografia().equals(bio));
        return filtrados.findFirst();
    }

    public ArrayList<Perfil> getPerfis(){
        return _perfis;
    }

    public void removerPerfil(String username){
        Optional<Perfil> perfilARemover = consultarPorUsername(username);
        if(perfilARemover.isPresent()){
            _perfis.remove(perfilARemover.get());
        }else{
            throw new UserAlreadyExistsException();
            
        }
        
    }

}