package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;

import static com.ruanbianca.redesocial.utils.ConsoleColors.*;
import com.ruanbianca.redesocial.SocialException;


public class Postagem {

    
    private @Getter String texto;
    private @Getter Perfil perfil; 
    private @Getter LocalDateTime data;
    private @Getter int curtidas;
    private @Getter int descurtidas;
    private @Getter UUID id;


    public String exibirPostagem(){
        return PURPLE_BOLD_BRIGHT+"╔══════════════════════════════════════════════════════\n"+
        "║    "+getPerfil().getNome()+RESET+PURPLE_BRIGHT+" @"+getPerfil().getUsername()+RESET+"\n║\n║    "+  
            getTexto()+"\n║\n║    "
            +RED_BOLD_BRIGHT+getCurtidas()+" ❤️   " +RESET + YELLOW_BOLD_BRIGHT + getDescurtidas() + " 👎"+RESET+"            •" +mostrarData() + YELLOW_BOLD
            + "\n╚══════════════════════════════════════════════════════\n"+RESET;
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


    public String mostrarData() {

        LocalDateTime data = this.data;
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String agoraFormatado = RESET+WHITE_BRIGHT+data.format(formatoHora) + RESET+"  " 
        + data.format(formatoData);
        return agoraFormatado;
    }
}