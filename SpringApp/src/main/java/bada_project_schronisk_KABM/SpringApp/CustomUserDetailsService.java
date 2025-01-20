package bada_project_schronisk_KABM.SpringApp;

import bada_project_schronisk_KABM.SpringApp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UzytkownikDAO uzytkownikDAO; // Twoje DAO do tabeli Uzytkownicy
    @Autowired
    private KlientDAO klientDAO;
    @Autowired
    private PracownikDAO pracownikDAO;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println("hkjasf");
        Uzytkownik uzytkownik = uzytkownikDAO.findByLogin(login); // Pobierz użytkownika po loginie z bazy
        System.out.println("Uzytkownik: " + uzytkownik);

        if (uzytkownik == null) {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o loginie: " + login);
        }

        List<String> roleList = new ArrayList<>();
        Integer schr = 0;
        Pracownik p = pracownikDAO.getFromUser(uzytkownik.getIdUzytkownika());
        if(uzytkownik.getCzy_admin()){
            roleList.add("ADMIN");
        }else if (klientDAO.getFromUser(uzytkownik.getIdUzytkownika()) != null) {
            roleList.add("USER");
        } else if (p != null) {
            roleList.add("PRACOWNIK");
            schr = p.getIdSchroniska();
            switch (p.getStanowisko()) {
                case "Kierownik": roleList.addAll(List.of(new String[]{"ZARZADZANIE_KLATKA", "ZARZADZANIE_ZWIERZE", "ZARZADZANIE_PRACOWNIK"}));
                case "Opiekun": roleList.add("ZARZADZANIE_ZWIERZE");
            }
        } else {
            roleList.add("USER");
        }
        System.out.println("SCHR: " + schr);
        String[] roles = roleList.toArray(new String[0]);
        for(String r: roles) {
            System.out.println(r);
        }

        UserDetails u = User.withUsername(uzytkownik.getLogin())
                .password(uzytkownik.getHaslo())
                .roles(roles) // Ustaw role użytkownika
                .build();
        return new CustomUserDetails(uzytkownik.getIdUzytkownika(), schr, u.getUsername(), u.getPassword(), u.getAuthorities());

    }
}