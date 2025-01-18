package bada_project_schronisk_KABM.SpringApp.entity;

public class Klient {
    private int idKlienta;
    private String imie;
    private String nazwisko;
    private String adres;
    private String email;
    private String telefon;

    // Konstruktor domyślny (bezargumentowy) - wymagany dla niektórych frameworków
    public Klient() {
    }

    // Konstruktor z argumentami - ułatwia tworzenie obiektów
    public Klient(int idKlienta, String imie, String nazwisko, String adres, String email, String telefon) {
        this.idKlienta = idKlienta;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adres = adres;
        this.email = email;
        this.telefon = telefon;
    }

    // Gettery
    public int getIdKlienta() {
        return idKlienta;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getAdres() {
        return adres;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    // Settery
    public void setIdKlienta(int idKlienta) {
        this.idKlienta = idKlienta;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Klient{" +
                "idKlienta=" + idKlienta +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", adres='" + adres + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}