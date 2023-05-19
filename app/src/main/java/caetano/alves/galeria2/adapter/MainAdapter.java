package caetano.alves.galeria2.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import caetano.alves.galeria2.R;
import caetano.alves.galeria2.activity.MainActivity;
import caetano.alves.galeria2.model.Utils;

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
        LayoutInflater inflater = LayoutInflater.from(mainActivity); // Criando um inflater pra ler o XML da pagina inicial e criar os elementos de interface
        View v = inflater.inflate(R.layout.list_item,parent,false); // Criando os elementos de interface
        return new MyViewHolder(v); // Retornando o holder com os elementos da view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // ImageView que vai receber a foto
        View v = holder.itemView;
        ImageView imPhoto = v.findViewById(R.id.imItem);

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
        return photos.size();
    }
}
