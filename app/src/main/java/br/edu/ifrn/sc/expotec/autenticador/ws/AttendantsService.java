package br.edu.ifrn.sc.expotec.autenticador.ws;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AttendantsService {

    @POST("presenca/{idAtividade}/{idUsuario}")
    Call<Boolean> registrarPresenca(@Path("idAtividade") long activityUuid, @Path("idUsuario") long userUuid);
}
