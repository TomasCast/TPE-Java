package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Diccionario {
    /**Esta clase tiene como objetivo agrupar un conjunto de paquetes y agregar funcionalidades sobre estos*/

    private HashMap<String, Integer> dic = new HashMap<>();

    private ArrayList<Paquete> paquetes = new ArrayList<>();

    /**Agrega un paquete al diccionario
     * @param paq paquete a agregar.*/
    public void addPaq(Paquete paq){
        paquetes.add(paq);
        dic.put(paq.getName(),paquetes.size()-1);
    }

    /**@returns retorna la cantidad de paquetes contenidos.*/
    public int size(){
        return paquetes.size();
    }

    /**Permite aceder a un paquete que esta en la posicion indicada en indice*/
    public Paquete getPaquete(int indice){
        if(indice < paquetes.size())
            return paquetes.get(indice);
        return null;
    }

    /**Devuelve el int que corrsponde al nombre de un paquete
     * @param nombre nombre del paquete del
     *               cual se quiere conocer su
     *               numero.*/
    public int getNumero(String nombre){
        return dic.get(nombre);
    }

    /**Devuelve el nombre del paquete que corresponde a un numero
     * @param numero numero del paquete del cual
     *               se quiere conocer su nombre.*/
    public String getNombre(int numero){
        return paquetes.get(numero).getName();
    }

    /**Permite conocer que paquete del diccionario define esa clase
     * @param nombreClase nombre de la clase de la cual
     *                    quiero conocer el paquete que
     *                    la define.
     * @returns paquete que define nombreClase. retorna null si este
     * no existe.*/
    public Paquete buscarPaquete(String nombreClase){
        for(int i = 0; i < paquetes.size(); i++){
            if(paquetes.get(i).define(nombreClase)){
                return paquetes.get(i);
            }
        }
        return null;
    }

    /**Permite traducir de integer a string un conjunto de numeros de paquete
     * @returns el correspondiente conjunto pero con los nombres de paquete que
     * corresponden a cada numero*/
    public ArrayList<String> traducirIntAString(Set<Integer> set){
        ArrayList<String> s = new ArrayList<>();
        for(Integer i : set){
            s.add(getNombre(i));
        }
        return s;
    }

}
