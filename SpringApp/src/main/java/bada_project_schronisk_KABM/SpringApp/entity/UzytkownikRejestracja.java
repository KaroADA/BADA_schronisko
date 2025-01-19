package bada_project_schronisk_KABM.SpringApp.entity;


public class UzytkownikRejestracja {

    private String login;

    private String haslo;

    private String powtorzHaslo;

    // Gettery i settery dla wszystkich pól
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getHaslo() { return haslo; }
    public void setHaslo(String haslo) { this.haslo = haslo; }
    public String getPowtorzHaslo() { return powtorzHaslo; }
    public void setPowtorzHaslo(String powtorzHaslo) { this.powtorzHaslo = powtorzHaslo; }
}
