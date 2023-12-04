package com.ruanbianca.redesocial;
import com.ruanbianca.redesocial.SocialException;

abstract public class SocialException extends RuntimeException {
}

class UserAlreadyExistsException extends SocialException {
    @Override
    public String getMessage() {
        return "Usuário já está cadastrado no banco de dados!";
    }
}

class PostAlreadyExistsException extends SocialException {
    @Override
    public String getMessage() {
        return "Postagem já está cadastrada no banco de dados!";
    }
}

class NullAtributesException extends SocialException {
    @Override
    public String getMessage() {
        return "Tenha certeza de não deixar nenhum parâmetro nulo! ";
    }
}

class BadChoiceOfEntityForDB extends SocialException {
    @Override
    public String getMessage() {
        return "Você tentou conseguir o caminho do banco de dados, mas digitou o nome da entidade errado! Tente usar <Perfil> ou <Postagem>";
    }
}

class PostNotFoundException extends SocialException {
    @Override
    public String getMessage() {
        return "Post não encontrado!";
    }
}

class NullObjectAsArgumentException extends SocialException {
    @Override
    public String getMessage() {
        return "O argumento deveria ser um objeto (String, Integer, Perfil, Postagem...), mas você passou null!";
    }
}

class UserNotFoundException extends SocialException {
    @Override
    public String getMessage(){
        return "Usuário não encontrado!";
    }
}