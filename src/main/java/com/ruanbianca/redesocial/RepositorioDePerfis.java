package com.ruanbianca.redesocial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RepositorioDePerfis {
    private ArrayList<Perfil> _perfis;

    public RepositorioDePerfis(ArrayList<Perfil> perfis) {
        this._perfis = perfis;
    }

    public ArrayList<Perfil> getPerfis(){
        return _perfis;
    }

    public void incluir(Perfil perfil){
        if(perfil != null)
            _perfis.add(perfil);
    }

    public Perfil consultar(Integer id, String nome, String email){

        Stream<Perfil> perfisFiltrados = getPerfis().stream();

        if(nome != null)
            perfisFiltrados = perfisFiltrados.filter(perfil -> perfil.getNome().equals(nome));
        if(email != null) 
            perfisFiltrados = perfisFiltrados.filter(perfil -> perfil.getEmail().equals(email));
        if(id != null)
            perfisFiltrados = perfisFiltrados.filter(perfil -> perfil.getId().equals(id));
            
        List <Perfil> saida = perfisFiltrados.toList();

        if(saida.size() > 0){
            return saida.get(0);
        }return null;


    }
    public static void main(String[] args) {
        ArrayList<Perfil> perfisArray = new ArrayList<>();
        perfisArray.add(new Perfil(1,"Bianca","binca-bezerra"));
        perfisArray.add(new Perfil(2,"Ruan","ruan-macs"));
        RepositorioDePerfis perfis = new RepositorioDePerfis(perfisArray); 
        System.out.println(perfis.consultar(2, null, null).getNome());  
        System.out.println(perfis.consultar(null, "Bianca", null).getNome());  
        System.out.println(perfis.consultar(3, null, null));

        ArrayList<Perfil> perfisArray2 = new ArrayList<>();
        Perfil bianca = new Perfil(1,"c","@");
        perfisArray2.add(bianca);
        
        RepositorioDePerfis perfis2 = new RepositorioDePerfis(perfisArray);
        Perfil soTrue = perfis2.consultar(1,null,null);
        System.out.println(soTrue.getNome()+soTrue.getEmail());
        
    }
}
