package bada_project_schronisk_KABM.SpringApp.entity;

public class UzytkownikZarzadzanie {
    private int id;
    private String login;
    private String haslo;
    private String imie;
    private String nazwisko;
    private String adres;
    private String email;
    private String telefon;
    private Boolean czy_admin;
    private Boolean czy_pracownik;
    private String stanowisko;
    private Double wynagrodzenie;
    private Integer idSchroniska;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Gettery
    public String getLogin() { return login; }
    public String getHaslo() { return haslo; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public String getAdres() { return adres; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }
    public Boolean getCzy_admin() { return czy_admin; }
    public Boolean getCzy_pracownik() { return czy_pracownik; }
    public String getStanowisko() { return stanowisko; }
    public Double getWynagrodzenie() { return wynagrodzenie; }
    public Integer getIdSchroniska() { return idSchroniska; }

    // Settery
    public void setLogin(String login) { this.login = login; }
    public void setHaslo(String haslo) { this.haslo = haslo; }
    public void setImie(String imie) { this.imie = imie; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }
    public void setAdres(String adres) { this.adres = adres; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public void setCzy_admin(Boolean czy_admin) { this.czy_admin = czy_admin; }
    public void setCzy_pracownik(Boolean czy_pracownik) { this.czy_pracownik = czy_pracownik; }
    public void setStanowisko(String stanowisko) { this.stanowisko = stanowisko; }
    public void setWynagrodzenie(Double wynagrodzenie) { this.wynagrodzenie = wynagrodzenie; }
    public void setIdSchroniska(Integer idSchroniska) { this.idSchroniska = idSchroniska; }

    // toString()
    @Override
    public String toString() {
        return "UzytkownikZarzadzanie{" +
                "login='" + login + '\'' +
                ", haslo='" + haslo + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", adres='" + adres + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", czy_admin=" + czy_admin +
                ", czy_pracownik=" + czy_pracownik +
                ", stanowisko='" + stanowisko + '\'' +
                ", wynagrodzenie=" + wynagrodzenie +
                ", idSchroniska=" + idSchroniska +
                '}';
    }
}
