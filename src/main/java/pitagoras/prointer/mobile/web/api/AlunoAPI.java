package pitagoras.prointer.mobile.web.api;

import java.util.List;

import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.domain.models.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlunoAPI {

    @GET("/api/alunos")
    Call<List<Aluno>> getAlunos();

    @GET("/api/alunos/{email}")
    Call<Aluno> getAlunosByEmail(@Path("email") String email);


}
