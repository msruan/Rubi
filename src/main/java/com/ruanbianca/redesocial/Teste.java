package com.ruanbianca.redesocial;
//import ij.IJ;
//import ij.ImagePlus;
public class Teste {
    public static void main(String[] args) {
        //ImagePlus foto = IJ.openImage("/home/ruan/Pictures/Wallpapers/lago.jpg");
        //foto.show();
        Perfil ruan = new Perfil("msruan", "Ruan Macedo Santos","santosruan2021@gmail.com");
        //Perfil ruanDuplicado = new Perfil("msruan", "Ruan Macedo Santos","santosruan2021@gmail.com");
        Perfil bianca = new Perfil("bbia", "Bianca Bezerra","bianca-bezerra@gmail.com");
        Perfil patro = new Perfil("patro", "LF Patrocinio","patro-rajada@gmail.com");
    

        RepositorioDePerfis meusPerfis = new RepositorioDePerfis();
        meusPerfis.incluir(bianca);
        meusPerfis.incluir(ruan);

        PostagemAvancada post1 = new PostagemAvancada("i just love rock!",ruan,"rock","roll","drogs","thinking");
        PostagemAvancada post2 = new PostagemAvancada("alguem quer dancar forro?",ruan,"forro","danca","felicidade");
        PostagemAvancada post3 = new PostagemAvancada("mds eh a dua lipa!!",ruan,"onekiss","diva","pop");
        PostagemAvancada post4 = new PostagemAvancada("mds eh a juju do pix!!",ruan,"humor","pix","nubank");
        PostagemAvancada post5 = new PostagemAvancada("Deus é maior <3", ruan,"deus","gospel","louvor");
        Postagem post6 = new Postagem("Deus é mais", ruan);

        RepositorioDePostagens meusPosts = new RepositorioDePostagens();
        meusPosts.incluir(post1);
        meusPosts.incluir(post2);
        meusPosts.incluir(post3);
        meusPosts.incluir(post4);
        meusPosts.incluir(post5);
        meusPosts.incluir(post6);

        RedeSocial instagram = new RedeSocial(meusPerfis,meusPosts);
        //instagram.incluirPerfil(patro);
        // System.out.println(post5);
        // System.out.println(avancada);
        // System.out.println(post6);
        // System.out.println(normal);
        System.out.println(System.getProperty("user.dir"));


    }   
}