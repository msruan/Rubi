package com.ruanbianca.redesocial;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static com.ruanbianca.redesocial.utils.MenuUtils.*;


public class App 
{
    //RepositorioDePerfis perfis = new RepositorioDePerfis();
    //RepositorioDePostagens postagens = new RepositorioDePostagens();
    //eai? a gente tenta fazer o q tinha peansdo?
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
    
    RedeSocial Rubi = new RedeSocial();//tem umc construor vazio pra redesocial
    Rubi.resgatarPerfis(Rubi.getCaminhoBancoDeDados("Perfil"));
    Rubi.resgatarPostagens(Rubi.getCaminhoBancoDeDados("Postagem"));
    Scanner input = new Scanner(System.in);
    String opcao = "";

    public void executar(){
        do{
            
            opcao = input.nextLine();

            switch (opcao) {
                case INCLUIR_PERFIL:
                    Perfil novoPerfil;
                    String username = lerString("Digite seu novo username: ",input);
                    String nome = lerString("Qual o seu nome?",input);
                    String email = lerString("Qual o seu email?",input);
                    break;//ei, a gente vai fazer o q a gente tinha penasndo mesmo,?

                case INCLUIR_POSTAGEM:
                    
                    break;

                case CONSULTAR_PERFIL:
                    username = lerString("Digite o username do perfil buscado: ", input);
                    Optional<Perfil> perfilBuscado = Rubi.consultarPerfilPorUsername(username);
                    if(perfilBuscado.isPresent()){
                        System.out.println(perfilBuscado.get());
                    }
                    break;

                case CONSULTAR_POSTAGEM:

                    break;

                case EXIBIR_POST_PERFIL:
                    ArrayList<Postagem> postagensEncontradas;
                    username = lerString("Digite o username do perfil buscado: ", input);
                    postagensEncontradas = Rubi.exibirPostagensPorPerfil(username);
                    if(postagensEncontradas != null){
                        for(Postagem postagem : postagensEncontradas){
                            System.out.println(postagem);
                        }
                    }
                    break;

                case EXIBIR_POST_HASHTAG:
                    ArrayList<PostagemAvancada> postagensAvancadasEncontradas;
                    String hashtag = lerString("Digite a hashtag buscada: ", input);
                    postagensAvancadasEncontradas = Rubi.exibirPostagensPorHashtag(hashtag);
                    if(postagensAvancadasEncontradas != null){
                        for(PostagemAvancada post : postagensAvancadasEncontradas){
                            System.out.println(post);
                        }
                    }
                    break;
                case EXIBIR_POST_POPULARES:
                    ArrayList<Postagem> postagensPopulares;
                    postagensPopulares = Rubi.exibirPostagensPopulares();
                    if(postagensPopulares != null){
                        for(Postagem post : postagensPopulares){
                            System.out.println(post);
                        }
                    }
                    break;
                case EXIBIR_HASHTAGS_POPULARES:
                    break;
                default: 
                    System.out.println("Opção inválida!");
                    break;
            }

        }while(!opcao.equals("0"));
    }
    
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
       
    }
   

}