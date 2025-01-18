package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KlatkaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public KlatkaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Klatka> list() {
        String sql = "SELECT id_klatki, pojemnosc, typ, id_schroniska FROM Klatki";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Klatka.class));
    }

    public void save(Klatka klatka) {
        String sql = "INSERT INTO Klatki (pojemnosc, typ, id_schroniska) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, klatka.getPojemnosc(), klatka.getTyp(), klatka.getIdSchroniska());
    }

    public Klatka get(int id) {
        String sql = "SELECT id_klatki, pojemnosc, typ, id_schroniska FROM Klatki WHERE id_klatki = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Klatka.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void update(Klatka klatka) {
        String sql = "UPDATE Klatki SET pojemnosc = ?, typ = ?, id_schroniska = ? = ? WHERE id_klatki = ?";
        jdbcTemplate.update(sql, klatka.getPojemnosc(), klatka.getTyp(), klatka.getIdSchroniska(), klatka.getIdKlatki());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Klatki WHERE id_klatki = ?";
        jdbcTemplate.update(sql, id);
    }
}