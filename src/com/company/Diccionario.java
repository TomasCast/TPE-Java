package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Diccionario {

    private HashMap<String, Integer> dic = new HashMap<>();

    private ArrayList<Paquete> paquetes = new ArrayList<>();


    public void addPaq(Paquete paq){
        paquetes.add(paq);
        dic.put(paq.getName(),paquetes.size()-1);
    }

    public int size(){
        return paquetes.size();
    }

    public Paquete getPaquete(int indice){
        return paquetes.get(indice);
    }

    public int getNumero(String nombre){
        return dic.get(nombre);
    }

    public String getNombre(int numero){
        return paquetes.get(numero).getName();
    }

    public Paquete buscarPaquete(String nombre){
        for(int i = 0; i < paquetes.size(); i++){
            if(paquetes.get(i).define(nombre)){
                return paquetes.get(i);
            }
        }
        return null;
    }

    public ArrayList<String> traducirIntAString(Set<Integer> set){
        ArrayList<String> s = new ArrayList<>();
        for(Integer i : set){
            s.add(getNombre(i));
        }
        return s;
    }

}
