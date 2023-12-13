package com.ruanbianca.redesocial;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepositorioDePerfis {

    
    public ArrayList<Perfil> getPerfis() throws Exception;


    public Optional<Perfil> consultar(UUID id, String username, String email) throws Exception;


    boolean usuarioJaExite(UUID id, String username, String email) throws Exception;


    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, NullAtributesException, UserAlreadyExistsException, Exception;
    

    public void removerPerfil(String username) throws NullAtributesException, UserNotFoundException, Exception;


    public void atualizarPerfil(String username, String novoAtributo, String nomeAtributo) throws UserNotFoundException, Exception;
} 