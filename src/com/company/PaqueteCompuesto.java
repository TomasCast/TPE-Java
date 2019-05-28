package com.company;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class PaqueteCompuesto{
    private ArrayList<Paquete> contenidos;
    /**Crea el paquete compuesto, incluyendo aquellos que estan en la lista.*/
    public PaqueteCompuesto(ArrayList<Paquete> lista){
        contenidos = lista;
    }
    /**Crea el paquete compuesto, vacio.*/
    public PaqueteCompuesto(){
        contenidos = new ArrayList<>();
    }
    /**Agrega el paquete p al paquete compuesto.*/
    public void addPaquete(Paquete p){
        contenidos.add(p);
    }
    /**Busca el paquete que define la clase dada. Si no existe un paquete que la defina
     * retorna null.*/
    public Paquete buscarPaquete(String clase){
        for (Paquete p : contenidos) {
            if(p.define(clase))
                return p;
        }
        return null;
    }

    public ArrayList<String> nombresContenidos(){
        ArrayList<String> salida = new ArrayList<>();
        for (Paquete p: contenidos) {
            salida.add(p.getName());
        }
        return salida;
    }

    // Agregar las operaciones en comun con paquete, y hacer herencia.
}
