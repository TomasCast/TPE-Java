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

    public static Graph<String, Arco> buildGraph(ArrayList<Paquete> paqs){
        Graph<String,Arco> graph = new SimpleDirectedGraph<>(Arco.class);
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
        ArrayList<String> vertices = new ArrayList<>(g.vertexSet());

        for (int i=0; i<vertices.size(); i++) {
            LinkedHashSet<String> visitados= new LinkedHashSet<>();
            DFS(g, out, vertices.get(i), visitados, vertices.get(i));
            //System.out.println("removi el:"+vertices.get(i));
            g.removeVertex(vertices.get(i));
            vertices.remove(i);
        }



        /*LinkedHashSet<String> visitados= new LinkedHashSet<>();
        DFS(g, out, vertices.get(0), visitados, vertices.get(0));
        System.out.println("removi el:"+vertices.get(0));
*/

        return out;
    }

    private static void DFS(Graph<String, Arco> g, ArrayList<ArrayList<String>> cycles, String inicial, Set<String> visitados, String actual){
        if(visitados.contains(actual) && actual.equals(inicial)){
            cycles.add(new ArrayList<>(visitados)); // guardar el ciclo
        }
        else if(!visitados.contains(actual)){
            visitados.add(actual);
            Set<Arco> arcos= g.outgoingEdgesOf(actual);
            for (Arco hijo: arcos) {
                DFS(g, cycles, inicial, visitados, g.getEdgeTarget(hijo));
            }
            visitados.remove(actual);
        }
        //visitados.remove(actual); // mal
    }


}
