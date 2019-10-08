package br.edu.ifrn.sc.expotec.autenticador.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import br.edu.ifrn.sc.expotec.autenticador.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        if (sharedPreferences.contains("ja_abriu_app")) {
            startActivity(new Intent(getBaseContext(), EventsActivity.class));
            finish();
        }
        else {
            adicionarJaAbriu(sharedPreferences);
            showSplashScreen();
        }
    }

    private void adicionarJaAbriu(SharedPreferences sharedPreferences) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ja_abriu_app", true);
        editor.commit();
    }

    private void showSplashScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), EventsActivity.class));
                finish();
            }
        }, 5000);
    }
}
