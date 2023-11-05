package com.ruanbianca;
import com.ruanbianca.redesocial.*;

public class App 
{
    RepositorioDePerfis perfis = new RepositorioDePerfis();
    RepositorioDePostagens postagens = new RepositorioDePostagens();
    RedeSocial Rubi = new RedeSocial(perfis,postagens);
    
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
       
    }
   

}