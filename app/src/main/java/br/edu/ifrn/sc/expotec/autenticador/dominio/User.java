package br.edu.ifrn.sc.expotec.autenticador.dominio;

public class User {

    private long uuid;
    private String name;

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
