package br.edu.ifrn.sc.expotec.autenticador.dominio;

public class Inscription {

    private User user;
    private Activity activity;
    private boolean attendance;

    public Inscription() {

    }
    public Inscription(User user, Activity activity, boolean attendance) {
        this.user = user;
        this.activity = activity;
        this.attendance = attendance;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
