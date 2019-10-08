package br.edu.ifrn.sc.expotec.autenticador.ws;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AttendantsService {

    @POST("presenca")
    @FormUrlEncoded
    Call<Boolean> registrarPresenca(@Field("idAtividade") long activityUuid, @Field("idUsuario") long userUuid);
}
