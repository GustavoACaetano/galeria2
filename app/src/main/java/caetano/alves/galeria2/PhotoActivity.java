package caetano.alves.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto); // Capturando a toolbar criada
        setSupportActionBar(toolbar); // Setando a toolbar na página photoActivity

        ActionBar actionbar = getSupportActionBar(); // Capturando a actionbar que acabamos de definir acima
        actionbar.setDisplayHomeAsUpEnabled(true); // Habilitando o botão de voltar
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater(); // Cria um inflador de menu
        inflater.inflate(R.menu.photo_activity_tb, menu); // Passa a tela e o menu que o inflador de menu colocará as opções de menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // Executa quando um item de menu for clicado
        switch (item.getItemId()) { // Seleciona o id do item
            case R.id.opShare: // Caso seja o item de compartilhar
                sharePhoto(); // Chama o método que compartilha a foto
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }
        }


}