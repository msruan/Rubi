package com.ruanbianca.redesocial;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;


import static com.ruanbianca.redesocial.utils.MenuUtils.*;
import static com.ruanbianca.redesocial.utils.ConsoleColors.*;


public class App 
{

  
    final String INCLUIR_PERFIL = "1";
    final String INCLUIR_POSTAGEM = "2";
    final String CONSULTAR_PERFIL = "3";
    final String CONSULTAR_POSTAGEM = "4";
    // final String CURTIR_POSTAGEM = "5";
    // final String DESCURTIR_POSTAGEM = "6";
    final String EXIBIR_POST_PERFIL = "5";
    final String EXIBIR_POST_HASHTAG = "6";
    final String EXIBIR_POST_POPULARES = "7";
    final String EXIBIR_HASHTAGS_POPULARES = "8";
    Scanner input = new Scanner(System.in);

    void pausar() {
        System.out.println("\n\nPressione <Enter> para continuar...");
        input.nextLine();
    }
    
    public void executar(RedeSocial Rubi){
        
        String titulo = "RUBI";
        String opcoes = "Incluir Perfil,Incluir Postagem,Consultar Perfil,Consultar Postagem,Exibir Postagens por Perfil,Exibir Postagens por Hashtag,Exibir Postagens Populares,Exibir Hashtags Populares";
        String menu = gerarMenu(titulo,opcoes);
        String opcao = "";
        
        menuprincipal:do{

            System.out.print(menu);
            opcao = input.nextLine();

            switch (opcao) {

                case INCLUIR_PERFIL:
                    String nome = lerString("Qual o seu nome? ", input);
                    String username;
                    String email;

                    while (true) {
                        username = lerString("Digite um username: ", input);
                        if (Rubi.usuarioJaExite(null, username, null)) {
                            System.out.println("Username já está em uso!");
                            if (!lerSimOuNao("Deseja tentar outro? ", input)) {
                                limparConsole();
                                continue menuprincipal;
                            }
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        email = lerString("Digite um endereço de email: ", input);
                        if (Rubi.usuarioJaExite(null, null, email)) {
                            System.out.println("Email já está em uso!");
                            if (!lerSimOuNao("Deseja tentar outro? ", input)) {
                                limparConsole();
                                continue menuprincipal;
                            }
                        } else {
                            break;
                        }
                    }

                    Rubi.incluirPerfil(new Perfil(username, nome, email));

                    break;

                case INCLUIR_POSTAGEM:
                    Postagem novaPostagem;
                    String usernamePost;
                    Optional <Perfil> perfilUser;
                    do {
                        usernamePost= lerString("Digite seu username: ",input);
                        perfilUser = Rubi.consultarPerfilPorUsername(usernamePost);
                        if(perfilUser.isEmpty()){
                            if(!(lerSimOuNao("Usuário não encontrado! Tentar novamente? ",input))){
                                limparConsole();
                                continue menuprincipal;
                            }
                        }else 
                            break;
                    }while(true);
                    String texto = lerString("Digite o conteúdo do texto: ",input);
                    if(lerSimOuNao("Deseja por hashtags? ",input)){
                        String hashtags = lerString("Digite as hashtags separadas por # : ",input);
                        novaPostagem = new PostagemAvancada(texto,perfilUser.get(),hashtags.split("#"));
                    }else{
                        novaPostagem = new Postagem(texto,perfilUser.get());
                    }Rubi.incluirPostagem(novaPostagem);
                    break;

                case CONSULTAR_PERFIL:
                    
                    username = lerString("Digite o username do perfil buscado: ", input);
                    Optional<Perfil> perfilBuscado = Rubi.consultarPerfilPorUsername(username);
                    if(perfilBuscado.isPresent()){
                        System.out.println(perfilBuscado.get().getNome());
                    }else{
                        System.out.println("Perfil não encontrado!");
                    }
                    break;

                case CONSULTAR_POSTAGEM:
                    texto = lerString("Digite o texto da postagem buscada: ", input);
                    username = lerString("Digite o username do perfil buscado: ", input);
                    perfilBuscado = Rubi.consultarPerfilPorUsername(username);
                    String hashtag = lerString("Digite a hashtag buscada: ", input);
                    ArrayList<Postagem> postagemBuscada = Rubi.consultarPostagens(texto, perfilBuscado.get(), hashtag);
                    if(postagemBuscada != null){
                        for(Postagem postagem : postagemBuscada){
                            System.out.println(postagem.exibirPostagem());
                        }
                    }else{
                        System.out.println("Postagem não encontrada!");
                    }
                    break;

                case EXIBIR_POST_PERFIL:
                    ArrayList<Postagem> postagensEncontradas;
                    username = lerString("Digite o username do perfil buscado: ", input);
                    postagensEncontradas = Rubi.exibirPostagensPorPerfil(username);
                    if(postagensEncontradas != null){
                        for(Postagem postagem : postagensEncontradas){
                            System.out.println(postagem.exibirPostagem());
                        }
                    }else{
                        System.out.println("Nenhuma postagem encontrada para esse perfil!");
                    }
                    break;

                case EXIBIR_POST_HASHTAG:
                    ArrayList<PostagemAvancada> postagensAvancadasEncontradas;
                    String hashtagBuscada = lerString("Digite a hashtag buscada: ", input);
                    postagensAvancadasEncontradas = Rubi.exibirPostagensPorHashtag(hashtagBuscada);
                    if(Optional.ofNullable(postagensAvancadasEncontradas).isPresent()){
                        for(PostagemAvancada post : postagensAvancadasEncontradas){
                            System.out.println(post.exibirPostagem());
                        }
                        
                    }else{
                        System.out.println(RED_BOLD_BRIGHT+"Nenhuma postagem encontrada para essa hashtag!"+RESET);
                    }
                    break;
                case EXIBIR_POST_POPULARES:
                    ArrayList<Postagem> postagensPopulares;
                    postagensPopulares = Rubi.exibirPostagensPopulares();
                    if(Optional.ofNullable(postagensPopulares).isPresent()){
                        for(Postagem post : postagensPopulares){
                            System.out.println(post.exibirPostagem()); 
                        }
                    }else{
                        System.out.println(RED_BOLD_BRIGHT+"Nenhuma postagem encontrada!"+RESET);
                    }
                    break;
                case EXIBIR_HASHTAGS_POPULARES:
                    ArrayList<Hashtag> hashtagsPopulares;
                    hashtagsPopulares = Rubi.exibirHashtagsPopulares();
                    if(Optional.of(hashtagsPopulares).isPresent()){
                        for(Hashtag hash : hashtagsPopulares){
                            System.out.println(hash.getHashtag());
                        }
                    }else{
                        System.out.println(RED_BOLD_BRIGHT+"Nenhuma hashtag encontrada!"+RESET);//eu sei, to pensan
                    }
                    break;
                
                default: 
                    System.out.println("Opção inválida!");
                    break;
            }
            Rubi.salvarPerfis(Rubi.getCaminhoDoBancoDeDados("Perfil"));
            Rubi.salvarPostagens(Rubi.getCaminhoDoBancoDeDados("Postagem"));
    
            pausar();

        }while(!opcao.equals("0"));
    }
    
    public static void main( String[] args )
    {
        
        RedeSocial Rubi = new RedeSocial();
        Rubi.resgatarPerfis(Rubi.getCaminhoDoBancoDeDados("Perfil"));
        Rubi.resgatarPostagens(Rubi.getCaminhoDoBancoDeDados("Postagem"));
        App RubiApp = new App();
        RubiApp.executar(Rubi);
    }
   
}