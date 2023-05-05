package caetano.alves.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbMain); // Capturando a toolbar criada
        setSupportActionBar(toolbar); // Setando a toolbar na página main
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater(); // Cria um inflador de menu
        inflater.inflate(R.menu.main_activity_tb, menu); // Passa a tela e o menu que o inflador de menu colocará as opções de menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // Executa quando o item da toolbar for selecionado
        switch (item.getItemId()) { // Pega o id do item clicado
            case R.id.opCamera: // Caso seja o item de camera
                dispatchTakePictureIntent(); // Executa o codigo que dispara a camera
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}