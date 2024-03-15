package br.edu.unifebe.atividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.edu.unifebe.atividades.Connection.DAO.ContatoDAO;
import br.edu.unifebe.atividades.Models.Contato;
import br.edu.unifebe.atividades.databinding.ActivityMainBinding;
import br.edu.unifebe.atividades.services.TemperaturaServices;
import br.edu.unifebe.atividades.services.TrianguloServices;

public class MainActivity extends AppCompatActivity {

    private String login = "admin";
    private String senha = "admin";
    public List<Contato> contatoList = new ArrayList<>();
    private ContatoDAO pessoaDAO;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        trocaFragment(new LoginFragment());
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home)
                trocaFragment(new HomeFragment());
            if(item.getItemId() == R.id.calcTemp)
                trocaFragment(new CalculoTemperaturaFragment());
            if(item.getItemId() == R.id.calctriangulo)
                trocaFragment(new CalculoTrianguloFragment());
            if(item.getItemId() == R.id.cadastro)
                trocaFragment(new ContatosFragment());
            return true;
        });
        pessoaDAO = new ContatoDAO(this);

    }


    public void trocaFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }
    public void validaLogin(View v){
        TextView loginInput = (TextView)findViewById(R.id.loginText);
        TextView senhaInput = (TextView)findViewById(R.id.senhaText);
        if(loginInput.getText().toString().equals(login) && senhaInput.getText().toString().equals(senha)){
            Snackbar snackbar = Snackbar.make(v,"Acesso Permitido",Snackbar.LENGTH_LONG);
            snackbar.show();
            BottomNavigationView btn = findViewById(R.id.bottomNavigationView2);
            btn.setVisibility(View.VISIBLE);
            trocaFragment(new HomeFragment());
        }else {
            Snackbar snackbar = Snackbar.make(v,"Usuário ou senha inválidos",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    public void calculaTriangulo(View v){
        int valorAInput = Integer.parseInt(((TextView) findViewById(R.id.valorA)).getText().toString());
        int valorBInput = Integer.parseInt(((TextView) findViewById(R.id.valorB)).getText().toString());
        int valorCInput = Integer.parseInt(((TextView) findViewById(R.id.valorC)).getText().toString());
        TextView resultadoText = (TextView) findViewById(R.id.resultado);
        resultadoText.setText("Resultado: ");
        if(TrianguloServices.seTriangulo(valorAInput,valorBInput,valorCInput)){
            resultadoText.setText(resultadoText.getText() + "É Triangulo");
        }else{
            resultadoText.setText(resultadoText.getText() + "Não É Triangulo");
        }
    }
    public void calculaCelsius(View v){
        TextView temperaturaResultado  = (TextView) findViewById(R.id.temperaturaC);
        temperaturaResultado.setText("Temperatura em Cº: ");
        try{
            float valorF = Float.parseFloat(((TextView)findViewById(R.id.temperaturaF)).getText().toString());
            float resultado = TemperaturaServices.calculaTemperatura(valorF);
            temperaturaResultado.setText(temperaturaResultado.getText() + Float.toString(resultado));
        }catch (Exception ex){
            temperaturaResultado.setText("Erro nos valores informados");
        }



    }

    public void showCadastroFragment(View v){
        trocaFragment(new CadastroFragment());
    }
    public void cadastrarContato(View v) throws Exception {
        String nome = ((TextView)findViewById(R.id.nomeText)).getText().toString();
        String sobreNome = ((TextView)findViewById(R.id.SobreNome)).getText().toString();
        String telefone = ((TextView)findViewById(R.id.Telefone)).getText().toString();
        String rg = ((TextView)findViewById(R.id.RG)).getText().toString();
        String cpf = ((TextView)findViewById(R.id.CPF)).getText().toString();
        Contato contato = new Contato(nome,sobreNome,telefone,rg,cpf);
        try {
            pessoaDAO.inserir(contato);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        trocaFragment(new ContatosFragment());
    }
    public void buscarContato(View view){
        TextView cpfBusca = findViewById(R.id.fieldBusca);
        ListView listView = findViewById(R.id.listaContatos);
        if(!cpfBusca.getText().toString().isEmpty()){
            contatoList = pessoaDAO.buscaContatoPorCPF(cpfBusca.getText().toString());
        }else{
            contatoList = pessoaDAO.buscarTodos();
        }
        if(contatoList == null){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("Erro").setMessage("Nenhum registro encontrado");
            alertDialog.show();
        }else {
            ArrayAdapter<Contato> listaAdaptada = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1,contatoList);
            listView.setAdapter(listaAdaptada);
        }

    }

}