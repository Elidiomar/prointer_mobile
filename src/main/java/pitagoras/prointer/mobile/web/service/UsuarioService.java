package pitagoras.prointer.mobile.web.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import pitagoras.prointer.mobile.AppLoader;
import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.domain.models.Usuario;
import pitagoras.prointer.mobile.infra.ok.repositories.Repo;
import pitagoras.prointer.mobile.web.api.AlunoAPI;
import pitagoras.prointer.mobile.web.api.UsuarioAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioService {
    private UsuarioAPI apiService = AppLoader.getRetrofit().create(UsuarioAPI.class);
    Repo repo;
    public void getUserByEmail(final TextView txt, Context context) {

        Log.e("TestAluno", "baixando...");
        txt.setText("Baixando...");

        //DatabaseManager.init(context);
        //List<Book> list = DatabaseManager.getInstance().findAllBook();

        //repo = new Repo(context);

        // Create a call instance for looking up Retrofit contributors.
        Call<Usuario> call = apiService.getUsuarioByEmail("aluno1@test.com.br");
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                int statusCode = response.code();
                Usuario usuario = response.body();
                String texto = "";
                if (usuario != null) {
                    Log.e("Test", usuario.getId() + " (" + usuario.getPassword() + ")");
                    txt.setText(usuario.getUserName());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });
    }

}

