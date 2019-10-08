package br.edu.ifrn.sc.expotec.autenticador.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.R;
import br.edu.ifrn.sc.expotec.autenticador.adaptador.ActivityAdapter;
import br.edu.ifrn.sc.expotec.autenticador.adaptador.EventAdapter;
import br.edu.ifrn.sc.expotec.autenticador.dominio.Event;
import br.edu.ifrn.sc.expotec.autenticador.ws.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private ProgressBar spinner;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.progressBar);
        constraintLayout = findViewById(R.id.constraintLayout);

        checkNetworkStatus();
    }

    private void showEvents() {

        Call<List<Event>> call =  new RetrofitConfig().getEventService().listEvents();
        spinner.setVisibility(View.VISIBLE);


        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                spinner.setVisibility(View.GONE);
                List<Event> eventList = response.body();

                EventAdapter adapter = new EventAdapter(EventsActivity.this, eventList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            GridLayoutManager manager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(manager);
            showEvents();
        }
        else {
            Snackbar.make(constraintLayout, "Sem conexão com Internet!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void finish() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
}