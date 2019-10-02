package br.edu.ifrn.sc.expotec.autenticador.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.expotec.autenticador.R;
@JsonIgnoreProperties({"edition", "about", "logo", "start", "end", "address", "promoVideo",
        "blog", "organization", "contact"})
public class Event implements Serializable {

    private long id;
    private String title;
    private int logo;
    private List<Activity> listActivities;
    private int edition;


    public Event() {

        this.listActivities = new ArrayList<Activity>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogo() {
        return R.drawable.logo_expotec_2019_128_x_128;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setListActivities(List<Activity> listActivities) {
        this.listActivities = listActivities;
    }

    public List<Activity> getListActivities() {

        return listActivities;
    }
}
