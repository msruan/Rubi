package com.ruanbianca.redesocial;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepositorioDePerfis {

    boolean usuarioJaExite(UUID id, String username, String email);

    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, NullAtributesException, UserAlreadyExistsException;
    
    public Optional<Perfil> consultarPerfil(UUID id, String username, String email);

    public ArrayList<Perfil> getPerfis();

    //Todo: fazer acontecer
    //public void removerPerfil(String username);
} 