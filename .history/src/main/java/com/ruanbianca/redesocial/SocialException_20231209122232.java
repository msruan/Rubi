package com.ruanbianca.redesocial;
import com.ruanbianca.redesocial.SocialException;

abstract public class SocialException extends RuntimeException {
    public SocialException(String message){
        super(message);
    }//Posso deixar abstrata e ter um construtor?
}

class PostAlreadyExistsException extends SocialException {
    
    public PostAlreadyExistsException (){
        super("Postagem já está cadastrada no banco de dados!");
    } 
}

class NullAtributesException extends SocialException {

    public NullAtributesException (){
        super("Tenha certeza de não deixar nenhum parâmetro nulo! ");
    }
}

class BadChoiceOfEntityForDB extends SocialException {
    
    public BadChoiceOfEntityForDB() {
        super("Você tentou conseguir o caminho do banco de dados, mas digitou o nome da entidade errado! Tente usar <Perfil> ou <Postagem>");
    }
}

class PostNotFoundException extends SocialException {

    public PostNotFoundException (){
        super("Post não encontrado!");
    }
}

class NullObjectAsArgumentException extends SocialException {
    
    public NullObjectAsArgumentException() {
        super("O argumento deveria ser um objeto (String, Integer, Perfil, Postagem...), mas você passou null!");
    }
}

class UserNotFoundException extends SocialException {
    
    public UserNotFoundException(){
        super("Usuário não encontrado!");
    }
}

class UserAlreadyExistsException extends SocialException {
    
    public UserAlreadyExistsException(String cause){
        super(message);
    }
}