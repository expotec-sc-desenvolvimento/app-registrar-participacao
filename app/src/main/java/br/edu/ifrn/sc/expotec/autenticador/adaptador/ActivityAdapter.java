package br.edu.ifrn.sc.expotec.autenticador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.R;
import br.edu.ifrn.sc.expotec.autenticador.dominio.Activity;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

    private List<Activity> listaActivities;
    private Context context;
    private int posicaoSelecionada;

    public ActivityAdapter(Context context, List<Activity> listaActivities) {
        this.context = context;
        this.listaActivities = listaActivities;
        this.posicaoSelecionada = -1;
    }


    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ActivityHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ActivityHolder holder, final int position) {

        holder.getTitulo().setText(listaActivities.get(position).getTitle());
        holder.getImagem().setImageResource(listaActivities.get(position).getImagemExibicao());

        holder.getImagem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity activity = listaActivities.get(position);

                if (posicaoSelecionada == -1 && activity.getStatus() == 0) {

                    posicaoSelecionada = position;
                    activity.setStatus(2);
                } else if (posicaoSelecionada != -1 && activity.getStatus() == 2) {
                    posicaoSelecionada = -1;
                    activity.setStatus(0);
                }
                notifyDataSetChanged();
            }
        });

        holder.getImagem().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Activity activity = listaActivities.get(position);

                if (activity.getStatus() == 0) {

                    activity.setStatus(1);
                    notifyDataSetChanged();

                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaActivities.size();
    }

    public Activity getSelectedActivity() {

        if (posicaoSelecionada != -1) {

            return listaActivities.get(posicaoSelecionada);
        }
        return null;

    }
}
