package br.edu.unifebe.atividades.Connection.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.unifebe.atividades.Connection.BaseDAO;
import br.edu.unifebe.atividades.Models.Contato;

public class ContatoDAO {
    private BaseDAO conexao;
    private SQLiteDatabase banco;
    public ContatoDAO(Context context){
        this.conexao = new BaseDAO(context);
        banco = conexao.getWritableDatabase();
    }
    public boolean inserir(Contato contato) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome",contato.nome);
        contentValues.put("sobrenome",contato.sobreNome);
        contentValues.put("telefone",contato.telefone);
        contentValues.put("rg",contato.RG);
        contentValues.put("cpf",contato.CPF);
        try{
            banco.insert("contato",null,contentValues);
            return true;
        }catch (Exception ex){
            throw new Exception("Erro ao inserir");
        }
    }
    public ArrayList<Contato> buscarTodos(){
        ArrayList<Contato> contatos = new ArrayList<>();
        String[] colunas = new String[]{"nome","sobrenome","telefone","rg","cpf"};
        Cursor rs = banco.query("contato",colunas,null,null,null,null,null);
        while (rs.moveToNext()){
            Contato contato = new Contato();
            contato.nome = rs.getString(0);
            contato.sobreNome = rs.getString(1);
            contato.telefone = rs.getString(2);
            contato.RG = rs.getString(3);
            contato.CPF = rs.getString(4);
            contatos.add(contato);
        }
        if(contatos.size() <= 0)
            return null;
        return contatos;
    }
    public ArrayList<Contato> buscaContatoPorCPF(String cpf){

        ArrayList<Contato> contatos = new ArrayList<>();
        String[] colunas = new String[]{"nome","sobrenome","telefone","rg","cpf"};
        Cursor rs = banco.query("contato",colunas,"cpf = ?",new String[]{cpf},null,null,null);
        while (rs.moveToNext()){
            Contato contato = new Contato();
            contato.nome = rs.getString(0);
            contato.sobreNome = rs.getString(1);
            contato.telefone = rs.getString(2);
            contato.RG = rs.getString(3);
            contato.CPF = rs.getString(4);
            contatos.add(contato);
        }
        if(contatos.size() <= 0)
            return null;
        return contatos;
    }

}
