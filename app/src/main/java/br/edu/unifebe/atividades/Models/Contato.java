package br.edu.unifebe.atividades.Models;

import androidx.annotation.NonNull;

public class Contato {
    public String nome;
    public Contato(){}
    public Contato(String nome, String sobreNome, String telefone, String RG, String CPF) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefone = telefone;
        this.RG = RG;
        this.CPF = CPF;
    }

    public String sobreNome;
    public String telefone;
    public String RG;
    public String CPF;

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s Tel: %s Doc: %s",nome,sobreNome,telefone,RG);
    }
}
