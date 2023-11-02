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

        if(Optional.ofNullable(perfil).isEmpty())
            throw new NullObjectAsArgumentException();
        else if(perfil.temAtributosNulos()){
            throw new NullAtributesException();
        }
        Optional<Perfil> perfilBuscado = consultar(perfil.getId(), perfil.getUsername(), perfil.getEmail());
        if(perfilBuscado.isPresent())
            throw new UserAlreadyExistsException();
        else
            _perfis.add(perfil);
        
    }



    public Optional<Perfil> consultar(Integer id, String username, String email){

        Stream<Perfil> filtrados = getPerfis().stream();

        if(Optional.ofNullable(username).isPresent())
            filtrados = filtrados.filter(perfil -> perfil.getUsername().equals(username));
        if(Optional.ofNullable(email).isPresent()) 
            filtrados = filtrados.filter(perfil -> perfil.getEmail().equals(email));
        if(Optional.ofNullable(id).isPresent())
            filtrados = filtrados.filter(perfil -> perfil.getId().equals(id));

        return filtrados.findFirst();
            
        /*if(Optional.ofNullable(perfisFiltrados.findFirst()).isEmpty())
            return null;
        return perfisFiltrados.findFirst().get();*/
    }

    public ArrayList<Perfil> getPerfis(){
        return _perfis;
    }

}
