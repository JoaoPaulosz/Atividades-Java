package br.edu.unifebe.atividades.services;

public class TrianguloServices {
    public static boolean seTriangulo(int a, int b, int c){
        if (a + b > c && a + c > b && c + b > a)
            return true;
        return false;
    }
}
