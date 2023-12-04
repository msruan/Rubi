package com.ruanbianca.redesocial;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepositorioDePerfis {

    boolean usuarioJaExite(UUID id, String username, String email);

    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, NullAtributesException, UserAlreadyExistsException;
    
    public Optional<Perfil> consultarPerfilPorTodosOsAtributos(UUID id, String username, String email);

    public Optional<Perfil> consultarPerfilPorId(UUID id);

    public Optional<Perfil> consultarPerfilPorUsername(String username);

    public Optional<Perfil> consultarPerfilPorEmail(String email);

    public ArrayList<Perfil> getPerfis();

    public void removerPerfil(String username);

    public void resgatarPerfis();

    void salvarPerfis();
} 