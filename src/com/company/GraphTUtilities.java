package com.company;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.ArrayList;

public class GraphTUtilities{
   // public Graph\\

    private static Paquete buscarPaquete(String clase, ArrayList<Paquete> paquetes){
        for (Paquete p: paquetes) {
            if(p.define(clase))
                return p;
        }
        return null;
    }

    public static Graph<String, DefaultEdge> buildGraph(ArrayList<Paquete> paqs){
        Graph<String,DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        for (Paquete p: paqs) {
            if(!graph.containsVertex(p.getName()))
                graph.addVertex(p.getName());
        }
        //adding the vertex
        for (Paquete p : paqs) {

            ArrayList<String> dependencias = p.getDependencias();
            for (int j=0; j<dependencias.size(); j++) {
                Paquete p_dep = buscarPaquete(dependencias.get(j), paqs);
                if(p_dep != null && !p_dep.getName().equals(p.getName()) && !graph.containsEdge(p.getName(), p_dep.getName())) {
                    System.out.println(p_dep.getName() + "  " + p.getName()); // son del mismo nombre
                    graph.addEdge(p.getName(), p_dep.getName());
                }
            }
        }
        //adding the edges...
        return graph;
    }
}
