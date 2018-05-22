package pitagoras.prointer.mobile.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;
import java.util.List;

import pitagoras.prointer.mobile.AppLoader;
import pitagoras.prointer.mobile.R;
import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.domain.models.Usuario;
import pitagoras.prointer.mobile.web.api.AlunoAPI;
import pitagoras.prointer.mobile.web.api.UsuarioAPI;
import retrofit2.Call;

public class AlunoActivity extends AppCompatActivity {
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("prointer", Context.MODE_PRIVATE);
        result = sharedPreferences.getString("UserId", "w");
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    Call<Aluno> call = AppLoader.getRetrofit().create(AlunoAPI.class).getAlunosByEmail(result);
                    Aluno aluno = call.execute().body();

                    if (aluno != null) {

                    }

                    } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }).start();
    }
}
