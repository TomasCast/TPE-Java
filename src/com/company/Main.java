package com.company;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;


import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;
import org.jgrapht.traverse.*;



public class Main {

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
    /*AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRANCA LA NOCHEEEEEEEEEEEEEEEEH**/
    public static ArrayList<String> processNode(Node n, String parentName, String attributeToGet){
        //Element e = (Element) n;
        NodeList lista = ((Element)n).getElementsByTagName(parentName);
        ArrayList<String> salida = new ArrayList<String>();
        for(int i=0; i<lista.getLength(); i++){
            Node node = lista.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) node;
                salida.add(e.getAttribute(attributeToGet).toString());
            }
        }

        //System.out.println(salida);
        return salida;
    }

    /**Levanta los paquetes de un archivo .odem junto con sus clases y dependencias.*/


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





    public static void prueba(String route){

        Document document = processFile(route);
        Element root = document.getDocumentElement(); // Extraigo el elemento raiz
        System.out.println(root.getNodeName());

        Node containerNameNode = ((Element) root.getElementsByTagName("context").item(0)).getElementsByTagName("container").item(0);
        // con esto llego al padre de los paquetes.
        System.out.println("node name: "+containerNameNode.getNodeName());

       // processNode(containerNameNode, "namespace", "name");

        NodeList listaPaquetes = ((Element) containerNameNode).getElementsByTagName("namespace"); //agarro todos los paq en una lista
        for(int i=0; i<listaPaquetes.getLength(); i++){
            Node nNode = listaPaquetes.item(i); // agarro cada nodo de la lista, imprimo su nombre (va a ser namespace)
            //System.out.println("Item actual: "+ nNode.getNodeName());
            if(nNode.getNodeType() == Node.ELEMENT_NODE){ // si es un elemento
                Element elemento = (Element) nNode;
                System.out.println("nombre paq: "+elemento.getAttribute("name")); // veo su nombre.
                //Element dependencies = (Element) elemento.getElementsByTagName("dependencies").item(0);
                //System.out.println("\tcantidad de dependencias: "+dependencies.getAttribute("count"));
                NodeList clases = elemento.getElementsByTagName("type"); //agarro las clases
               // processNode(nNode, "type", "name");
                for(int j=0; j<clases.getLength(); j++){ // por cada clase, guardo su nombre
                    Node clase = clases.item(j);
                    //System.out.println("Clase actual: "+clase.getNodeName());
                    if(clase.getNodeType() == Node.ELEMENT_NODE){
                        Element elementoClase = (Element) clase;
                        System.out.println("\tNombre clase: "+elementoClase.getAttribute("name"));
                        NodeList depends = elementoClase.getElementsByTagName("depends-on");

                        for(int k=0; k<depends.getLength(); k++){
                            Node dependencia = depends.item(k);
                            if(dependencia.getNodeType() == Node.ELEMENT_NODE) {
                                Element elementoDependencia = (Element) dependencia;
                                System.out.println("\tDepende de:");
                                System.out.println("\t\t" + elementoDependencia.getAttribute("name"));
                            }
                        }
                    }
                }
            }
        }


        System.out.print("Root element: "+document.getDocumentElement().getNodeName()+"\n");
        System.out.println("--------------------------------------------");
        NodeList nodeTag =  root.getElementsByTagName("student"); // me da todos los  "student" en una list de nodos

        for(int i=0;i<nodeTag.getLength(); i++) { //para cada elemento de esa lista:
            Node nNode = nodeTag.item(i); //agarro el nodo i
            System.out.println("Item actual: "+ nNode.getNodeName()); // le pido el nombre actual (me da student porque son todos elementos student
            if(nNode.getNodeType() == Node.ELEMENT_NODE){ //le pregunto si ese nodo es un elemento (podria ser otro subarbol quizas?)
                Element eElement = (Element) nNode; //lo casteo a element, y le pido cada uno de los valores de sus atributos
                System.out.println("Student roll no: "+ eElement.getAttribute("rollno"));
                System.out.println("firstname: "+ eElement.getElementsByTagName("firstname").item(1).getTextContent()); // el indx 0 es por si ya varios "firstname"
                System.out.println("lastname: "+eElement.getElementsByTagName("lastname").item(0).getTextContent());
                System.out.println("nickname: "+eElement.getElementsByTagName("nickname").item(0).getTextContent());
                System.out.println("marks: "+eElement.getElementsByTagName("marks").item(0).getTextContent());
            }
            System.out.println("---------------------------------");
        }

    }

    public static void main(String[] args) {
	// write your code here


        //prueba("C:/Users/tomi/IdeaProjects/TPE Java/apache-camel-1.6.0.odem");
       /* Graph<Integer,DefaultEdge> a = new SimpleDirectedGraph<>(DefaultEdge.class);
        a.getAllEdges(0,0);*/
        Diccionario lista = getPaquetes("apache-camel-1.6.0.odem");
        for(int i=0; i<lista.size(); i++)
            lista.getPaquete(i).show();
       // PaqueteCompuesto paqs = new PaqueteCompuesto(lista); // Creo que la clase paquete compuesto es al pedo porque tambien necesito acceder
        // a las clases de cada paqute



        Graph<Integer,Arco> grafo = GraphTUtilities.buildGraph(lista);
        System.out.println("El Grafo generado es: \n"+grafo+
                "\n cantidad nodos:"+grafo.vertexSet().size());

        Set<Arco> edges= grafo.edgeSet();
        for (DefaultEdge e: edges) {
            System.out.println(e);
        }
        System.out.println(edges.size());


        //ArrayList<ArrayList<Integer>> ciclos = GraphTUtilities.DFS_Ciclos(grafo);


        //for(int i=0; i<ciclos.size(); i++)
          //  System.out.println(ciclos.get(i));

        File salida= new File("ciclos.txt");
        PrintWriter printer = null;
        try {
            printer = new PrintWriter(salida);
        }catch (Exception e){
            e.printStackTrace();
        }

        GraphTUtilities.DFS_Ciclos_void(grafo,printer);

        /*
        Graph<String, Arco> g = new SimpleDirectedGraph<>(Arco.class);
        g.addVertex("1");
        g.addVertex("2");
        g.addVertex("3");
        g.addVertex("4");
        g.addVertex("5");
        g.addVertex("6");
        g.addVertex("7");
        g.addVertex("8");
        g.addVertex("9");

        g.addEdge("8","9");
        g.addEdge("9","8");
        g.addEdge("2","9");
        g.addEdge("1","8");
        g.addEdge("1","2");
        g.addEdge("1","5");
        g.addEdge("2","7");
        g.addEdge("2","3");
        g.addEdge("3","2");
        g.addEdge("3","1");
        g.addEdge("3","4");
        g.addEdge("3","6");
        g.addEdge("6","4");
        g.addEdge("4","5");
        g.addEdge("5","2");

        System.out.println(GraphTUtilities.DFS_Ciclos(g));
        */

    }
}
