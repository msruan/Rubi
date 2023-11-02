package com.ruanbianca.redesocial;
import java.util.ArrayList;
import ij.IJ;
import ij.ImagePlus;
public class Teste {
    public static void main(String[] args) {
        ImagePlus foto = IJ.openImage("/home/ruan/Pictures/Wallpapers/lago.jpg");
        foto.show();
      
        ArrayList<Perfil> perfisArray = new ArrayList<>();
        Perfil bianca = new Perfil(1,"c","@");
        perfisArray.add(bianca);
        RepositorioDePerfis repoPerfis = new RepositorioDePerfis(perfisArray);

        repoPerfis.incluir(new Perfil(1,"Bianca","binca-bezerra"));
        repoPerfis.incluir(new Perfil(2,"Ruan","ruan-macs"));
        
        //RepositorioDePostagens posts = new RepositorioDePostagens(new Postagem(12346789,"só bebo coca",5,7,LocalDateTime.now(),null));
        //PostagemAvancada post2 = new PostagemAvancada(3389, "ouviram do ipiranga as margens placiadas", 0, 20,null,null,1000,"euqfiz","omg");
        //posts.incluir(post2);
        RedeSocial instagram = new RedeSocial(repoPerfis,null);
        instagram.incluirPerfil(new Perfil(564, "Patrocinio", "patro-rajada@gmail.com"));
        instagram.getRepositorioDePerfis().incluir(new Perfil(123, "Bia", "biabezerra@gmail.com"));
        //System.out.println(instagram.getRepositorioDePerfis().consultar(123, "Bia", null).getNome());
        System.out.println(instagram.getRepositorioDePerfis().consultar(564, "Patrocinio", "patro-rajada@gmail.com").getNome());
        for(Perfil p : instagram.getRepositorioDePerfis().getPerfis()){
            System.out.println(p.getNome());
        }

    }
    
        
}
