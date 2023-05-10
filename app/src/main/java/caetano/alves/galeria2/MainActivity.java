package caetano.alves.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Lista que vai armazenar todos os caminhos para as fotos
    List<String> photos = new ArrayList<>();

    // Classe que vai construir e preencher a RecyclerView
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbMain); // Capturando a toolbar criada
        setSupportActionBar(toolbar); // Setando a toolbar na página main

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Acessa o diretório das fotos
        File[] files = dir.listFiles(); // Lê a lista de fotos salvas
        // Adiciona na lista de fotos
        for(int i = 0; i < files.length; i++) {
            photos.add(files[i].getAbsolutePath());
        }

        // Cria o adapter
        mainAdapter = new MainAdapter(MainActivity.this, photos);

        // Captura o recyclerView
        RecyclerView rvGallery = findViewById(R.id.rvGallery);

        // Seta o adapter no recyclerView
        rvGallery.setAdapter(mainAdapter);

        // Tamanho da tela
        float w = getResources().getDimension(R.dimen.itemWidth);

        // Numero de colunas que cabem na tela
        int numberOfColumns = Utils.calculateNoOfColumns(MainActivity.this, w);

        // Configurando o recycleView conforme o numero de colunas calculado
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
        rvGallery.setLayoutManager(gridLayoutManager);
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