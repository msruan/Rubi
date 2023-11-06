package com.ruanbianca.redesocial;

import java.util.Optional;
import java.util.UUID;
import lombok.Getter;

import static com.ruanbianca.redesocial.utils.ConsoleColors.*;
import com.ruanbianca.redesocial.SocialException;

public class Perfil{


    private @Getter String username;
    private @Getter String nome;
    private @Getter String email;
    private @Getter UUID id;
    private @Getter String biografia;


    @Override
    public String toString() {
        return id.toString() + ";" + username + ";" + nome +  ";" + email + ";" + biografia +'\n';
    }


    public Perfil(String perfil) { 
        
        // *  IdPerfil  |    Username |  Nome  |    Email        | Bio
        String []atributos = perfil.split(";");
        this.id = UUID.fromString(atributos[0]);
        this.username = atributos[1];
        this.nome = atributos[2];
        this.email = atributos[3];
        this.biografia = atributos[4];
    }

    
    public Perfil(String username, String nome, String email, String biografia) throws NullAtributesException{

        if(Optional.ofNullable(username).isEmpty() || Optional.ofNullable(nome).isEmpty() || Optional.ofNullable(email).isEmpty() || Optional.ofNullable(biografia).isEmpty())
            throw new NullAtributesException();

        this.username = username;
        this.nome = nome;
        this.email = email;
        this.id = UUID.randomUUID();
        this.biografia = biografia;
    }


    public String limitarBio(String bio){

        if(bio.length() > 22){
            bio = bio.substring(0,23) + "\n  " + bio.substring(23, bio.length());
        }
        return bio;
    }


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


    public boolean temAtributosNulos(){
   
        return (Optional.ofNullable(getId()).isEmpty() || Optional.ofNullable(getUsername()).isEmpty() || 
            Optional.ofNullable(getNome()).isEmpty() || Optional.ofNullable(getEmail()).isEmpty() || Optional.ofNullable(getBiografia()).isEmpty()
        );
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