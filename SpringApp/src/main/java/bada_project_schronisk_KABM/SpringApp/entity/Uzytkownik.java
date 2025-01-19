package bada_project_schronisk_KABM.SpringApp.entity;

public class Uzytkownik {

    private Integer idUzytkownika;
    private String login;
    private String haslo;
    private Boolean czy_admin;

    // Konstruktory (pusty i z argumentami)
    public Uzytkownik() {
    }

    public Uzytkownik(String login, String haslo, Boolean czy_admin) {
        this.login = login;
        this.haslo = haslo;
        this.czy_admin = czy_admin;
    }

    // Gettery i settery
    public Integer getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(Integer idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Boolean getCzy_admin() {
        return czy_admin;
    }

    public void setCzy_admin(Boolean czy_admin) {
        this.czy_admin = czy_admin;
    }

    // metoda toString() (opcjonalnie, ale przydatna do debugowania)
    @Override
    public String toString() {
        return "Uzytkownik{" +
                "id=" + idUzytkownika +
                ", login='" + login + '\'' +
                ", haslo='" + haslo + '\'' +
                ", czy_admin=" + czy_admin +
                '}';
    }
}