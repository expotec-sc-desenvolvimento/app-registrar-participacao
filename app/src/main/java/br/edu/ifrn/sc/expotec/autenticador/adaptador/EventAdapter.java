package br.edu.ifrn.sc.expotec.autenticador.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.R;
import br.edu.ifrn.sc.expotec.autenticador.dominio.Event;
import br.edu.ifrn.sc.expotec.autenticador.telas.MainActivity;

public class EventAdapter extends RecyclerView.Adapter<ActivityHolder> {

    private List<Event> list;
    private Context context;

    public EventAdapter(Context context, List<Event> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ActivityHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, final int position) {

        holder.getTitulo().setText(list.get(position).getTitle());
        holder.getTitulo().setVisibility(View.GONE);
        holder.getImagem().setImageResource(list.get(position).getLogo());

        holder.getImagem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.putExtra("eventoSelecionado", list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
