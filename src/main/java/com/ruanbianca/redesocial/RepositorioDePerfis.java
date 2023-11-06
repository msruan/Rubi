package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.swing.text.html.Option;

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

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
        
        if(perfil.temAtributosNulos()){
            throw new NullAtributesException();
        }
        
        if(consultarPerfilPorTodosOsAtributos(perfil.getId(), perfil.getUsername(), perfil.getEmail()).isPresent())
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
}