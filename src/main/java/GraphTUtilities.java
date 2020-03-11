package main.java;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import java.io.PrintWriter;
import java.util.*;

public class GraphTUtilities{
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

    /**Hace Analisis sobre g, buscando todos los ciclos de tamaño mayor o igual a 3 y menores que MAX_CICLO.
     * Tambien registra la cantidad total de ciclos que cumplen esa regla, discriminando por tamaños
     * @param g Grafo a analizar.
     * @param printer para imprimir al archivo los ciclos.
     * @param dic para traducir los numeros de los vertices a los nombres String reales.
     * @param tabla_ciclos tabla que contiene el total de los ciclos discriminando por tamaños
     * */
    public static void DFS_Ciclos_void(Graph<Integer,Arco> g, PrintWriter printer, Diccionario dic, Hashtable<Integer, Integer> tabla_ciclos){
        ArrayList<Integer> vertices = new ArrayList<>(g.vertexSet());

        for (int i=0; i<vertices.size(); i++) {
            LinkedHashSet<Integer> visitados= new LinkedHashSet<>();
            DFS_void(g, vertices.get(i), visitados, vertices.get(i),printer, dic, tabla_ciclos);
            //System.out.println("removi el:"+vertices.get(i));
            g.removeVertex(vertices.get(i));
            //vertices.remove(i);
        }
    }



    private static void imprimirCiclo(ArrayList<String> ciclos, PrintWriter printer){
        for (String s: ciclos) {
                printer.write(s + " ; ");
        }
            printer.println();
    }

    /**TODO traducir los ciclos de integer a String !!!
     * -> quizas esto podria venir traducido de afuera.*/
    public static boolean chequearCiclo(Graph<Integer, Arco> g, Integer v1, Integer v2){
        LinkedHashSet<Integer> visitados = new LinkedHashSet<>();
        return DFS_ChequearCiclo(g,v1,v1,v2,visitados);
    }




    private static boolean DFS_ChequearCiclo(Graph<Integer, Arco> g, Integer inicial, Integer actual, Integer vertice2, Set<Integer> visitados){
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


    private static void DFS_void(Graph<Integer,Arco> g, Integer inicial, Set<Integer> visitados, Integer actual, PrintWriter printer,
                                 Diccionario dic, Hashtable<Integer, Integer> tabla_ciclos){
       // if(!(visitados.size() > MAX_CICLO)){ //visitados.size()<=MAX_CICLO
            if(visitados.contains(actual) && actual.equals(inicial) && visitados.size() >= 3){
                imprimirCiclo(dic.traducirIntAString(visitados), printer);

                if(tabla_ciclos.containsKey(visitados.size()))
                    tabla_ciclos.put(visitados.size(), tabla_ciclos.get(visitados.size()) + 1);
                else
                    tabla_ciclos.put(visitados.size(), 1);
            }
            else if(!visitados.contains(actual)){
                visitados.add(actual);
                Set<Arco> arcos= g.outgoingEdgesOf(actual);
                if(visitados.size()+1 <= MAX_CICLO){
                    for (Arco hijo : arcos) {
                        DFS_void(g, inicial, visitados, g.getEdgeTarget(hijo), printer, dic, tabla_ciclos);
                    }
                }
                visitados.remove(actual);
            }
       // }
    }

}
