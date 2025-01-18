package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Schronisko {
    private int idSchroniska;
    private String nazwa;
    private String adres;
    private String telefon;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataOtwarcia;

    public Schronisko() {}

    public Schronisko(int idSchroniska, String nazwa, String adres, String telefon, Date dataOtwarcia) {
        this.idSchroniska = idSchroniska;
        this.nazwa = nazwa;
        this.adres = adres;
        this.telefon = telefon;
        this.dataOtwarcia = dataOtwarcia;
    }

    // Gettery i Settery
    public int getIdSchroniska() { return idSchroniska; }
    public void setIdSchroniska(int idSchroniska) { this.idSchroniska = idSchroniska; }
    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }
    public String getAdres() { return adres; }
    public void setAdres(String adres) { this.adres = adres; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public Date getDataOtwarcia() { return dataOtwarcia; }
    public void setDataOtwarcia(Date dataOtwarcia) { this.dataOtwarcia = dataOtwarcia; }

    @Override
    public String toString() {
        return "Schronisko{" +
                "idSchroniska=" + idSchroniska +
                ", nazwa='" + nazwa + '\'' +
                ", adres='" + adres + '\'' +
                ", telefon='" + telefon + '\'' +
                ", dataOtwarcia=" + dataOtwarcia +
                '}';
    }
}