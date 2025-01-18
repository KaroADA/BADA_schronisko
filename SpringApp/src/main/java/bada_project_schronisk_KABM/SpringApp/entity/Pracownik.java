package bada_project_schronisk_KABM.SpringApp.entity;

public class Pracownik {
    private int idPracownika;
    private String imie;
    private String nazwisko;
    private String stanowisko;
    private double wynagrodzenie;
    private String telefon;
    private String email;
    private int idSchroniska;

    public Pracownik() {}

    public Pracownik(int idPracownika, String imie, String nazwisko, String stanowisko, double wynagrodzenie, String telefon, String email, int idSchroniska) {
        this.idPracownika = idPracownika;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stanowisko = stanowisko;
        this.wynagrodzenie = wynagrodzenie;
        this.telefon = telefon;
        this.email = email;
        this.idSchroniska = idSchroniska;
    }

    // Gettery i Settery
    public int getIdPracownika() { return idPracownika; }
    public void setIdPracownika(int idPracownika) { this.idPracownika = idPracownika; }
    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }
    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }
    public String getStanowisko() { return stanowisko; }
    public void setStanowisko(String stanowisko) { this.stanowisko = stanowisko; }
    public double getWynagrodzenie() { return wynagrodzenie; }
    public void setWynagrodzenie(double wynagrodzenie) { this.wynagrodzenie = wynagrodzenie; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getIdSchroniska() { return idSchroniska; }
    public void setIdSchroniska(int idSchroniska) { this.idSchroniska = idSchroniska; }

    @Override
    public String toString() {
        return "Pracownik{" +
                "idPracownika=" + idPracownika +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", stanowisko='" + stanowisko + '\'' +
                ", wynagrodzenie=" + wynagrodzenie +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", idSchroniska=" + idSchroniska +
                '}';
    }
}