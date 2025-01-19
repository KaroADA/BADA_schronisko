package bada_project_schronisk_KABM.SpringApp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ZwierzeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ZwierzeDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Zwierze> list() {
        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji, url_zdjecia, plec, TRUNC(SYSDATE - data_przyjecia) AS dni_od_przyjecia FROM Zwierzeta";
        System.out.println("list " + sql);
        return jdbcTemplate.query(sql, new ZwierzeRowMapper());
    }

    public List<Zwierze> listUnadopted() {
        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, plec, url_zdjecia, TRUNC(SYSDATE - data_przyjecia) AS dni_od_przyjecia\n" +
                "FROM Zwierzeta\n" +
                "WHERE id_zwierzecia NOT IN (SELECT id_zwierzecia FROM Adopcje)";
        System.out.println("list " + sql);
        return jdbcTemplate.query(sql, new ZwierzeRowMapper());
    }

    public List<Zwierze> listByUser(Integer userId) {
        String sql = "SELECT * " +
                "FROM Zwierzeta z " +
                "JOIN Adopcje a ON z.id_zwierzecia = a.id_zwierzecia " +
                "JOIN Klienci k ON a.id_klienta = k.id_klienta " +
                "WHERE k.id_uzytkownika = " + userId.toString();
        return jdbcTemplate.query(sql, new ZwierzeRowMapper());
    }

    public List<Zwierze> listBySchroniskoId(int idSchroniska) {
        String sql = "SELECT z.* FROM Zwierzeta z JOIN Klatki k ON z.id_klatki = k.id_klatki WHERE k.id_schroniska = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Zwierze.class), idSchroniska);
    }

    public void save(Zwierze zwierze) {
        String sql = "INSERT INTO Zwierzeta (imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, url_zdjecia, plec) VALUES (?, ?, ?, ?, ?, ?, NULL, ?, ?)";
        jdbcTemplate.update(sql, zwierze.getImie(), zwierze.getGatunek(), zwierze.getWiek(), zwierze.getStanZdrowia(), zwierze.getDataPrzyjecia(), zwierze.getIdKlatki(), zwierze.getUrlZdjecia(), zwierze.getPlec());
    }

    public Zwierze get(int id) {
        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji FROM Zwierzeta WHERE id_zwierzecia = ?";
        try {
            return jdbcTemplate.queryForObject(sql,  new ZwierzeRowMapper(), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void update(Zwierze zwierze) {
        String sql = "UPDATE Zwierzeta SET imie = ?, gatunek = ?, wiek = ?, stan_zdrowia = ?, data_przyjecia = ?, id_klatki = ?, WHERE id_zwierzecia = ?";
        jdbcTemplate.update(sql, zwierze.getImie(), zwierze.getGatunek(), zwierze.getWiek(), zwierze.getStanZdrowia(), zwierze.getDataPrzyjecia(), zwierze.getIdKlatki(), zwierze.getIdZwierzecia());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Zwierzeta WHERE id_zwierzecia = ?";
        jdbcTemplate.update(sql, id);
    }

//    public List<Zwierze> findByStatusAdopcji(String statusAdopcji) {
//        String sql = "SELECT id_zwierzecia, imie, gatunek, wiek, stan_zdrowia, data_przyjecia, id_klatki, id_adopcji";
//        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Zwierze.class));
//    }

    public void adopt(int idZwierzecia, Integer idUzytkownika) {
        Integer idKlienta = jdbcTemplate.queryForObject(
                "SELECT id_klienta FROM Klienci WHERE id_uzytkownika = ?", Integer.class, idUzytkownika
        );
        System.out.println("idUzytkownika: " + idUzytkownika);
        System.out.println("idKlienta: " + idKlienta);
        jdbcTemplate.update(
                "INSERT INTO Adopcje (id_zwierzecia, id_klienta, data_adopcji) VALUES (?, ?, SYSDATE)", idZwierzecia, idKlienta
        );
    }


    public class ZwierzeRowMapper implements RowMapper<Zwierze> {
        @Override
        public Zwierze mapRow(ResultSet rs, int rowNum) throws SQLException {
            System.out.println(rs.getString("imie"));
            Zwierze zwierze = new Zwierze();
            zwierze.setIdZwierzecia(rs.getInt("id_zwierzecia"));
            zwierze.setImie(rs.getString("imie"));
            zwierze.setGatunek(rs.getString("gatunek"));
            zwierze.setWiek(rs.getInt("wiek"));
            zwierze.setStanZdrowia(rs.getString("stan_zdrowia"));
            zwierze.setDataPrzyjecia(rs.getDate("data_przyjecia"));
            zwierze.setUrlZdjecia(rs.getString("url_zdjecia"));
            zwierze.setPlec(rs.getString("plec"));
            zwierze.setIdKlatki(rs.getInt("id_klatki"));
            Integer dniOdPrzyjecia = null;
            try {
                dniOdPrzyjecia = rs.getInt("dni_od_przyjecia");
            } catch (SQLException e) {
                dniOdPrzyjecia=null;
            }
            zwierze.setDniOdPrzyjecia(dniOdPrzyjecia);
            return zwierze;
        }
    }
}