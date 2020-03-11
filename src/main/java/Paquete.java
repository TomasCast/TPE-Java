package main.java;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class Paquete {
    private String name;
    private Set<String> clases;
    private Set<String>dependencias;

    Paquete(String name){
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
        return new ArrayList<String>(dependencias);
    }

    public ArrayList<String> getClases(){
        return new ArrayList<String>(clases);
    }

    public void addClase(String clase){
        clases.add(clase);
    }

    public void addDependecia(String clase){
        dependencias.add(clase);
    }

    public String getName(){
        return name;
    }

    public void show(){   //must be String toString()
        System.out.println("Paquete: "+name);
        System.out.println("\tClases: ");
        for(String s : clases)
            System.out.println("\t\t"+s);
        System.out.println("\tDependencias: ");
        for(String s : dependencias)
            System.out.println("\t\t"+s);
    }
}
