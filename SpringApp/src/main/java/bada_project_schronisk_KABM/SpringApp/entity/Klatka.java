package bada_project_schronisk_KABM.SpringApp.entity;

public class Klatka {
    private int idKlatki;
    private int pojemnosc;
    private String typ;
    private int idSchroniska;

    public Klatka() {}

    public Klatka(int idKlatki, int pojemnosc, String typ, int idSchroniska) {
        this.idKlatki = idKlatki;
        this.pojemnosc = pojemnosc;
        this.typ = typ;
        this.idSchroniska = idSchroniska;
    }

    // Gettery i Settery
    public int getIdKlatki() { return idKlatki; }
    public void setIdKlatki(int idKlatki) { this.idKlatki = idKlatki; }
    public int getPojemnosc() { return pojemnosc; }
    public void setPojemnosc(int pojemnosc) { this.pojemnosc = pojemnosc; }
    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }
    public int getIdSchroniska() { return idSchroniska; }
    public void setIdSchroniska(int idSchroniska) { this.idSchroniska = idSchroniska; }

    @Override
    public String toString() {
        return "Klatka{" +
                "idKlatki=" + idKlatki +
                ", pojemnosc=" + pojemnosc +
                ", typ='" + typ + '\'' +
                ", idSchroniska=" + idSchroniska +
                '}';
    }
}