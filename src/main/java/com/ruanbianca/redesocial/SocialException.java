package com.ruanbianca.redesocial;

abstract public class SocialException extends RuntimeException {
}

class UserAlreadyExistsException extends SocialException {
    @Override
    public String getMessage() {
        return "Usuário já está cadastrado no banco de dados!";
    }
}

class NullAtributesException extends SocialException {
    @Override
    public String getMessage() {
        return "Tenha certeza de não deixar nenhum parâmetro nulo!";
    }
}