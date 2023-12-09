package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


public class RepositorioDePerfisArray implements IRepositorioDePerfis {


    private ArrayList<Perfil> _perfis;


    public RepositorioDePerfisArray() {
        this._perfis = new ArrayList<>();
    }


    public RepositorioDePerfisArray(ArrayList<Perfil> perfis) {

        if(Optional.ofNullable(perfis).isEmpty())
            this._perfis = new ArrayList<>();
        else
            this._perfis = perfis;
    }

   
    public RepositorioDePerfisArray(Perfil[] perfis) {

        this(new ArrayList<>(Arrays.asList(perfis)));
    }


    public ArrayList<Perfil> getPerfis(){
        return _perfis;
    }


    public Optional<Perfil> consultarPerfil(UUID id, String username, String email){

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


    public void usuarioJaExite(UUID id, String username, String email){  
        
        Stream<Perfil> filtrados = getPerfis().stream();
        filtrados.forEach(perfil -> {

            if(Optional.ofNullable(id).isPresent() && id.equals(perfil.getId()))
                throw new UserAlreadyExistsException("id");

            else if(Optional.ofNullable(username).isPresent() && username.equals(perfil.getUsername()))
                throw new UserAlreadyExistsException("username");
            else if(Optional.ofNullable(email).isPresent() && email.equals(perfil.getEmail()))
                return true;

            return false;
        });
    }


    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, UserAlreadyExistsException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
        
        if(usuarioJaExite(perfil.getId(),perfil.getUsername(), perfil.getEmail()))
            throw new UserAlreadyExistsException();
        else
            _perfis.add(perfil);   
    }


    public void removerPerfil(String username){
        
        Optional<Perfil> perfilARemover = consultarPerfil(null, username, null);

        if(perfilARemover.isPresent()){
            _perfis.remove(perfilARemover.get());
        }
    }
}