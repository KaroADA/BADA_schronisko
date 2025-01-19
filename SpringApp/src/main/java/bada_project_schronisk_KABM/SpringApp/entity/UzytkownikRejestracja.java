package bada_project_schronisk_KABM.SpringApp.entity;


public class UzytkownikRejestracja {

    private String login;
    private String haslo;
    private String powtorzHaslo;
    private String imie;
    private String nazwisko;
    private String adres;
    private String email;
    private String telefon;

    // Gettery i settery dla wszystkich pól
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getHaslo() { return haslo; }
    public void setHaslo(String haslo) { this.haslo = haslo; }
    public String getPowtorzHaslo() { return powtorzHaslo; }
    public void setPowtorzHaslo(String powtorzHaslo) { this.powtorzHaslo = powtorzHaslo; }
    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
