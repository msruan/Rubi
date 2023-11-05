package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import static com.ruanbianca.redesocial.utils.ConsoleColors.*;

import lombok.Getter;

public class Postagem {

    @Getter
    private String texto;
    @Getter
    private Perfil perfil; 
    @Getter  
    private LocalDateTime data;
    @Getter
    private int curtidas;
    @Getter
    private int descurtidas;
    @Getter
    private UUID id;

    public String exibirPostagem(){
        return "--------------------------------------------\n"+
         "|*"+getPerfil().getUsername()+"*"+"\n\n"+
            getTexto()+"\n"+"\n"
            +RED_BOLD_BRIGHT+getCurtidas()+RESET+BLUE_BOLD_BRIGHT+" <3    " + getDescurtidas() + " '</3'"+RESET
            + "\n"+"--------------------------------------------\n";
    }


    @Override
    public String toString() {
        return 0 + ";" + id.toString() + ";" + perfil.getId().toString() + ";" + 
            data.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ";" + texto + ";" + 
            String.valueOf(curtidas) + ";" + String.valueOf(descurtidas) + ";" + "\n";  
    }


    public Postagem(Perfil perfil, String postagem) {
        /* | Tipo |    IdPost   |    IdPerfil    |   Data  | Texto  | Likes | Deslikes | ViewsRestantes | Hashtags<> |*/
        String []atributos = postagem.split(";");
        this.id = UUID.fromString(atributos[1]);
        this.perfil = perfil;
        this.data = LocalDateTime.parse(atributos[3],DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.texto = atributos[4];
        this.curtidas = Integer.parseInt(atributos[5]);
        this.descurtidas = Integer.parseInt(atributos[6]);
    }

    public Postagem(String texto, Perfil perfil) throws NullAtributesException{

        if(Optional.ofNullable(texto).isEmpty() || Optional.ofNullable(perfil).isEmpty())
            throw new NullAtributesException();

        this.texto = texto;
        this.perfil = perfil;
        this.curtidas = 0;
        this.descurtidas = 0;
        this.data = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }


    public boolean ehPopular(){
        return (curtidas - descurtidas) >= 0.5 * descurtidas;
    }

    public void curtir(){
        curtidas++;
    }

    public void descurtir(){
        descurtidas++;
    }

    public boolean temAtributosNulos () {
        return(Optional.ofNullable(id).isEmpty() || Optional.ofNullable(texto).isEmpty() || 
            Optional.ofNullable(data).isEmpty() || Optional.ofNullable(perfil).isEmpty() || 
            Optional.ofNullable(curtidas).isEmpty() || Optional.ofNullable(descurtidas).isEmpty());
    }
}