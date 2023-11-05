package com.ruanbianca.redesocial;

import java.util.Optional;
import java.util.UUID;


public class Teste {
    public static void main(String[] args) {
      //ImagePlus foto = IJ.openImage("/home/ruan/Pictures/Wallpapers/lago.jpg");voltei
      //foto.show();

      // Perfil ruan = new Perfil("msruan", "Ruan Macedo Santos","santosruan2021@gmail.com");
      // Perfil bianca = new Perfil("bbia", "Bianca Bezerra","bianca-bezerra@gmail.com");
      // Perfil patro = new Perfil("patro", "LF Patrocinio","patro-rajada@gmail.com");
      // //parece que funcionou
      // //qual o log do terminal?
  

      // RepositorioDePerfis meusPerfis = new RepositorioDePerfis();
      
      // meusPerfis.incluir(bianca);
      // meusPerfis.incluir(ruan);

      // PostagemAvancada post1 = new PostagemAvancada("i just love rock!",ruan,"rock","roll","drogs","thinking");
      // PostagemAvancada post2 = new PostagemAvancada("alguem quer dancar forro?",ruan,"forro","danca","felicidade");
      // PostagemAvancada post3 = new PostagemAvancada("mds eh a dua lipa!!",ruan,"onekiss","diva","pop");
      // PostagemAvancada post4 = new PostagemAvancada("mds eh a juju do pix!!",ruan,"humor","pix","nubank");
      // PostagemAvancada post5 = new PostagemAvancada("Deus eh maior <3", ruan,"deus","gospel","louvor");
      // Postagem post6 = new Postagem("Deus eh mais", ruan);

      // RepositorioDePostagens meusPosts = new RepositorioDePostagens();
      // meusPosts.incluir(post1);
      // meusPosts.incluir(post2);
      // meusPosts.incluir(post3);
      // meusPosts.incluir(post4);
      // meusPosts.incluir(post5);
      // meusPosts.incluir(post6);
      
      RedeSocial instagram = new RedeSocial();
      //instagram.incluirPerfil(patro);
      //  for(Perfil perfil : instagram.getRepositorioDePerfis().getPerfis()){
      //      System.out.println(perfil);
        //}
      // instagram.salvarPerfis(System.getProperty("user.dir")+"/db/perfis.txt");
      // instagram.salvarPostagens(System.getProperty("user.dir")+"/db/postagens.txt");
      instagram.resgatarPerfis(System.getProperty("user.dir")+"/db/perfis.txt");
      instagram.resgatarPostagens0(System.getProperty("user.dir")+"/db/postagens.txt");

      // for(Perfil perfil : instagram.getRepositorioDePerfis().getPerfis()){//me fala o resultado depois
      //      System.out.println(perfil);
   
      for(Postagem post : instagram.getRepositorioDePostagens().getPostagens()){
              System.out.println(post.getTexto());
   
}
    }
  }
