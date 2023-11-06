package com.ruanbianca.redesocial.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

public class ManipuladorDeArquivos {
    public static boolean arquivoExiste(String path){
        return Files.exists(Paths.get(path));
    }
    public static String lerArquivo(String caminho){
        StringBuilder conteudo = new StringBuilder();
       
        BufferedReader leitor;
        try {
            leitor = new BufferedReader(new FileReader(caminho));
            try{
                String linha = leitor.readLine();
                for(; linha != null; linha = leitor.readLine())
                    conteudo.append(linha);
                leitor.close();
            }catch(IOException e){
                System.out.println("Problema durante leitura do arquivo!");
            }
        }catch (FileNotFoundException e){
            System.out.println("Arquivo não encontrado!");
        }
        return conteudo.toString();
    }
    public static boolean gravarArquivo(String caminho, String texto, boolean append){
        try {
            BufferedWriter gravador = new BufferedWriter(new FileWriter(caminho,append));
            gravador.write(texto);
            if(texto.charAt(texto.length()-1) == '\n')
                gravador.newLine();
            gravador.close();
        }
        catch (IOException e){
            System.out.println("Erro durante gravação de arquivo!");
            return false;
        }return true;
    }
    public static ArrayList <String> lerLinhas(String caminho){
        ArrayList<String> linhas = new ArrayList<>();
        try{
            Files.lines(Paths.get(caminho)).forEach(line -> linhas.add(line.toString()));
        }catch(IOException e){
            System.out.println("Erro durante a leitura do arquivo! "+e.getMessage());
        }catch(Exception e){
            System.out.println("Erro desconhecido durante operacao no arquivo! "+e.getMessage());
        }return linhas;
    }
     public static ArrayList <String> lerLinhas(Path caminho){
        ArrayList<String> linhas = new ArrayList<>();
        try{
            Files.lines(caminho).forEach(line -> linhas.add(line.toString()));
        }catch(IOException e){
            System.out.println("Erro durante a leitura do arquivo! "+e.getMessage());
        }catch(Exception e){
            System.out.println("Erro desconhecido durante operacao no arquivo! "+e.getMessage());
        }return linhas;
    }

    public static void gravarLinhas(Path caminhoArquivo, String conteudo){
        try{
            Files.write(caminhoArquivo,conteudo.getBytes());
        }catch(IOException e){
            System.out.println("Erro durante a gravação no arquivo! "+e.getMessage());
        }catch(Exception e){
            System.out.println("Erro desconhecido durante operacao no arquivo! "+e.getMessage());
        }
    }    


    public static void gravarLinhas(String caminho, String conteudo){
        try{
            Files.write(Path.of(caminho),conteudo.getBytes());
        }catch(IOException e){
            System.out.println("Erro durante a gravação no arquivo! "+e.getMessage());
        }catch(Exception e){
            System.out.println("Erro desconhecido durante operacao no arquivo! "+e.getMessage());
        }
    }
}