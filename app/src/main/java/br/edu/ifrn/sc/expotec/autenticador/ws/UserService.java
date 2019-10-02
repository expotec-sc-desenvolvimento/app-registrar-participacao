package br.edu.ifrn.sc.expotec.autenticador.ws;

import br.edu.ifrn.sc.expotec.autenticador.dominio.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("detail/user/{id}")
    Call<User> detailUser(@Path("id") long id);
}
