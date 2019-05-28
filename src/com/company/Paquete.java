package com.company;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class Paquete {
    String name;
    private Set<String> clases;
    private Set<String>dependencias;

    public Paquete(String name){
        this.name = name;
        clases = new LinkedHashSet<String>();
        dependencias = new LinkedHashSet<String>();
    }

    public boolean define(String clase){
        return clases.contains(clase);
    }

    public boolean depende(String clase){
        return dependencias.contains(clase);
    }

    public ArrayList<String> getDependencias(){
        ArrayList<String> salida= new ArrayList<String>(dependencias);
        return salida;
    }

    public ArrayList<String> getClases(){
        ArrayList<String> salida= new ArrayList<String>(clases);
        return salida;
    }

    public void addClase(String clase){
        clases.add(clase);
    }

    public void addDependecia(String clase){
        dependencias.add(clase);
    }

    public String getName(){return name;}

    public void show(){
        System.out.println("Paquete: "+name);
        System.out.println("\tClases: ");
        for(String s : clases)
            System.out.println("\t\t"+s);
        System.out.println("\tDependencias: ");
        for(String s : dependencias)
            System.out.println("\t\t"+s);
    }
}
