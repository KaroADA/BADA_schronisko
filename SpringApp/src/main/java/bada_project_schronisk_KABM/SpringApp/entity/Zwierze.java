package bada_project_schronisk_KABM.SpringApp.entity;

import java.util.Date;

public class Zwierze {
    private int idZwierzecia;
    private String imie;
    private String gatunek;
    private Integer wiek;
    private String stanZdrowia;
    private Date dataPrzyjecia;
    private int idKlatki; // Zamiast encji Pomieszczenie - id
    private int idAdopcji; // Zamiast encji Adopcja - id

    // Konstruktory (pamiętaj o konstruktorze bezargumentowym)
    public Zwierze() {}

    public Zwierze(int idZwierzecia, String imie, String gatunek, Integer wiek, String stanZdrowia, Date dataPrzyjecia, int idKlatki, int idAdopcji) {
        this.idZwierzecia = idZwierzecia;
        this.imie = imie;
        this.gatunek = gatunek;
        this.wiek = wiek;
        this.stanZdrowia = stanZdrowia;
        this.dataPrzyjecia = dataPrzyjecia;
        this.idKlatki = idKlatki;
        this.idAdopcji = idAdopcji;
    }

    // Gettery i Settery
    public int getIdZwierzecia() { return idZwierzecia; }
    public void setIdZwierzecia(int idZwierzecia) { this.idZwierzecia = idZwierzecia; }
    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }
    public String getGatunek() { return gatunek; }
    public void setGatunek(String gatunek) { this.gatunek = gatunek; }
    public Integer getWiek() { return wiek; }
    public void setWiek(Integer wiek) { this.wiek = wiek; }
    public String getStanZdrowia() { return stanZdrowia; }
    public void setStanZdrowia(String stanZdrowia) { this.stanZdrowia = stanZdrowia; }
    public Date getDataPrzyjecia() { return dataPrzyjecia; }
    public void setDataPrzyjecia(Date dataPrzyjecia) { this.dataPrzyjecia = dataPrzyjecia; }
    public int getIdKlatki() { return idKlatki; }
    public void setIdKlatki(int idKlatki) { this.idKlatki = idKlatki; }
    public int getIdAdopcji() { return idAdopcji; }
    public void setIdAdopcji(int idAdopcji) { this.idAdopcji = idAdopcji; }

    @Override
    public String toString() {
        return "Zwierze{" +
                "idZwierzecia=" + idZwierzecia +
                ", imie='" + imie + '\'' +
                ", gatunek='" + gatunek + '\'' +
                ", wiek=" + wiek +
                ", stanZdrowia='" + stanZdrowia + '\'' +
                ", dataPrzyjecia=" + dataPrzyjecia +
                ", idKlatki=" + idKlatki +
                ", idAdopcji=" + idAdopcji +
                '}';
    }
}