package main.java;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class GraphBuilder {
    Document document = null;

    public GraphBuilder(String route){
        this.readFile(route);
    }
    /*Lee el archivo .odem que esta en la ruta especificada**/
    public void readFile(String route){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {

            dBuilder = factory.newDocumentBuilder(); //creo un document  builder
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }

        File inputFile = new File(route);

        try {
            document = dBuilder.parse(inputFile); // creo el documento a partir del archivo
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void buildGraph(){
        Element root = document.getDocumentElement(); // extrae el elemento raiz del documento.
        NodeList nList = ((Element) root.getElementsByTagName("context name").item(0)).getElementsByTagName(" ");
     }
}
