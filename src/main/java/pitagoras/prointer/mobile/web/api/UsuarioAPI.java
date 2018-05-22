package pitagoras.prointer.mobile.web.api;

import java.util.List;

import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.domain.models.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioAPI {

    @GET("/api/usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("/api/usuarios/{email}")
    Call<Usuario> getUsuarioByEmail(@Path("email") String email);

}
