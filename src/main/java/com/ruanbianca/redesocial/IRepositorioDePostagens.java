package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface IRepositorioDePostagens {

    public void incluir(Postagem postagem) throws NullObjectAsArgumentException, NullAtributesException, UserAlreadyExistsException;

    ArrayList<Postagem> consultarPostagens(String texto, Perfil perfil, String hashtag);

    Optional<Postagem> consultarPostagemPorId(UUID id);

    ArrayList<Postagem> getPostagens();

    ArrayList<PostagemAvancada> getPostagensAvancadas();

    void removerPostagem(String texto, Perfil perfil, String hashtag);

    String salvarPostagem(Postagem postagem) throws NullAtributesException;

    void salvarPostagens() throws NullAtributesException;

    void resgatarPostagens();

    String getCaminhoDoBancoDeDados(String entidade) throws BadChoiceOfEntityForDB;
}
