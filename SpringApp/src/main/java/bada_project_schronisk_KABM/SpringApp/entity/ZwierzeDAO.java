package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZwierzeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ZwierzeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Zwierze> list() {
        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji, url_zdjecia, plec FROM Zwierzeta";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Zwierze.class));
    }

    public void save(Zwierze zwierze) {
        String sql = "INSERT INTO Zwierzeta (imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, zwierze.getImie(), zwierze.getGatunek(), zwierze.getWiek(), zwierze.getStanZdrowia(), zwierze.getDataPrzyjecia(), zwierze.getIdKlatki(), zwierze.getIdAdopcji());
    }

    public Zwierze get(int id) {
        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji FROM Zwierzeta WHERE id_zwierzecia = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Zwierze.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void update(Zwierze zwierze) {
        String sql = "UPDATE Zwierzeta SET imie = ?, gatunek = ?, wiek = ?, stan_zdrowia = ?, data_przyjecia = ?, id_klatki = ?, id_adopcji = ? WHERE id_zwierzecia = ?";
        jdbcTemplate.update(sql, zwierze.getImie(), zwierze.getGatunek(), zwierze.getWiek(), zwierze.getStanZdrowia(), zwierze.getDataPrzyjecia(), zwierze.getIdKlatki(), zwierze.getIdAdopcji(), zwierze.getIdZwierzecia());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Zwierzeta WHERE id_zwierzecia = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Zwierze> findByStatusAdopcji(String statusAdopcji) {
        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Zwierze.class));
    }
}