package com.ruanbianca.redesocial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.Getter;

public class PostagemAvancada extends Postagem {

    private final Integer numeroPadraoVisualizacoesRestantes = 10;
    private @Getter Integer visualizacoesRestantes;
    private @Getter ArrayList<String> hashtags;

    public PostagemAvancada(UUID perfilId, String texto, ArrayList<String> hashtags) throws NullAtributesException {

        super(perfilId, texto);
        this.visualizacoesRestantes = numeroPadraoVisualizacoesRestantes;
        this.hashtags = (Optional.ofNullable(hashtags).isEmpty()) ? new ArrayList<>() : hashtags;
    }

    public PostagemAvancada(UUID perfilId, String texto, String... hashtags) {

        this(perfilId, texto, new ArrayList<>(Arrays.asList(hashtags)));
    }

    public PostagemAvancada(UUID id, UUID perfilId, LocalDateTime data, String texto, int curtidas, int descurtidas,
            Integer visualizacoesRestantes, ArrayList<String> hashtags) {

        super(id, perfilId, data, texto, curtidas, descurtidas);
        this.visualizacoesRestantes = visualizacoesRestantes;
        this.hashtags = hashtags;
    }

    public void adicionarHashtag(String hashtag) {

        if (Optional.ofNullable(hashtag).isPresent())
            hashtags.add(hashtag);
    }

    public boolean ehExibivel() {

        return visualizacoesRestantes > 0;
    }

    public boolean existeHashtag(String hashtag) {

        Stream<String> hashs = hashtags.stream();
        return hashs.anyMatch(h -> h.equals(hashtag));
    }

    public void decrementarVisualizacoes() {

        if (ehExibivel())
            visualizacoesRestantes--;
    }

    public void incrementarVisualizacoes() {
        visualizacoesRestantes++;
    }

    public String getHashtagsParaDb() {
        StringBuilder strHashtags = new StringBuilder();
        for (int i = 0; i < hashtags.size(); i++) {
            if (i > 0)
                strHashtags.append("#");
            strHashtags.append(hashtags.get(i));
        }
        return strHashtags.toString();
    }
}