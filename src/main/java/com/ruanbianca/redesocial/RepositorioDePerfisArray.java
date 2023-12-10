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


    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, UserAlreadyExistsException{

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
        
        if(usuarioJaExite(perfil.getId(),perfil.getUsername(), perfil.getEmail()))
            throw new UserAlreadyExistsException();
        else
            _perfis.add(perfil);   
    }


    public void removerPerfil(String username){
        
        Optional<Perfil> perfilARemover = consultar(null, username, null);

        if(perfilARemover.isPresent()){
            _perfis.remove(perfilARemover.get());
        }
    }

    //Fazer validação do lado de fora?
    public void atualizarPerfil(String username, String novoAtributo, String nomeAtributo) throws UserNotFoundException{

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
    }
}