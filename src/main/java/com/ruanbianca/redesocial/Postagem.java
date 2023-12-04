package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;

import static com.ruanbianca.redesocial.utils.ConsoleColors.*;


public class Postagem {

    
    private @Getter String texto;
    private @Getter UUID perfilId; 
    private @Getter LocalDateTime data;
    private @Getter int curtidas;
    private @Getter int descurtidas;
    private @Getter UUID id;
 
    public Postagem(UUID id, UUID perfilId, LocalDateTime data, String texto, int curtidas, int descurtidas) throws NullAtributesException{

        if(Optional.ofNullable(id).isEmpty() || Optional.ofNullable(perfilId).isEmpty() ||
            Optional.ofNullable(data).isEmpty() || Optional.ofNullable(texto).isEmpty() || 
            Optional.ofNullable(perfilId).isEmpty())

            throw new NullAtributesException();

        this.id = id;
        this.perfilId = perfilId;
        this.data = data;
        this.texto = texto;
        this.curtidas = curtidas;
        this.descurtidas = descurtidas;
    }

    public Postagem(UUID perfilId, String texto) throws NullAtributesException{

        if(Optional.ofNullable(texto).isEmpty() || Optional.ofNullable(perfilId).isEmpty())
            throw new NullAtributesException();

        this.texto = texto;
        this.perfilId = perfilId;
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

    
    public String mostrarData() {

        LocalDateTime data = this.data;
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String agoraFormatado = RESET+WHITE_BRIGHT+data.format(formatoHora) + RESET+"  " 
        + data.format(formatoData);
        return agoraFormatado;
    }
}