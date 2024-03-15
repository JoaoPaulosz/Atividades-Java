package br.edu.unifebe.atividades.Connection;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class BaseDAO extends SQLiteOpenHelper {
    private static final String name = "banco.db";
    private static final int version = 1;
    public BaseDAO(Context context){super(context,name,null,version);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE Contato (nome VARCHAR(100),sobrenome VARCHAR(50),telefone VARCHAR(20),rg VARCHAR(15),cpf VARCHAR (14))";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
 