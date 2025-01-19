package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Zwierze {
    private int idZwierzecia;
    private String imie;
    private String gatunek;
    private Integer wiek;
    private String stanZdrowia;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataPrzyjecia;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataAdopcji;
    private String plec;
    private String urlZdjecia;
    private Integer idKlatki; // Zamiast encji Pomieszczenie - id
    private Integer idAdopcji; // Zamiast encji Adopcja - id

    private Integer dniOdPrzyjecia;

    // Konstruktory (pamiętaj o konstruktorze bezargumentowym)
    public Zwierze() {}

    public Zwierze(int idZwierzecia, String imie, String gatunek, Integer wiek, String stanZdrowia, Date dataPrzyjecia, Integer idKlatki, Integer idAdopcji, String urlZdjecia, String plec) {
        this.idZwierzecia = idZwierzecia;
        this.imie = imie;
        this.gatunek = gatunek;
        this.wiek = wiek;
        this.stanZdrowia = stanZdrowia;
        this.dataPrzyjecia = dataPrzyjecia;
        this.idKlatki = idKlatki;
        this.idAdopcji = idAdopcji;
        this.urlZdjecia = urlZdjecia;
        this.plec = plec;
    }

    // Gettery i Settery
    public int getIdZwierzecia() { return idZwierzecia; }
    public void setIdZwierzecia(Integer idZwierzecia) { this.idZwierzecia = idZwierzecia; }
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
    public Integer getIdKlatki() { return idKlatki; }
    public void setIdKlatki(Integer idKlatki) { this.idKlatki = idKlatki; }
    public Integer getIdAdopcji() { return idAdopcji; }
    public void setIdAdopcji(Integer idAdopcji) { this.idAdopcji = idAdopcji; }

    public String getPlec() {
        return plec;
    }

    public String getUrlZdjecia() {
        return urlZdjecia;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public void setUrlZdjecia(String urlZdjecia) {
        this.urlZdjecia = urlZdjecia;
    }
    public Date getDataAdopcji() {
        return dataAdopcji;
    }

    public void setDataAdopcji(Date dataAdopcji) {
        this.dataAdopcji = dataAdopcji;
    }

    public Integer getDniOdPrzyjecia() {
        return dniOdPrzyjecia;
    }

    public void setDniOdPrzyjecia(Integer dniOdPrzyjecia) {
        this.dniOdPrzyjecia = dniOdPrzyjecia;
    }

    @Override
    public String toString() {
        return "Zwierze{" +
                "idZwierzecia=" + idZwierzecia +
                ", imie='" + imie + '\'' +
                ", gatunek='" + gatunek + '\'' +
                ", wiek=" + wiek +
                ", stanZdrowia='" + stanZdrowia + '\'' +
                ", dataPrzyjecia=" + dataPrzyjecia +
                ", plec='" + plec + '\'' +
                ", url_zdjecia='" + urlZdjecia + '\'' +
                ", idKlatki=" + idKlatki +
                ", idAdopcji=" + idAdopcji +
                '}';
    }
}