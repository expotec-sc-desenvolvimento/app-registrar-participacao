package br.edu.ifrn.sc.expotec.autenticador.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import br.edu.ifrn.sc.expotec.autenticador.R;

@JsonIgnoreProperties({"description", "numDays", "numMinutes", "limited", "maxAttendees", "targetPublic", "requirements",
        "startInscription", "endInscription", "status", "type", "event", "schedules", "facilitators", "mainGoal"})

public class Activity implements Serializable {

    private long uuid;
    private String title;
    private int status;

    public Activity() {

    }

    public Activity(long uuid, String title) {

        this.uuid = uuid;
        this.title = title;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImagemExibicao() {

        if (status == 1) {
            return R.drawable.evento_encerrado;
        }
        else if (status == 2) {
            return R.drawable.evento_selecionado;
        }
        else {
            return R.drawable.evento_padrao;
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}