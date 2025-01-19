package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PracownikDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PracownikDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pracownik> list() {
        String sql = "SELECT id_pracownika, id_uzytkownika, imie, nazwisko, stanowisko, wynagrodzenie, telefon, email, id_schroniska FROM Pracownicy";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pracownik.class));
    }

    public List<Pracownik> listBySchroniskoId(int idSchroniska) {
        String sql = "SELECT * FROM Pracownicy WHERE id_schroniska = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pracownik.class), idSchroniska);
    }

    public void save(Pracownik pracownik) {
        String sql = "INSERT INTO Pracownicy (imie, nazwisko, stanowisko, wynagrodzenie, telefon, email, id_schroniska, id_uzytkownika) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pracownik.getImie(), pracownik.getNazwisko(), pracownik.getStanowisko(), pracownik.getWynagrodzenie(), pracownik.getTelefon(), pracownik.getEmail(), pracownik.getIdSchroniska(), pracownik.getIdUzytkownika());
    }

    public Pracownik get(int id) {
        String sql = "SELECT * FROM Pracownicy WHERE id_pracownika = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Pracownik.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Pracownik getFromUser(int id) {
        String sql = "SELECT * FROM Pracownicy WHERE id_uzytkownika = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Pracownik.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null; // Zwracamy null, jeśli nie znaleziono klienta o danym id
        }
    }

    public void update(Pracownik pracownik) {
        String sql = "UPDATE Pracownicy SET imie = ?, nazwisko = ?, stanowisko = ?, wynagrodzenie = ?, telefon = ?, email = ?, id_schroniska = ?, id_uzytkownika = ? WHERE id_pracownika = ?";
        jdbcTemplate.update(sql, pracownik.getImie(), pracownik.getNazwisko(), pracownik.getStanowisko(), pracownik.getWynagrodzenie(), pracownik.getTelefon(), pracownik.getEmail(), pracownik.getIdSchroniska(), pracownik.getIdPracownika(), pracownik.getIdUzytkownika());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Pracownicy WHERE id_pracownika = ?";
        jdbcTemplate.update(sql, id);
    }
}