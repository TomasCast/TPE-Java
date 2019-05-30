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
    public static int MAX_CICLO;

    private static long i= 1;

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

    public static void DFS_Ciclos_void(Graph<Integer,Arco> g, PrintWriter printer, Diccionario dic){
        ArrayList<Integer> vertices = new ArrayList<>(g.vertexSet());

        for (int i=0; i<vertices.size(); i++) {
            LinkedHashSet<Integer> visitados= new LinkedHashSet<>();
            DFS_void(g, vertices.get(i), visitados, vertices.get(i),printer, dic);
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

    private static void imprimirCiclo(ArrayList<String> ciclos, PrintWriter printer){
        for (String s: ciclos) {
            printer.print(s+" ; ");
        }
        printer.println();
    }
    /**TODO traducir los ciclos de integer a String !!!*/
    public static boolean ChequearCiclo(Graph<Integer, Arco> g, Integer v1, Integer v2){
        LinkedHashSet<Integer> visitados = new LinkedHashSet<>();
        return DFS_ChequearCiclo(g,v1,v1,v2,visitados);
    }

    public static boolean DFS_ChequearCiclo(Graph<Integer, Arco> g, Integer inicial, Integer actual, Integer vertice2, Set<Integer> visitados){
        if(visitados.contains(actual) && actual.equals(inicial)) {
            if (visitados.contains(vertice2))
                return true;
        } else {
            if(!visitados.contains(actual)){
                visitados.add(actual);
                Set<Arco> arcos= g.outgoingEdgesOf(actual);
                for(Arco hijos : arcos) {
                    if (DFS_ChequearCiclo(g, inicial, g.getEdgeTarget(hijos), vertice2, visitados))
                        return true;
                }
                visitados.remove(actual);
                return false;
            }
        }
        return false;
    }

    private static void DFS_void(Graph<Integer,Arco> g, Integer inicial, Set<Integer> visitados, Integer actual, PrintWriter printer,Diccionario dic){
        if(!(visitados.size() > MAX_CICLO))
            if(visitados.contains(actual) && actual.equals(inicial) && visitados.size() > 3){
                //printer.println(dic.traducirIntAString(visitados).toString());
                imprimirCiclo(dic.traducirIntAString(visitados), printer);
                //System.out.println(visitados.toString() + i++);
            }
            else if(!visitados.contains(actual)){
                visitados.add(actual);
                Set<Arco> arcos= g.outgoingEdgesOf(actual);
                for (Arco hijo: arcos) {
                    DFS_void(g, inicial, visitados, g.getEdgeTarget(hijo),printer,dic);
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
}
