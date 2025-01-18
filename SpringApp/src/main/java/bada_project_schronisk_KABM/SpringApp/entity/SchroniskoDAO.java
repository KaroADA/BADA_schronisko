package bada_project_schronisk_KABM.SpringApp.entity;

import bada_project_schronisk_KABM.SpringApp.entity.Schronisko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SchroniskoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SchroniskoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Schronisko> list() {
        String sql = "SELECT id_schroniska, nazwa, adres, telefon, data_otwarcia FROM Schroniska";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Schronisko.class));
    }

    public void save(Schronisko schronisko) {
        String sql = "INSERT INTO Schroniska (nazwa, adres, telefon, data_otwarcia) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, schronisko.getNazwa(), schronisko.getAdres(), schronisko.getTelefon(), schronisko.getDataOtwarcia());
    }

    public Schronisko get(int id) {
        String sql = "SELECT id_schroniska, nazwa, adres, telefon, data_otwarcia FROM Schroniska WHERE id_schroniska = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Schronisko.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void update(Schronisko schronisko) {
        String sql = "UPDATE Schroniska SET nazwa = ?, adres = ?, telefon = ?, data_otwarcia = ? WHERE id_schroniska = ?";
        jdbcTemplate.update(sql, schronisko.getNazwa(), schronisko.getAdres(), schronisko.getTelefon(), schronisko.getDataOtwarcia(), schronisko.getIdSchroniska());
    }

    @Transactional
    public void delete(int id) {
        String sql = "DELETE FROM Schroniska WHERE id_schroniska = ?";
        jdbcTemplate.update(sql, id);
    }
}