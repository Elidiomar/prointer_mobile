package pitagoras.prointer.mobile.web.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import pitagoras.prointer.mobile.AppLoader;
import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.infra.ok.repositories.Repo;
import pitagoras.prointer.mobile.web.api.AlunoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoService {
    private AlunoAPI apiService = AppLoader.getRetrofit().create(AlunoAPI.class);
    Repo repo;
    public void getAlunos(final TextView txt, Context context) {

        Log.e("TestAluno", "baixando...");
        txt.setText("Baixando...");

        //DatabaseManager.init(context);
        //List<Book> list = DatabaseManager.getInstance().findAllBook();

        //repo = new Repo(context);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Aluno>> call = apiService.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(@NonNull Call<List<Aluno>> call, @NonNull Response<List<Aluno>> response) {
                int statusCode = response.code();
                List<Aluno> alunos = response.body();
                String texto = "";
                if (alunos != null) {
                    for (Aluno aluno : alunos) {
                        Log.e("Test", aluno.getName() + " (" + aluno.getEmail() + ")");
                        texto += aluno.getName() + "\n";
                    }

                    txt.setText(texto);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Aluno>> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });
    }

}

