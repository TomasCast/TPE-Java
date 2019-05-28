package com.company;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class GraphTUtilities extends DefaultEdge{
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
        //adding the vertex...
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
        //adding the edges... yeah!
        return graph;
    }

    public static ArrayList<ArrayList<String>> DFS_Ciclos(Graph<String,Arco> g){
        ArrayList<ArrayList<String>> out = new ArrayList<ArrayList<String>>();

        for (String vertice: g.vertexSet()) {
            LinkedHashSet<String> visitados= new LinkedHashSet<>();
            String actual= new String();
            DFS(g, out, vertice, visitados, actual);
        }

        return out;
    }

    private static void DFS(Graph<String, Arco> g, ArrayList<ArrayList<String>> cycles, String inicial, Set<String> visitados, String actual){
        if(visitados.contains(actual) && actual.equals(inicial)){
            // guardar el ciclo.
        }
        else if(!visitados.contains(actual)){
            visitados.add(actual);
            for (Arco hijo: g.edgesOf(actual)) {
                hijo.getDestino();
            }
        }
    }

//HOLA CAST
}
