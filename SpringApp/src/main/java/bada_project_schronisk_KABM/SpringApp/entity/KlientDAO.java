package bada_project_schronisk_KABM.SpringApp.entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class KlientDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public KlientDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Klient> list() {
        String sql = "SELECT id_klienta, id_uzytkownika, imie, nazwisko, adres, email, telefon FROM Klienci";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Klient.class));
    }

    public void save(Klient klient) {
        String sql = "INSERT INTO Klienci (imie, nazwisko, adres, email, telefon, id_uzytkownika) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, klient.getImie(), klient.getNazwisko(), klient.getAdres(), klient.getEmail(), klient.getTelefon(), klient.getIdUzytkownika());
    }

    public Klient get(int id) {
        String sql = "SELECT id_klienta, imie, nazwisko, adres, email, telefon FROM Klienci WHERE id_klienta = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new KlientRowMapper(), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null; // Zwracamy null, jeśli nie znaleziono klienta o danym id
        }
    }

    public void update(Klient klient) {
        String sql = "UPDATE Klienci SET imie = ?, nazwisko = ?, adres = ?, email = ?, telefon = ? WHERE id_klienta = ?";
        jdbcTemplate.update(sql, klient.getImie(), klient.getNazwisko(), klient.getAdres(), klient.getEmail(), klient.getTelefon(), klient.getIdKlienta());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Klienci WHERE id_klienta = ?";
        jdbcTemplate.update(sql, id);
    }

    // Klasa mapująca wiersz z bazy danych na obiekt Klient
    private static final class KlientRowMapper implements RowMapper<Klient> {
        @Override
        public Klient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Klient klient = new Klient();
            klient.setIdKlienta(rs.getInt("id_klienta"));
            klient.setImie(rs.getString("imie"));
            klient.setNazwisko(rs.getString("nazwisko"));
            klient.setAdres(rs.getString("adres"));
            klient.setEmail(rs.getString("email"));
            klient.setTelefon(rs.getString("telefon"));
            return klient;
        }
    }
}