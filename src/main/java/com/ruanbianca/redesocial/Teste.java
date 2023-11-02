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

        String[] hashs = {"#rock"};
        PostagemAvancada post1 = new PostagemAvancada("i just love rock!",ruan,hashs);
        Postagem post2 = new Postagem("alguem quer dancar forro?",ruan);
        Postagem post3 = new Postagem("mds eh a dua lipa!!",ruan);
        Postagem post4 = new Postagem("mds eh a juju do pix!!",ruan);
        Postagem post5 = new Postagem("Deus é maior <3", ruan);
        Postagem post6 = new Postagem("Deus é mais", ruan);

        RepositorioDePostagens meusPosts = new RepositorioDePostagens();
        meusPosts.incluir(post1);
        meusPosts.incluir(post2);
        meusPosts.incluir(post3);
        meusPosts.incluir(post4);
        meusPosts.incluir(post5);
        meusPosts.incluir(post6);

        RedeSocial instagram = new RedeSocial(meusPerfis,meusPosts);
        instagram.incluirPerfil(patro);
        
        for(Postagem p : instagram.exibirPostagensPorPerfil(ruan.getId())){
            System.out.println(p.getTexto() + " posted on " + p.getData());
        }
    }   
}
