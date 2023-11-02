package com.ruanbianca.redesocial;
//import ij.IJ;
//import ij.ImagePlus;
public class Teste {
    public static void main(String[] args) {
        //ImagePlus foto = IJ.openImage("/home/ruan/Pictures/Wallpapers/lago.jpg");
        //foto.show();
        Perfil ruan = new Perfil("msruan", "Ruan Macedo Santos","santosruan2021@gmail.com");
        Perfil ruanDuplicado = new Perfil("msruan", "Ruan Macedo Santos","santosruan2021@gmail.com");
        Perfil bianca = new Perfil("bbia", "Bianca Bezerra","bianca-bezerra@gmail.com");
        Perfil patro = new Perfil("patro", "LF Patrocinio","patro-rajada@gmail.com");

        RepositorioDePerfis meusPerfis = new RepositorioDePerfis();
        meusPerfis.incluir(bianca);
        meusPerfis.incluir(ruan);
        meusPerfis.incluir(ruanDuplicado);

        Postagem post1 = new Postagem("i just love rock!",ruan);
        Postagem post2 = new Postagem("alguem quer dancar forro?",bianca);
        Postagem post3 = new Postagem("mds eh a dua lipa!!",ruan);
        RepositorioDePostagens meusPosts = new RepositorioDePostagens();
        meusPosts.incluir(post1);
        meusPosts.incluir(post2);
        meusPosts.incluir(post3);

        RedeSocial instagram = new RedeSocial(meusPerfis,meusPosts);
        instagram.incluirPerfil(patro);
        // for(Perfil p : instagram.getRepositorioDePerfis().getPerfis()){
        //     System.out.println(p.getNome());
        // }
        // for(Postagem p : instagram.getRepositorioDePostagens().getPostagens()){
        //     System.out.println(p.getTexto() + " posted by " + p.getPerfil().getUsername());
        // }
        //obs: consultar tá funcionado?

    }
    
        
}
