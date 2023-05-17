package caetano.alves.galeria2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Lista que vai armazenar todos os caminhos para as fotos
    List<String> photos = new ArrayList<>();

    // Classe que vai construir e preencher a RecyclerView
    MainAdapter mainAdapter;

    static int RESULT_TAKE_PICTURE = 1;
    String currentPhotoPath;
    static int RESULT_REQUEST_PERMISSION = 2;

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

    public void startPhotoActivity(String photoPath) {
        Intent i = new Intent(MainActivity.this, PhotoActivity.class); // Intenção de mudar de tela
        i.putExtra("photo_path", photoPath); // Adicionando a foto q deve ser aberta na próxima tela no dicionário da intent
        startActivity(i); // Começar a activity
    }

    private void dispatchTakePictureIntent() {
        // Declarando variável que receberá o arquivo de imagem vazio
        File f = null;

        // Tentando criar o arquivo de imagem
        try {
            // Cria o arquivo vazio de imagem
            f = createImageFile();
        } catch(IOException e) { // Captura o erro se não for possível criar o arquivo
            // Dispara um texto de erro pro usuário
            Toast.makeText(MainActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Caminho do arquivo vazio da imagem
        currentPhotoPath = f.getAbsolutePath();

        // Conferindo se o arquivo foi criado
        if(f != null) {
            // Capturando o caminho para o arquivo
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "caetano.alves.galeria2.fileprovider", f);

            // Intenção de abrir a camera pra tirar uma foto
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Passa o caminho do arquivo vazio para a camera armazenar a foto
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            // Começa a activity e espera pelo resultado
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Captura o horário do arquivo criado
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Define o nome do arquivo com o tempo de criação
        String imageFileName = "JPEG_" + timeStamp;

        // Captura o diretorio que armazenará o arquivo
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Cria o arquivo temporário de imagem
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Retorna o arquivo
        return f;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         // Conferindo se a foto foi tirada
         if(requestCode == RESULT_TAKE_PICTURE) {
             if(resultCode == Activity.RESULT_OK) {
                 // Adicionando o caminho da foto na lista de fotos
                 photos.add(currentPhotoPath);

                 // Avisa o mainAdapter sobre a inclusão para atualizar o recyclerView
                 mainAdapter.notifyItemInserted(photos.size()-1);
             }
             else {
                 // Se a foto não for tirada, o arquivo é deletado
                 File f = new File(currentPhotoPath);
                 f.delete();
             }
         }
    }

    private void checkForPermissions(List<String> permissions) {
        // Lista de persmissões que o aplicativo não tem
        List<String> permissionsNotGranted = new ArrayList<>();

        // Para cada permisão que o app precisa
        for(String permission : permissions) {
            // Checa se tem a permissão
            if(!hasPermission(permission)) {
                // Adiciona na lista de que não tem permissão
                permissionsNotGranted.add(permission);
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
            }
        }
    }

    private boolean


}