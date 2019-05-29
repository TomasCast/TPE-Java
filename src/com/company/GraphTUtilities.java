package com.company;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class GraphTUtilities extends DefaultEdge{
   // public Graph\\

    public static Graph<Integer, Arco> buildGraph(Diccionario dic){
        Graph<Integer,Arco> graph = new SimpleDirectedGraph<>(Arco.class);
        for (int i = 0; i < dic.size() ; i++) {
            if(!graph.containsVertex(dic.getNumero(dic.getPaquete(i).getName())))
                graph.addVertex(dic.getNumero(dic.getPaquete(i).getName()));
        }
        //adding the vertex...
        for (int i = 0; i < dic.size() ; i++) {

            ArrayList<String> dependencias = dic.getPaquete(i).getDependencias();
            for (int j=0; j<dependencias.size(); j++) {
                Paquete p_dep = dic.buscarPaquete(dependencias.get(j));
                if(p_dep != null && !Integer.valueOf(dic.getNumero(p_dep.getName())).equals(dic.getNumero(dic.getPaquete(i).getName()))
                        && !graph.containsEdge(dic.getNumero(dic.getPaquete(i).getName()), dic.getNumero(p_dep.getName()))) {

                    graph.addEdge(dic.getNumero(dic.getPaquete(i).getName()), dic.getNumero(p_dep.getName()));
                }
            }
        }
        //adding the edges... yeah!
        return graph;
    }

    public static void DFS_Ciclos_void(Graph<Integer,Arco> g, PrintWriter printer){
        ArrayList<Integer> vertices = new ArrayList<>(g.vertexSet());

        for (int i=0; i<vertices.size(); i++) {
            LinkedHashSet<Integer> visitados= new LinkedHashSet<>();
            DFS_void(g, vertices.get(i), visitados, vertices.get(i),printer);
            //System.out.println("removi el:"+vertices.get(i));
            g.removeVertex(vertices.get(i));
            //vertices.remove(i);
        }
    }



    public static ArrayList<ArrayList<Integer>> DFS_Ciclos(Graph<Integer,Arco> g){
        ArrayList<ArrayList<Integer>> out = new ArrayList<>();
        ArrayList<Integer> vertices = new ArrayList<>(g.vertexSet());

        for (int i=0; i<vertices.size(); i++) {
            LinkedHashSet<Integer> visitados= new LinkedHashSet<>();
            DFS(g, out, vertices.get(i), visitados, vertices.get(i));
            //System.out.println("removi el:"+vertices.get(i));
            g.removeVertex(vertices.get(i));
            //vertices.remove(i);
        }
        return out;
    }

    private static void DFS_void(Graph<Integer,Arco> g, Integer inicial, Set<Integer> visitados, Integer actual, PrintWriter printer){
        if(visitados.contains(actual) && actual.equals(inicial)){
            printer.println(visitados.toString());
        }
        else if(!visitados.contains(actual)){
            visitados.add(actual);
            Set<Arco> arcos= g.outgoingEdgesOf(actual);
            for (Arco hijo: arcos) {
                DFS_void(g, inicial, visitados, g.getEdgeTarget(hijo),printer);
            }
            visitados.remove(actual);
        }
    }



    private static void DFS(Graph<Integer, Arco> g, ArrayList<ArrayList<Integer>> cycles, Integer inicial, Set<Integer> visitados, Integer actual){
        if(visitados.contains(actual) && actual.equals(inicial)){
            cycles.add(new ArrayList<>(visitados)); // guardar el ciclo
            System.out.println(cycles.get(cycles.size()-1).toString());
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

//HOLA CAST
}
