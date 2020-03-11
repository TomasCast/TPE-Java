package main.java;

import org.jgrapht.graph.DefaultEdge;

public class Arco extends DefaultEdge {
    public Object getOrigen(){
        return super.getSource();
    }

    public Object getDestino(){
        return super.getTarget();
    }
}
