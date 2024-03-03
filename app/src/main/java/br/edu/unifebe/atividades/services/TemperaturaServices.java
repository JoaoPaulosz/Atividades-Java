package br.edu.unifebe.atividades.services;

public class TemperaturaServices {
    public static float calculaTemperatura(float tempF){
        float celsius = (tempF - 32) * 5 /9;
        return celsius;
    }
}
