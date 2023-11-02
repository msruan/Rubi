package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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
        if(usuarioJaExiste(perfil.getId(), perfil.getUsername(), perfil.getEmail()))
            throw new UserAlreadyExistsException();
        else
            _perfis.add(perfil);
        
    }


    public boolean usuarioJaExiste(Integer id, String username, String email){

        return (consultarPorId(id).isPresent() || consultarPorUsername(username).isPresent() || consultarPorEmail(email).isPresent());
    }

    public Optional<Perfil> consultarPorId(Integer id){

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

    public ArrayList<Perfil> getPerfis(){
        return _perfis;
    }

}