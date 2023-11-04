package com.ruanbianca.redesocial;
//import ij.IJ;
//import ij.ImagePlus;//9fd42fc8-9a86-41a9-baeb-e930b881aa91;bbia;Bianca Bezerra;bianca-bezerra@gmail.com2e53ba1f-75a8-4994-94e4-be4e500fb22c;msruan;Ruan Macedo Santos;santosruan2021@gmail.com
//nao sei se tá lendo a quebra de linha..
public class Teste {//executa aí namoralzinha
    public static void main(String[] args) {
        //ImagePlus foto = IJ.openImage("/home/ruan/Pictures/Wallpapers/lago.jpg");
        //foto.show();

        // Perfil ruan = new Perfil("msruan", "Ruan Macedo Santos","santosruan2021@gmail.com");
        // Perfil bianca = new Perfil("bbia", "Bianca Bezerra","bianca-bezerra@gmail.com");
        // //Perfil patro = new Perfil("patro", "LF Patrocinio","patro-rajada@gmail.com");
    

        RepositorioDePerfis meusPerfis = new RepositorioDePerfis();
        // meusPerfis.incluir(bianca);
        // meusPerfis.incluir(ruan);

        // PostagemAvancada post1 = new PostagemAvancada("i just love rock!",ruan,"rock","roll","drogs","thinking");
        // PostagemAvancada post2 = new PostagemAvancada("alguem quer dancar forro?",ruan,"forro","danca","felicidade");
        // PostagemAvancada post3 = new PostagemAvancada("mds eh a dua lipa!!",ruan,"onekiss","diva","pop");
        // PostagemAvancada post4 = new PostagemAvancada("mds eh a juju do pix!!",ruan,"humor","pix","nubank");
        // PostagemAvancada post5 = new PostagemAvancada("Deus eh maior <3", ruan,"deus","gospel","louvor");
        // Postagem post6 = new Postagem("Deus é mais", ruan);

        RepositorioDePostagens meusPosts = new RepositorioDePostagens();
        // meusPosts.incluir(post1);
        // meusPosts.incluir(post2);
        // meusPosts.incluir(post3);
        // meusPosts.incluir(post4);
        // meusPosts.incluir(post5);
        // meusPosts.incluir(post6);
        

        RedeSocial instagram = new RedeSocial();
        //instagram.resgatarPerfis0(System.getProperty("user.dir")+"/db/perfis.txt");
        //instagram.resgatarPostagens0(System.getProperty("user.dir")+"/db/perfis.txt");
        
        //instagram.resgatarPostagens3(System.getProperty("user.dir")+"/db/postagens.txt"); testa
        // for(Postagem postinho : instagram.getRepositorioDePostagens().getPostagens()){
        //     postinho.getTexto();
        // }
        //for(Perfil postinho : instagram.getRepositorioDePerfis().getPerfis()){
          //  postinho.getNome();
        //}
        for (String linha : RedeSocial.lerArquivo(System.getProperty("user.dir")+"/db/perfis.txt").split("\n")){
        System.out.println(linha);
        }
//go rockstar
//ta vendo?nao
//???w o q rolou?
    }//mudou nada oiiii// so printou a mesma coisa nao 
    
    //perfeito/deu quebra de linha AAAAH entao é isso mesmo]  
    //rlx amg vou aproveitar pra banhar //pode dar um git push?
    //dou
    //ei, vou sair bem aqui pra escolher um sabor de pizza, 10 min no maximo eu volto
    //9fd42fc8-9a86-41a9-baeb-e930b881aa91;bbia;Bianca Bezerra;bianca-bezerra@gmail.com2e53ba1f-75a8-4994-94e4-be4e500fb22c;msruan;Ruan Macedo Santos;santosruan2021@gmail.com
}
// tinha dado certo faz tempo e ahnete mongando tomanocu
//ele n ta achando nextLine()
//agora ver se l?e 
//agora foi KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK