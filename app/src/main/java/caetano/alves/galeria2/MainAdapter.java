package caetano.alves.galeria2;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Classe para construir e preencher a RecyclerView
public class MainAdapter extends RecyclerView.Adapter{
    // Tela main
    MainActivity mainActivity;

    // Lista com os caminhos para as imagens
    List<String> photos;

    // Construtor da classe
    public MainAdapter (MainActivity mainActivity, List<String> photos){
        this.mainActivity = mainActivity;
        this.photos = photos;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // ImageView que vai receber a foto
        ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);

        // Tamanho da largura do item
        int w = (int) mainActivity.getResources().getDimension(R.dimen.itemWidth);

        // Tamanho da altura do item
        int h = (int) mainActivity.getResources().getDimension(R.dimen.itemHeight);

        // Carrega a imagem pra um bitmap e redimensiona os tamanhos
        Bitmap bitmap = Utils.getBitmap(photos.get(position), w, h);

        // Bitmap colocado na ImageView
        imPhoto.setImageBitmap(bitmap);

        // No clique da imagem, abre a photoActivity para exibir em tamanho ampliado
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startPhotoActivity(photos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
