package com.ruanbianca.redesocial;
import com.ruanbianca.redesocial.SocialException;

abstract public class SocialException extends RuntimeException {

    public SocialException (){
        super("Ocorreu um erro na aplicação!");
    }
    public SocialException (String message){
        super(message);
    }
}

class UserAlreadyExistsException extends SocialException {
    
    public UserAlreadyExistsException(){
        super("Usuário já está cadastrado no banco de dados!");
    }
}

class PostAlreadyExistsException extends SocialException {
    public PostAlreadyExistsException(){
        super( "Postagem já está cadastrada no banco de dados!");
    }
}

class NullAtributesException extends SocialException {
    public NullAtributesException(){
        super( "Tenha certeza de não deixar nenhum parâmetro nulo! ");
    }
}

class NullObjectAsArgumentException extends SocialException {
    public NullObjectAsArgumentException(){
        super( "O argumento deveria ser um objeto (String, Integer, Perfil, Postagem...), mas você passou null!");
    }
}

class UserNotFoundException extends SocialException {
    public UserNotFoundException(){
        super("Usuário não encontrado!");
    }
}

class PostNotFoundException extends SocialException {
    public PostNotFoundException(){
        super( "Post não encontrado!");
    }
    public PostNotFoundException(String message){
        super( "Post não encontrado! "+message);
    }
}

class BadChoiceOfEntityForDB extends SocialException {
    public BadChoiceOfEntityForDB(){
        super( "Você tentou conseguir o caminho do banco de dados, mas digitou o nome da entidade errado! Tente usar <Perfil> ou <Postagem>");
    }
}