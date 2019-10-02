package br.edu.ifrn.sc.expotec.autenticador.telas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.R;
import br.edu.ifrn.sc.expotec.autenticador.adaptador.ActivityAdapter;
import br.edu.ifrn.sc.expotec.autenticador.dominio.Activity;
import br.edu.ifrn.sc.expotec.autenticador.dominio.Event;
import br.edu.ifrn.sc.expotec.autenticador.dominio.Inscription;
import br.edu.ifrn.sc.expotec.autenticador.dominio.User;
import br.edu.ifrn.sc.expotec.autenticador.ws.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Event eventoAtual;
    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private ProgressBar spinner;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.progressBar);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        if (getIntent().hasExtra("eventoSelecionado")) {
            eventoAtual = (Event) getIntent().getSerializableExtra("eventoSelecionado");
            checkNetworkStatus();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             //           .setAction("Action", null).show();

                capturarCodigoQR();
            }
        });
    }

    private void capturarCodigoQR() {

        if (activityAdapter != null) {

            Activity activity = activityAdapter.getSelectedActivity();

            if (activity != null) {

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Registrando participação na Atividade");
                integrator.setCameraId(0);
                integrator.initiateScan();
            } else {
                Toast.makeText(MainActivity.this, "Por favor, selecione alguma atividade disponível!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            String resultado = result.getContents();

            if (resultado != null && !resultado.isEmpty()) {

                Activity activity = activityAdapter.getSelectedActivity();

                long userUuid = Long.parseLong(resultado);
                long activityUuid = activity.getUuid();
                this.registrarParticipacao(activityUuid, userUuid);

                Toast.makeText(MainActivity.this, "Autenticando usuário: " + userUuid + " para o activity: " + activity.getTitle(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void registrarParticipacao(long activitiesUuid, long userUuid) {


        Call<Boolean> call = new RetrofitConfig().getAttendantsService().registrarPresenca(activitiesUuid, userUuid);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                boolean resposta = response.body();

                if (resposta)
                    Toast.makeText(MainActivity.this, "Participação Registrada com sucesso!", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(MainActivity.this, "Erro ao tentar registrar participação no evento!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha ao tentar se conectar com o servidor! Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void exibirListaAtividades() {

        Call<List<Activity>> call = new RetrofitConfig().getActivitiesInterface().listActivities(eventoAtual.getId());
        spinner.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Activity>>() {
            @Override
            public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {

                spinner.setVisibility(View.GONE);
                List<Activity> activityList = response.body();
                eventoAtual.setListActivities(activityList);

                activityAdapter = new ActivityAdapter(MainActivity.this, eventoAtual.getListActivities());
                recyclerView.setAdapter(activityAdapter);
            }

            @Override
            public void onFailure(Call<List<Activity>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            GridLayoutManager manager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(manager);
            exibirListaAtividades();
        }
        else {
            Snackbar.make(coordinatorLayout, "Sem conexão com Internet!", Snackbar.LENGTH_LONG).show();
        }
    }
}
