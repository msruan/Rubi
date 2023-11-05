package com.ruanbianca.redesocial;

public class Teste2 {
    public static void main(String[] args) {
        RedeSocial xwitter = new RedeSocial();
        xwitter.resgatarPerfis(xwitter.getCaminhoDoBancoDeDados("Perfil"));
        xwitter.resgatarPostagens(xwitter.getCaminhoDoBancoDeDados("Postagem"));
        for(Postagem post : xwitter.exibirPostagensPorPerfil("ryofav"))
            System.out.println(post.getTexto());
    }//aqui tava resgaratPerfis kksksksksk duas vezes

}//
