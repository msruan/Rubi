package com.ruanbianca.redesocial;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JOptionPane;

import static com.ruanbianca.redesocial.utils.MenuUtils.*;
import static com.ruanbianca.redesocial.utils.ConsoleColors.*;


public class App {

    final String INCLUIR_PERFIL = "1";
    final String INCLUIR_POSTAGEM = "2";
    final String CONSULTAR_PERFIL = "3";
    final String CONSULTAR_POSTAGEM = "4";
    
    final String EXIBIR_POST_PERFIL = "5";
    final String EXIBIR_POST_HASHTAG = "6";
    final String EXIBIR_POST_POPULARES = "7";
    final String EXIBIR_HASHTAGS_POPULARES = "8";

    final String ATUALIZAR_PERFIL = "9";
    final String REMOVER_PERFIL = "10";
    final String REMOVER_POSTAGEM = "11";
    final String SAIR = "0";

    final static int USERNAME = 0;
    final static int EMAIL = 1;

    Scanner input = new Scanner(System.in);

    public static void main( String[] args ) throws Exception{

        RedeSocial Rubi;
        IRepositorioDePerfis perfis;
        IRepositorioDePostagens postagens;

        final int ARRAY = 0;
        final int FILE = 1;

        String[] persistencias = { "Array", "Arquivos", "Sql" };
        int persistencia = JOptionPane.showOptionDialog(null, "Escolha o tipo de persistência desejado", "Bem vindo!", 0, JOptionPane.QUESTION_MESSAGE, null, persistencias, persistencias[0]);

        if(persistencia == ARRAY){
            perfis = new RepositorioDePerfisArray();
            postagens = new RepositorioDePostagensArray();
        }

        else if (persistencia == FILE){ 
            perfis = new RepositorioDePerfisFile();
            postagens = new RepositorioDePostagensFile();
        }

        else {
            perfis = new RepositorioDePerfisSql();
            postagens = new RepositorioDePostagensSql();
        }

        Rubi = new RedeSocial(perfis, postagens);
        App RubiApp = new App();
        RubiApp.executar(Rubi); 
    }


    public void pausar() {

        String enter = GREEN_BOLD_BRIGHT +"<Enter>"+RESET;
        System.out.print(YELLOW_BOLD_BRIGHT+ "\n\nPressione " + enter + YELLOW_BOLD_BRIGHT + " para continuar..."+ RESET);
        input.nextLine();
        limparConsole();
    }
    

    public void executar(RedeSocial Rubi){
        
        String titulo = "RUBI";
        String opcoes = "Incluir Perfil,Incluir Postagem,Consultar Perfil,Consultar Postagem,Exibir Postagens por Perfil,Exibir Postagens por Hashtag,Exibir Postagens Populares,Exibir Hashtags Populares,Atualizar Perfil,Remover Perfil,Remover Postagem";
        String menu = gerarMenu(titulo,opcoes);
        String opcao = "";
        limparConsole();
        try{
            menuprincipal:do{

                System.out.print(menu);
                opcao = input.nextLine();

                switch (opcao) {

                    case INCLUIR_PERFIL:

                        
                        String nome = lerEValidarAtributo("Digite seu nome: ", 80);
                        String username = lerValidarEBuscarAtributo("Digite seu username: ", 30, Rubi, USERNAME);
                        String email = lerValidarEBuscarAtributo("Digite seu email: ", 320, Rubi, EMAIL);
                        String biografia = lerEValidarAtributo("Digite sua bio: ", 100);

                        Rubi.incluirPerfil(new Perfil(username, nome, email,biografia));

                        System.out.println(GREEN_BOLD_BRIGHT+"\nSeja bem vindo "+nome+"! :)\n"+RESET);
                        break;

                    case INCLUIR_POSTAGEM:

                        String usernamePost;
                        Optional <Perfil> perfilUser;
                        do {
                            usernamePost= lerEValidarAtributo("Digite seu username: ",30);
                            perfilUser = Rubi.consultarPerfilPorUsername(usernamePost);
                            if(perfilUser.isEmpty()){
                                if(lerString2("Usuário não encontrado! Tentar novamente? ").equals("nao")){
                                    limparConsole();
                                    continue menuprincipal;
                                }
                            }else 
                                break;
                        }while(true);
                        String texto = lerEValidarAtributo("Digite o conteúdo do texto: ",400);
                        Postagem novaPostagem = new Postagem(perfilUser.get().getId(),texto);

                        if (lerString("Deseja por hashtags? (0-Enter, 1-Sim)",input).equals("1")){

                            ArrayList<String> hashsProConstrutor = new ArrayList<>();
                            String hashtag = lerString("Digite uma hashtag: ",input);
                            hashsProConstrutor.add(hashtag);

                            boolean querBotarMaisUmaHashtag;

                            do{
                                if (lerString("Deseja por mais uma hashtag? (0-Enter, 1-Sim)",input).equals("1")){

                                    String hashDoLoop = lerString("Digite outra hashtag: ",input);
                                    hashsProConstrutor.add(hashDoLoop);
                                    querBotarMaisUmaHashtag = true;

                                } else
                                    querBotarMaisUmaHashtag = false;
                                
                            }while(querBotarMaisUmaHashtag);

                            novaPostagem = new PostagemAvancada(perfilUser.get().getId(),texto,hashsProConstrutor);
                    
                        }else{
                            try{
                                novaPostagem = new Postagem(perfilUser.get().getId(),texto);
                            }catch(com.ruanbianca.redesocial.NullAtributesException e){
                                System.out.println("Você deixou algum atributo nulo!!!");
                            }
                        }
                    
                        Rubi.incluirPostagem(novaPostagem);
                        break;

                    case CONSULTAR_PERFIL:
                        
                        username = lerString("Digite o username do perfil buscado: ", input);
                        Optional<Perfil> perfilBuscado;                       
                        perfilBuscado = Rubi.consultarPerfilPorUsername(username);                        
                        if(perfilBuscado.isPresent())
                            System.out.println(RedeSocial.exibirPerfil(perfilBuscado.get()));
                            
                        else
                            System.out.println(RED_BOLD_BRIGHT+"Perfil não encontrado!"+RESET);
                        
                        break;
                        
                    
                    case CONSULTAR_POSTAGEM:
                    
                        texto = lerString("Digite o texto da postagem buscada: ", input);
                        username = lerString("Digite o username do perfil buscado: ", input);
                        String hashtagParaOConsultarPostagem = null;
                        if(lerString("Deseja buscar por hashtag tambem? (Enter - Nao, 1 - Sim)", input).equals("1")){
                            hashtagParaOConsultarPostagem = lerString("Digite uma hashtag: ",input);
                        }
                        perfilBuscado = Rubi.consultarPerfilPorUsername(username);
                        ArrayList<Postagem> postagemBuscada = Rubi.consultarPostagens(texto, perfilBuscado.get(), hashtagParaOConsultarPostagem);
                        if(Optional.ofNullable(postagemBuscada).isPresent()){
                            exibirFeed(postagemBuscada, Rubi);
                        }else{
                            System.out.println(RED_BOLD_BRIGHT+"Postagem não encontrada!"+RESET);
                        }
                        break;


                    case EXIBIR_POST_PERFIL:

                        ArrayList<Postagem> postagensEncontradas;
                        username = lerString("Digite o username do perfil buscado: ", input);
                        postagensEncontradas = Rubi.exibirPostagensPorPerfil(username);
                        if(Optional.ofNullable(postagensEncontradas).isPresent() && postagensEncontradas.size()>0){
                            exibirFeed(postagensEncontradas,Rubi);
                        }else{
                            System.out.println(RED_BOLD_BRIGHT+"Nenhuma postagem encontrada!"+RESET);
                        }
                        break;

                    case EXIBIR_POST_HASHTAG:

                        ArrayList<PostagemAvancada> postagensAvancadasEncontradas;
                        String hashtagBuscada = lerString("Digite a hashtag buscada: ", input).split("#")[0];
                        postagensAvancadasEncontradas = Rubi.exibirPostagensPorHashtag(hashtagBuscada);
                        ArrayList <Postagem> auxiliar = new ArrayList<>(postagensAvancadasEncontradas);

                        if(Optional.ofNullable(postagensAvancadasEncontradas).isPresent() && postagensAvancadasEncontradas.size()>0){
                            exibirFeed(auxiliar, Rubi);
                        }else{

                            System.out.println(RED_BOLD_BRIGHT+"Nenhuma postagem encontrada para essa hashtag!"+RESET);
                        }
                        break;


                    case EXIBIR_POST_POPULARES:
                    
                        ArrayList<Postagem> postagensPopulares;
                        postagensPopulares = Rubi.exibirPostagensPopulares();
                        if(Optional.ofNullable(postagensPopulares).isPresent() && postagensPopulares.size()>0){
                            exibirFeed(postagensPopulares, Rubi);
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
                        }else
                            System.out.println(RED_BOLD_BRIGHT+"Nenhuma hashtag encontrada!"+RESET);
                        
                        break;


                    case ATUALIZAR_PERFIL:

                        username = lerString("Digite o username do perfil buscado: ",input);
                        perfilBuscado = Rubi.consultarPerfilPorUsername(username);
                        if(perfilBuscado.isPresent()){
                            do{
                                String atributo = lerString("Digite o atributo que deseja atualizar: ",input);
                                switch(atributo){

                                    case "nome":
                                    
                                        String nomeAtualizado = lerEValidarAtributo("Digite o seu novo nome: ",80);
                                        Rubi.atualizarPerfil(username, nomeAtualizado, atributo);
                                        break;

                                    case "username":

                                        String usernameAtualizado = lerValidarEBuscarAtributo("Digite seu novo username: ", 30, Rubi, USERNAME);
                                        Rubi.atualizarPerfil(username, usernameAtualizado, atributo);
                                        break;

                                    case "email":

                                        String emailAtualizado = lerValidarEBuscarAtributo("Digite seu novo email: ", 320, Rubi, EMAIL);
                                        Rubi.atualizarPerfil(username, emailAtualizado, atributo);
                                        break;

                                    case "biografia":

                                        String biografiaAtualizada = lerEValidarAtributo("Digite a nova biografia: ",100);
                                        Rubi.atualizarPerfil(username, biografiaAtualizada, atributo);
                                        break;
                                    
                                    default:

                                        System.out.println(RED_BOLD_BRIGHT+"Atributo inválido"+RESET);
                                        break;
                                }

                            }while(lerString("Deseja atualizar outro atributo? ",input).equals("sim"));
                        }else{
                            System.out.println(RED_BOLD_BRIGHT+"Perfil não encontrado!"+RESET);
                        }
                        break;


                    case REMOVER_PERFIL:

                        username = lerEValidarAtributo("Digite o username do perfil buscado: ",30);
                        
                        Rubi.removerPerfil(username);
                        
                        System.out.println("Perfil removido com sucesso!");
                        break;


                    case SAIR:
                        break;


                    default:
                        System.out.println("Opção inválida!");
                        break;

                }
                if(!opcao.equals(SAIR))
                    pausar();
            
            }while(!opcao.equals(SAIR));

        } catch (Exception e) {
            e.getMessage();
        }
        input.close();
        
    }

    public static void exibirFeed(ArrayList<Postagem> postagens, RedeSocial rede) throws Exception{
        
        if(postagens.isEmpty())
            System.out.println("Oxi, não foi nenhuma postagem para o exibir");

        final String CURTIR_POSTAGEM = "1";
        final String DESCURTIR_POSTAGEM = "2";

        String feedAtualizado = "", resposta;
        Postagem postAtual;

        if(Optional.ofNullable(postagens).isPresent() && postagens.size()>0){
            for(int i  = 0; i < postagens.size(); i++) {   
                limparConsole();
                postAtual =  postagens.get(i);

                if(postAtual instanceof PostagemAvancada){

                    rede.decrementarVisualizacoes(postAtual.getId());
                }

                System.out.println(feedAtualizado + rede.exibirPostagem(postAtual));
                resposta = lerString2("Interagir?\n(Enter - Não, 1 - Curtir, 2 - Descurtir)\n>>> ");


                
                if(resposta.equals(CURTIR_POSTAGEM))

                    rede.curtir(postAtual.getId());

                else if(resposta.equals(DESCURTIR_POSTAGEM))

                    rede.descurtir(postAtual.getId());

                
                
                feedAtualizado += rede.exibirPostagem(rede.consultarPostagens(postAtual.getTexto(), null, null).get(0));

                

                if(i+1==postagens.size()){
                    limparConsole();
                    System.out.println(feedAtualizado);
                }
            }
        }else
            System.out.println(RED_BOLD_BRIGHT+"Nenhuma postagem encontrada!"+RESET);
                  
    }


    public static String lerEValidarAtributo(String message, int lenMax){

        String label;
        while(true){

            label = lerString2(message);

            if(Optional.ofNullable(label).isEmpty() ||  label.isEmpty() || label.isBlank()){
                System.out.println("Pls type something!");
                continue;
            }

            if(lenMax != 0 && label.length() > lenMax){
                System.out.printf("O limite de caracteres é %d!\n",lenMax);
                continue;
            }
            break;
        }
        return label;
    }


    public static String lerValidarEBuscarAtributo(String message, int lenMax, RedeSocial rede, int tipoAtributo) throws Exception{

        String atributo;

        while(true){

            atributo = lerEValidarAtributo(message, lenMax);
            
            if((tipoAtributo == USERNAME && rede.usuarioJaExite(null, atributo, null))
            || (tipoAtributo == EMAIL && rede.usuarioJaExite(null, null, atributo))){
                    System.out.println((tipoAtributo == USERNAME ? "Username" : "Email")+" já está em uso, por favor tente novamente!");
                    continue;
            }
            break;
        }
        return atributo;
    }
}