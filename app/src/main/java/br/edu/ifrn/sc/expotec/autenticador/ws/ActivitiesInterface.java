package br.edu.ifrn.sc.expotec.autenticador.ws;

import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.dominio.Activity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ActivitiesInterface {

    @GET("list/activities/{id}")
    Call<List<Activity>> listActivities(@Path("id") long id);
}
