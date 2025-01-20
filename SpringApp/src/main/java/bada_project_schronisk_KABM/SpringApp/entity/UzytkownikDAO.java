package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UzytkownikDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Uzytkownik findByLogin(String login) {
        String sql = "SELECT id_uzytkownika, login, haslo, czy_admin FROM Uzytkownicy WHERE login = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Uzytkownik.class), login);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void save(Uzytkownik uzytkownik) {
        String sql = "INSERT INTO Uzytkownicy (login, haslo, czy_admin) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, uzytkownik.getLogin(), uzytkownik.getHaslo(), uzytkownik.getCzy_admin());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Uzytkownicy WHERE id_uzytkownika = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(UzytkownikZarzadzanie uzytkownik) {
        String sql = "UPDATE Uzytkownicy SET login = ?, haslo = ?, czy_admin = ? WHERE id_uzytkownika = ?";
        jdbcTemplate.update(sql, uzytkownik.getLogin(), uzytkownik.getHaslo(), uzytkownik.getCzy_admin(), uzytkownik.getId());
    }
    public void update(Uzytkownik uzytkownik) {
        String sql = "UPDATE Uzytkownicy SET login = ?, haslo = ?, czy_admin = ? WHERE id_uzytkownika = ?";
        jdbcTemplate.update(sql, uzytkownik.getLogin(), uzytkownik.getHaslo(), uzytkownik.getCzy_admin(), uzytkownik.getIdUzytkownika());
    }

    public Uzytkownik get(int id) {
        String sql = "SELECT * FROM Uzytkownicy WHERE id_uzytkownika = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Uzytkownik.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Uzytkownik> list() {
        String sql = "SELECT id_uzytkownika, login, haslo, czy_admin FROM Uzytkownicy";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Uzytkownik.class));
    }
}