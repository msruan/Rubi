package com.ruanbianca.redesocial;

import java.util.Optional;
import java.util.UUID;
import lombok.Getter;

import static com.ruanbianca.redesocial.utils.ConsoleColors.*;

public class Perfil{

    private @Getter UUID id;
    private @Getter String username;
    private @Getter String nome;
    private @Getter String email;
    private @Getter String biografia;
    
    public Perfil(String username, String nome, String email, String biografia) throws NullAtributesException{

        if(Optional.ofNullable(username).isEmpty() || Optional.ofNullable(nome).isEmpty() || Optional.ofNullable(email).isEmpty() || Optional.ofNullable(biografia).isEmpty())
            throw new NullAtributesException();

        this.username = username;
        this.nome = nome;
        this.email = email;
        this.id = UUID.randomUUID();
        this.biografia = biografia;
    }


    public Perfil(UUID id, String username, String nome, String email, String biografia) throws NullAtributesException{
        
        this(username, nome, email, biografia);
        Optional.ofNullable(id).orElseThrow(NullAtributesException::new);
        this.id = id;
    }

    public String limitarBio(String bio){

        if(bio.length() > 22){
            bio = bio.substring(0,23) + "\n  " + bio.substring(23, bio.length());
        }
        return bio;
    }


    //Todo: tirar daqui e por em RedeSocial
    public static String exibirPerfil(Perfil perfil) {
  
        StringBuilder result = new StringBuilder();
        result.append("╭───────────────────────────────────╮\n");
        result.append("│" + CYAN_BOLD + "           Perfil Info" + RESET + "             │\n");
        result.append("├───────────────────────────────────┤\n");
        result.append("  " + YELLOW + "Username:" + RESET + " " + perfil.getUsername() + "\n");
        result.append("  " + YELLOW + "Nome:" + RESET + " " + perfil.getNome() + "\n");
        result.append("  " + YELLOW + "Biografia:" + RESET + " " + perfil.limitarBio(perfil.getBiografia()) + "\n");
        result.append("╰───────────────────────────────────╯");

        return result.toString();
    }


    public void setUsername(String username) {
        if(Optional.ofNullable(username).isPresent())
            this.username = username;
    }


    public void setNome(String nome) {
        if(Optional.ofNullable(nome).isPresent())
            this.nome = nome;
    }


    public void setEmail(String email) {
        if(Optional.ofNullable(email).isPresent())
            this.email = email;
    }
    

    public void setBiografia(String biografia){
        if(Optional.ofNullable(biografia).isPresent())
            this.biografia = biografia;
    }
}