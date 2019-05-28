package com.company;

public interface ElementoPaquete {
    boolean define(String clase);
    boolean depende(String clase);
    void show(); // deberia ser tostring y retornar string para qe sea mas lindo
}
