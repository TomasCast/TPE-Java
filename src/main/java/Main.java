package main.java;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.List;


import org.jgrapht.*;
import org.jgrapht.graph.*;

public class Main {

    public static void mostrarGrafo(Graph<Integer, Arco> g, Diccionario dic){
        Set<Integer> vertexs = g.vertexSet();
        System.out.println("cantidad de vertices: "+vertexs.size());
        System.out.println("cantidad de arcos: "+g.edgeSet().size());
        for (Integer vertice: vertexs) {
            System.out.println("nodo: "+dic.getNombre(vertice));
            Set<Arco> salientes = g.outgoingEdgesOf(vertice);
            System.out.println("\tadyacentes: ("+salientes.size()+")");
            for (Arco a: salientes) {
                System.out.println("\t\t"+dic.getNombre((Integer)a.getDestino()));
            }
        }
    }

    public static Document processFile(String route){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dBuilder = factory.newDocumentBuilder(); //creo un document  builder
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }

        File inputFile = new File(route);
        Document document = null;
        try {
            document = dBuilder.parse(inputFile); // creo el documento a partir del archivo
        }catch (Exception e){
            e.printStackTrace();
        }
        return document;
    }

    /**Levanta los paquetes de un archivo .odem junto con sus clases y dependencias, y los coloca en un Diccionario.*/
    public static Diccionario getPaquetes(String route){
        Diccionario salida = new Diccionario();
        Document document = processFile(route);
        Element root = document.getDocumentElement();
        Node container= ((Element) root.getElementsByTagName("context").item(0)).getElementsByTagName("container").item(0);

        NodeList paquetes= ((Element)container).getElementsByTagName("namespace");
        for(int i=0; i<paquetes.getLength(); i++){
            Node nodo_paquete = paquetes.item(i);
            if(nodo_paquete.getNodeType() == Node.ELEMENT_NODE){
                Element paquete = (Element) nodo_paquete;
                Paquete p = new Paquete(paquete.getAttribute("name"));
                NodeList clases = paquete.getElementsByTagName("type");

                for(int j=0; j<clases.getLength(); j++){
                    Node nodo_clase = clases.item(j);
                    if(nodo_clase.getNodeType() == Node.ELEMENT_NODE){
                        Element clase = (Element) nodo_clase;
                        p.addClase(clase.getAttribute("name"));
                        NodeList dependencias = clase.getElementsByTagName("depends-on");

                        for(int k=0; k<dependencias.getLength(); k++){
                            Node nodo_dependencia = dependencias.item(k);
                            if(nodo_dependencia.getNodeType() == Node.ELEMENT_NODE && !((Element) nodo_dependencia).getAttribute("name").startsWith("java")){
                                Element dependencia = (Element) nodo_dependencia;
                                p.addDependecia(dependencia.getAttribute("name"));
                            }
                        }
                    }
                }
                salida.addPaq(p);
            }
        }
        return salida;
    }


    public static void main(String[] args) {
        Diccionario lista = getPaquetes("apache-camel-1.6.0.odem");
        for(int i=0; i<lista.size(); i++)
            lista.getPaquete(i).show();

        Graph<Integer,Arco> grafo = GraphTUtilities.buildGraph(lista);
        //mostrarGrafo(grafo, lista);

        Hashtable<Integer, Integer> tabla_ciclos = new Hashtable<>();
        File salida= new File("ciclos.txt");
        PrintWriter printer = null;
        //BufferedWriter printer_buff = null;
        try {
            printer = new PrintWriter(salida);
        }catch (Exception e){
            e.printStackTrace();
        }

        GraphTUtilities.MAX_CICLO = 1000;

        long inicio = System.currentTimeMillis();
        //GraphTUtilities.DFS_Ciclos_void(grafo,printer,lista, tabla_ciclos);

       // System.out.println(
       //         GraphTUtilities.ChequearCiclo(grafo,lista.getNumero("org.apache.camel.model"), lista.getNumero("org.apache.camel.model.language"))
        //);
        JohnsonSimpleCycles<Integer, Arco> j = new JohnsonSimpleCycles<>(grafo);
       // System.out.println(j.findSimpleCycles().size());
        List<List<Integer>> l = j.findSimpleCycles();
        int elim = 0;
        for(int i=0; i<l.size(); i++) {
            if (l.get(i).size() == 2)
                elim++;
        }
        System.out.println(elim);
        long fin = System.currentTimeMillis();
        System.out.println("Se tardo: "+(fin - inicio)/1000+ " seg");

        printer.close();
        int sum =0;
        for(int i=2;i<tabla_ciclos.size()+3; i++){
            System.out.println("tamaño ciclo: " + i + " cantidad de ciclos: " + tabla_ciclos.get(i));
            if(tabla_ciclos.get(i) != null)
                sum += tabla_ciclos.get(i);
        }
        System.out.println("total ciclos: "+sum);


 /*       Graph<Integer, Arco> g = new SimpleDirectedGraph<>(Arco.class);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addVertex(7);
        g.addVertex(8);
        g.addVertex(9);

        g.addEdge(8,9);
        g.addEdge(9,8);
        g.addEdge(2,9);
        g.addEdge(1,8);
        g.addEdge(1,2);
        g.addEdge(1,5);
        g.addEdge(2,7);
        g.addEdge(2,3);
        g.addEdge(3,2);
        g.addEdge(3,1);
        g.addEdge(3,4);
        g.addEdge(3,6);
        g.addEdge(6,4);
        g.addEdge(4,5);
        g.addEdge(5,2);

        //System.out.println(GraphTUtilities.DFS_Ciclos(g));

        System.out.println(GraphTUtilities.ChequearCiclo(g,7,2));
*/
    }
}