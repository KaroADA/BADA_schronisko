package bada_project_schronisk_KABM.SpringApp.entity;

public class UzytkownikHaslo {
    private int idUzytkownika;
    private String haslo;
    private String powtorzHaslo;
    public int getIdUzytkownika() {
        return idUzytkownika;
    }
    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }
    public String getHaslo() {
        return haslo;
    }
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    public String getPowtorzHaslo() {
        return powtorzHaslo;
    }
    public void setPowtorzHaslo(String powtorzHaslo) {
        this.powtorzHaslo = powtorzHaslo;
    }
    @Override
    public String toString() {
        return "UzytkownikHaslo{" +
                "idUzytkownika=" + idUzytkownika +
                ", haslo='" + haslo + '\'' +
                ", powtorzHaslo='" + powtorzHaslo + '\'' +
                '}';
    }
}
