package br.edu.ifrn.sc.expotec.autenticador.ws;

import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.dominio.Event;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EventService {

    @GET("list/events")
    Call<List<Event>> listEvents();
}
