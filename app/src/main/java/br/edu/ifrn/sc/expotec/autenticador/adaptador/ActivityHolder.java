package br.edu.ifrn.sc.expotec.autenticador.adaptador;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.expotec.autenticador.R;

public class ActivityHolder extends RecyclerView.ViewHolder {

    private TextView titulo;
    private ImageView imagem;

    public ActivityHolder(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.txtTitulo);
        imagem = itemView.findViewById(R.id.imgFoto);
    }

    public TextView getTitulo() {
        return titulo;
    }



    public ImageView getImagem() {
        return imagem;
    }


}
