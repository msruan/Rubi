package com.ruanbianca.redesocial;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import static com.ruanbianca.redesocial.utils.ConsoleColors.*;
import com.ruanbianca.redesocial.SocialException;

public class PostagemAvancada extends Postagem {

    private final Integer numeroPadraoVisualizacoesRestantes = 10;
    @Getter
    private Integer visualizacoesRestantes;
    @Getter
    private ArrayList<String> hashtags;


    public String exibirPostagem() {

        this.decrementarVisualizacoes();
        StringBuilder strHashtags = new StringBuilder();
        for(String hash:  hashtags){
            strHashtags.append("#"+hash+" ");
        }
        return PURPLE_BOLD+"╔═══════════════════════════════════════════════════════\n"+
         "║    "+getPerfil().getNome()+RESET+PURPLE_BRIGHT+" @"+getPerfil().getUsername()+RESET+"\n║\n║    "+
            getTexto()+"\n║    "+GREEN_BOLD_BRIGHT+strHashtags+RESET+"\n║\n║    "
            +RED_BOLD_BRIGHT+getCurtidas()+" ❤️   " +RESET + YELLOW_BOLD_BRIGHT + getDescurtidas() +" 👎   "
            +RESET+BLUE_BOLD_BRIGHT +getVisualizacoesRestantes()+ " 👀      "+RESET+"•" +mostrarData() + BLUE_BOLD+
            "\n╚══════════════════════════════════════════════════════\n"+RESET;
    }
    
    @Override
    public String toString() {
        StringBuilder strHashtags = new StringBuilder();
        hashtags.stream().forEach(hash -> strHashtags.append("#"+hash));
        return 1 + ";" + getId().toString() + ";" + getPerfil().getId().toString() + ";" + 
            getData().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ";" + super.getTexto() + ";" + 
            String.valueOf(super.getCurtidas()) + ";" + String.valueOf(super.getDescurtidas()) + ";" +
            String.valueOf(getVisualizacoesRestantes()) +";" +
            (Optional.ofNullable(strHashtags.toString()).isPresent() ? strHashtags.toString() : null ) + ";" + '\n';  
    }//:3
//fofo ne n
    public PostagemAvancada(Perfil perfil, String postagem) {
        
        // | Tipo |    IdPost   |    IdPerfil    |   Data  | Texto  | Likes | Deslikes | ViewsRestantes | Hashtags<> |*/
        super(perfil,postagem);
        String []atributos = postagem.split(";");
        visualizacoesRestantes = Integer.valueOf(atributos[7]);
        hashtags = new ArrayList<>(Arrays.asList(atributos[8].split("#")));
    }
    
    public PostagemAvancada(String texto, Perfil perfil, ArrayList<String> hashtags) {
        
        super(texto, perfil);
        this.visualizacoesRestantes = numeroPadraoVisualizacoesRestantes;
        this.hashtags = (Optional.ofNullable(hashtags).isEmpty()) ? new ArrayList<>() : hashtags;
    }

    public PostagemAvancada(String texto, Perfil perfil, String ... hashtags) {

        this(texto, perfil, new ArrayList<>(Arrays.asList(hashtags)));
    }

    public void adicionarHashtag(String hashtag) {
        if(Optional.ofNullable(hashtag).isPresent())
            hashtags.add(hashtag);
    }

    public boolean ehExibivel(){
        return visualizacoesRestantes > 0;
    }

    public boolean existeHashtag(String hashtag) {
        
        Stream <String> hashs = hashtags.stream();

        return hashs.anyMatch(h -> h.equals(hashtag));
    }

    public void decrementarVisualizacoes() {
        if(ehExibivel())
            visualizacoesRestantes--;
    }
    public void incrementarVisualizacoes() {
        visualizacoesRestantes++;
    }
}