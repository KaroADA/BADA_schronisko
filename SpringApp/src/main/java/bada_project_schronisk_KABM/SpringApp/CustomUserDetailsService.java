package bada_project_schronisk_KABM.SpringApp;

import bada_project_schronisk_KABM.SpringApp.entity.Uzytkownik;
import bada_project_schronisk_KABM.SpringApp.entity.UzytkownikDAO;
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

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println("hkjasf");
        Uzytkownik uzytkownik = uzytkownikDAO.findByLogin(login); // Pobierz użytkownika po loginie z bazy
        System.out.println("Uzytkownik: " + uzytkownik);

        if (uzytkownik == null) {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o loginie: " + login);
        }

        List<String> roleList = new ArrayList<>();
        if(uzytkownik.getCzy_admin()){
            roleList.add("ADMIN");
        } else {
            roleList.add("USER");
        }

        String[] roles = roleList.toArray(new String[0]);


        return User.withUsername(uzytkownik.getLogin())
                .password(uzytkownik.getHaslo())
                .roles(roles) // Ustaw role użytkownika
                .build();
    }
}