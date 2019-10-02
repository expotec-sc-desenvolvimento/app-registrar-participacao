package br.edu.ifrn.sc.expotec.autenticador.ws;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {


    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://lausana.ifrn.edu.br/expotecsc/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public AttendantsService getAttendantsService() {
        return this.retrofit.create(AttendantsService.class);
    }

    public ActivitiesInterface getActivitiesInterface() {
        return this.retrofit.create(ActivitiesInterface.class);
    }

    public EventService getEventService() {

        return this.retrofit.create(EventService.class);
    }

    public UserService getUserService(){

        return this.retrofit.create(UserService.class);
    }
}
