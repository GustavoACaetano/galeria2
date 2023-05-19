package caetano.alves.galeria2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.File;

import caetano.alves.galeria2.R;
import caetano.alves.galeria2.model.Utils;

public class PhotoActivity extends AppCompatActivity {

    String photoPath; // Caminho para foto que deverá ser aberta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto); // Capturando a toolbar criada
        setSupportActionBar(toolbar); // Setando a toolbar na página photoActivity

        ActionBar actionbar = getSupportActionBar(); // Capturando a actionbar que acabamos de definir acima
        actionbar.setDisplayHomeAsUpEnabled(true); // Habilitando o botão de voltar

        Intent i = getIntent(); // Capturando a intent
        photoPath = i.getStringExtra("photo_path"); // Caminho da foto que deve ser aberta

        Bitmap bitmap = Utils.getBitmap(photoPath); // Transformando a foto para bitmap
        ImageView imPhoto = findViewById(R.id.imPhoto); // Capturando o imageView que deve mostrar a imagem
        imPhoto.setImageBitmap(bitmap); // Colocando o bitmap no imageView
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

    void sharePhoto() {
        // Captura o URI da foto
        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this, "caetano.alves.galeria2.fileprovider", new File(photoPath));

        // Cria uma intenção de enviar
        Intent i = new Intent(Intent.ACTION_SEND);

        // Adiciona o URI na intent
        i.putExtra(Intent.EXTRA_STREAM, photoUri);

        // Seleciona o tipo de arquivo que será enviado
        i.setType("image/jpeg");

        // Executa a intenção
        startActivity(i);
    }

}