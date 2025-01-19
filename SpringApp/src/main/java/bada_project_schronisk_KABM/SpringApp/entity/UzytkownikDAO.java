package bada_project_schronisk_KABM.SpringApp.entity;

import bada_project_schronisk_KABM.SpringApp.entity.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UzytkownikDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Uzytkownik findByLogin(String login) {
        String sql = "SELECT login, haslo, czy_admin FROM Uzytkownicy WHERE login = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Uzytkownik.class), login);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }
}