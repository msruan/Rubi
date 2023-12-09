package com.ruanbianca.redesocial;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepositorioDePerfis {

    public ArrayList<Perfil> getPerfis();

    public Optional<Perfil> consultarPerfil(UUID id, String username, String email);

    boolean usuarioJaExite(UUID id, String username, String email);

    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, NullAtributesException, UserAlreadyExistsException;
    
    public void removerPerfil(String username);

    //Todo: adicionar um atualizar
} 