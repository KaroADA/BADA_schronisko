package bada_project_schronisk_KABM.SpringApp;

import bada_project_schronisk_KABM.SpringApp.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Configuration
public class AppController implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/nowe_konto").setViewName("nowe_konto");
        registry.addViewController("/main_admin").setViewName("admin/main_admin");
        registry.addViewController("/main_user").setViewName("user/main_user");
        registry.addViewController("/main_pracownik").setViewName("user/main_pracownik");
        registry.addViewController("/adopcje_user").setViewName("user/adopcje_user");
    }

    @Controller
    public class DashboardController {
        @RequestMapping("/main")
        public String defaultAfterLogin(HttpServletRequest request) {
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/main_admin";
            } else if (request.isUserInRole("USER")) {
                return "redirect:/main_user";
            }else if (request.isUserInRole("PRACOWNIK")) {
                return "redirect:/main_pracownik";
            } else {
                return "redirect:/";
            }
        }
        @Autowired
        private SchroniskoDAO schroniskoDAO;
        @Autowired
        private PracownikDAO pracownikDAO;
        @Autowired
        private KlatkaDAO klatkaDAO;
        @Autowired
        private ZwierzeDAO zwierzeDAO;
        @Autowired
        private KlientDAO klientDAO;
        @Autowired private UzytkownikDAO uzytkownikDAO;
        @Autowired private PasswordEncoder passwordEncoder;

        @RequestMapping("/main_user")
        public String showUserPage(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                // Użytkownik nie jest zalogowany, obsłuż ten przypadek (np. przekierowanie na stronę logowania)
                return "redirect:/login"; //lub inny odpowiedni adres
            }
            UserDetails details = (UserDetails) authentication.getPrincipal();
            String username = details.getUsername();

            Uzytkownik uzytkownik = uzytkownikDAO.findByLogin(username);
            if (uzytkownik == null) {
                return "error"; //Lub inna strona błędu
            }

            Integer userId = uzytkownik.getIdUzytkownika();
            System.out.println("USER ID " + userId);

            List<Zwierze> zwierzeta = zwierzeDAO.listByUser(userId);
            model.addAttribute("uzytkownik", uzytkownik);
            model.addAttribute("zwierzeta", zwierzeta);

            return "user/main_user";
        }
        @RequestMapping("/adopcje_user")
        public String showAdopcjeUserPage(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                // Użytkownik nie jest zalogowany, obsłuż ten przypadek (np. przekierowanie na stronę logowania)
                return "redirect:/login"; //lub inny odpowiedni adres
            }
            UserDetails details = (UserDetails) authentication.getPrincipal();
            String username = details.getUsername();

            Uzytkownik uzytkownik = uzytkownikDAO.findByLogin(username);
            if (uzytkownik == null) {
                return "error"; //Lub inna strona błędu
            }

            Integer userId = uzytkownik.getIdUzytkownika();
            System.out.println("USER ID" + userId);

            List<Zwierze> zwierzeta = zwierzeDAO.listUnadopted();
            System.out.println(zwierzeta);
            model.addAttribute("zwierzeta", zwierzeta);

            return "user/adopcje_user";
        }
        @PostMapping("/adoptuj/{id}")
        public String adoptujZwierze(@PathVariable("id") int idZwierzecia) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return "redirect:/login";
            }

            UserDetails details = (UserDetails) authentication.getPrincipal();
            String username = details.getUsername();
            Uzytkownik uzytkownik = uzytkownikDAO.findByLogin(username);
            System.out.println("username " + username);
            System.out.println("uzytkownik " + uzytkownik);

            if (uzytkownik == null) {
                return "error";
            }


            try {
                zwierzeDAO.adopt(idZwierzecia, uzytkownik.getIdUzytkownika()); // Zakładam, że masz taką metodę w DAO
                return "redirect:/main_user"; // Przekierowanie po udanej adopcji
            } catch (Exception e) {
                // Obsługa błędów (np. zwierzę już adoptowane)
                // Dodaj do modelu atrybut z informacją o błędzie
                // i zwróć widok "user/adopcje_user" lub inny widok błędu.
                System.out.println(e);
                return "error";
            }
        }
        @RequestMapping(value={"index", "/"})
        public String showIndexPage(Model model) {
            List<Zwierze> zwierzeta = zwierzeDAO.listUnadopted();
            System.out.println(zwierzeta);
            model.addAttribute("zwierzeta", zwierzeta);
            return "index";
        }

        @RequestMapping("/main_pracownik")
        public String showPracownikPage(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                // Użytkownik nie jest zalogowany, obsłuż ten przypadek (np. przekierowanie na stronę logowania)
                return "redirect:/login"; //lub inny odpowiedni adres
            }
            CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(details.getIdSchroniska());
            String username = details.getUsername();
            Uzytkownik uzytkownik = uzytkownikDAO.findByLogin(username);

            Pracownik pracownik = pracownikDAO.getFromUser(uzytkownik.getIdUzytkownika());
            Schronisko schronisko = schroniskoDAO.get(pracownik.getIdSchroniska());
            List<Klatka> klatki = klatkaDAO.listBySchroniskoId(schronisko.getIdSchroniska());
            List<Zwierze> zwierzeta = zwierzeDAO.listBySchroniskoId(schronisko.getIdSchroniska());
            List<Pracownik> pracownicy = pracownikDAO.listBySchroniskoId(schronisko.getIdSchroniska());

            model.addAttribute("pracownik", pracownik);
            model.addAttribute("schronisko", schronisko);
            model.addAttribute("pracownicy", pracownicy);
            model.addAttribute("klatki", klatki);
            model.addAttribute("zwierzeta", zwierzeta);

            return "user/main_pracownik";
        }

        @RequestMapping("/nowe_konto")
        public String rejestracja(Model model) {
            model.addAttribute("uzytkownik", new Uzytkownik());
            return "nowe_konto";
        }
        @RequestMapping("/stworz_nowe_konto")
        public String noweKonto(UzytkownikRejestracja uzytkownik, BindingResult result, RedirectAttributes redirectAttributes) {
            if (result.hasErrors()) {
                return "nowe_konto";
            }

            if (!uzytkownik.getHaslo().equals(uzytkownik.getPowtorzHaslo())) {
                redirectAttributes.addFlashAttribute("haslaNiezgodne", "Hasła muszą być identyczne."); // Dodajemy komunikat flash
                return "redirect:/nowe_konto";
            }

            if (uzytkownikDAO.findByLogin(uzytkownik.getLogin()) != null) {
                redirectAttributes.addFlashAttribute("loginZajety", "Podany login jest już zajęty."); // Dodajemy komunikat flash
                return "redirect:/nowe_konto";
            }

            Uzytkownik nowy = new Uzytkownik();
            nowy.setLogin(uzytkownik.getLogin());
            nowy.setHaslo(passwordEncoder.encode(uzytkownik.getHaslo()));
            nowy.setCzy_admin(false);
            uzytkownikDAO.save(nowy);

            Klient klient = new Klient();
            klient.setAdres(uzytkownik.getAdres());
            klient.setEmail(uzytkownik.getEmail());
            klient.setImie(uzytkownik.getImie());
            klient.setNazwisko(uzytkownik.getNazwisko());
            klient.setTelefon(uzytkownik.getTelefon());
            klient.setIdUzytkownika(uzytkownikDAO.findByLogin(nowy.getLogin()).getIdUzytkownika());
            System.out.println("KLIENT: " + klient);
            klientDAO.save(klient);

            redirectAttributes.addFlashAttribute("successMessage", "Rejestracja przebiegła pomyślnie. Możesz się zalogować.");
            return "redirect:/login";
        }
        @RequestMapping("/admin/removeUzytkownik")
        public String removeUzytkownik(int id, Model model) {
            System.out.println("DEL " + id);
            uzytkownikDAO.delete(id);
            return showAdminPage(model);
        }
        @RequestMapping("/admin/uzytkownik_admin/{id}")
        public String zarzadzanieUzytkownik(@PathVariable("id") int id, Model model) {
            Uzytkownik uzytkownik = uzytkownikDAO.get(id);
            if (uzytkownik == null) {
                return "error";
            }

            UzytkownikZarzadzanie uzytkownikZarzadzanie = new UzytkownikZarzadzanie();
            uzytkownikZarzadzanie.setId(id);
            uzytkownikZarzadzanie.setLogin(uzytkownik.getLogin());
            uzytkownikZarzadzanie.setHaslo(uzytkownik.getHaslo());
            uzytkownikZarzadzanie.setCzy_admin(uzytkownik.getCzy_admin());

            Optional<Klient> klient = Optional.ofNullable(klientDAO.getFromUser(id));
            klient.ifPresent(k -> {
                uzytkownikZarzadzanie.setCzy_pracownik(false);
                uzytkownikZarzadzanie.setAdres(k.getAdres());
                uzytkownikZarzadzanie.setImie(k.getImie());
                uzytkownikZarzadzanie.setNazwisko(k.getNazwisko());
                uzytkownikZarzadzanie.setEmail(k.getEmail());
                uzytkownikZarzadzanie.setTelefon(k.getTelefon());
            });

            Optional<Pracownik> pracownik = Optional.ofNullable(pracownikDAO.getFromUser(id));
            pracownik.ifPresent(p -> {
                uzytkownikZarzadzanie.setCzy_pracownik(true);
                uzytkownikZarzadzanie.setStanowisko(p.getStanowisko());
                uzytkownikZarzadzanie.setWynagrodzenie(p.getWynagrodzenie());
                uzytkownikZarzadzanie.setIdSchroniska(p.getIdSchroniska());
                uzytkownikZarzadzanie.setImie(p.getImie());
                uzytkownikZarzadzanie.setNazwisko(p.getNazwisko());
                uzytkownikZarzadzanie.setEmail(p.getEmail());
                uzytkownikZarzadzanie.setTelefon(p.getTelefon());
            });

            if (uzytkownikZarzadzanie.getImie() == null) uzytkownikZarzadzanie.setImie("");
            if (uzytkownikZarzadzanie.getNazwisko() == null) uzytkownikZarzadzanie.setNazwisko("");
            if (uzytkownikZarzadzanie.getAdres() == null) uzytkownikZarzadzanie.setAdres("");
            if (uzytkownikZarzadzanie.getTelefon() == null) uzytkownikZarzadzanie.setTelefon("");
            if (uzytkownikZarzadzanie.getEmail() == null) uzytkownikZarzadzanie.setEmail("");
//            if(uzytkownikZarzadzanie.getCzy_pracownik() == null) uzytkownikZarzadzanie.setCzy_pracownik(false);

            System.out.println("ENTER " + uzytkownikZarzadzanie);
            model.addAttribute("uzytkownikZarzadzanie", uzytkownikZarzadzanie); // Przekazujemy obiekt do widoku
            return "admin/uzytkownik_admin";
        }
        @RequestMapping("/admin/updateUser")
        public String updateUser(UzytkownikZarzadzanie uzytkownik, RedirectAttributes redirectAttributes) {
            System.out.println("UPDATE");
            System.out.println(uzytkownik);
            int id = uzytkownik.getId();

            uzytkownikDAO.update(uzytkownik);
            if (uzytkownik.getCzy_pracownik()) {
                if (uzytkownik.getStanowisko() == null) {
                    uzytkownik.setStanowisko("test");
                }
                if (uzytkownik.getWynagrodzenie() == null) {
                    uzytkownik.setWynagrodzenie((double) 0);
                }
                if (uzytkownik.getIdSchroniska() == null) {
                    uzytkownik.setIdSchroniska(1);
                }
                System.out.println("AFTER");
                System.out.println(uzytkownik);
                Pracownik p = new Pracownik(0, id, uzytkownik.getImie(), uzytkownik.getNazwisko(), uzytkownik.getStanowisko(), uzytkownik.getWynagrodzenie(), uzytkownik.getTelefon(), uzytkownik.getEmail(), uzytkownik.getIdSchroniska());
                Pracownik getp = pracownikDAO.getFromUser(id);
                if (getp == null) {
                    klientDAO.delete(klientDAO.getFromUser(id).getIdKlienta());
                    pracownikDAO.save(p);
                } else {
                    p.setIdPracownika(getp.getIdPracownika());
                    pracownikDAO.update(p);
                }
            } else {
                Klient k = new Klient(0, id, uzytkownik.getImie(), uzytkownik.getNazwisko(), uzytkownik.getAdres(), uzytkownik.getEmail(), uzytkownik.getTelefon());
                Klient getk = klientDAO.getFromUser(id);
                if (getk == null) {
                    pracownikDAO.delete(pracownikDAO.getFromUser(id).getIdPracownika());
                    klientDAO.save(k);
                } else {
                    k.setIdKlienta(getk.getIdKlienta());
                    klientDAO.update(k);
                }
            }
//            if (klient != null) {
//                klientDAO.update(klient);
//            }
//            if (pracownik != null) {
//                pracownikDAO.update(pracownik);
//            }

            redirectAttributes.addFlashAttribute("success", "Dane użytkownika zostały zaktualizowane.");
            return "redirect:/admin/uzytkownik_admin/" + uzytkownik.getId();
        }

        @RequestMapping("/user_management/haslo/{id}")
        @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
        public String showHaslo(@PathVariable("id") int id, Model model) {
            UzytkownikHaslo uzytkownikHaslo = new UzytkownikHaslo();
            uzytkownikHaslo.setIdUzytkownika(id);
            model.addAttribute("uzytkownik", uzytkownikHaslo);
            return "user_management/haslo";
        }

        @RequestMapping("/user_management/nowe_haslo")
        public String noweHaslo(UzytkownikHaslo uzytkownik, RedirectAttributes redirectAttributes) {
            System.out.println(uzytkownik);
            if (!uzytkownik.getHaslo().equals(uzytkownik.getPowtorzHaslo())) {
                redirectAttributes.addFlashAttribute("haslaNiezgodne", "Hasła muszą być identyczne."); // Dodajemy komunikat flash
                return "redirect:/user_management/haslo/" + uzytkownik.getIdUzytkownika();
            }
            Uzytkownik u = uzytkownikDAO.get(uzytkownik.getIdUzytkownika());
            u.setHaslo(passwordEncoder.encode(uzytkownik.getHaslo()));
            uzytkownikDAO.update(u);
            return user_powrot(u.getIdUzytkownika());
        }
        @RequestMapping("/user_management/powrot/{id}")
        public String user_powrot(@PathVariable("id") int id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return "redirect:/login";
            }
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) { // Roles typically have a "ROLE_" prefix
                    return "redirect:/admin/uzytkownik_admin/" + id;
                }
                if (authority.getAuthority().equals("ROLE_PRACOWNIK")) { // Roles typically have a "ROLE_" prefix
                    return "redirect:/main_pracownik";
                }
            }
            return "redirect:/main_user";
        }

        @RequestMapping("/schronisko_management/powrot/{id}")
        public String schronisko_powrot(@PathVariable("id") int id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return "redirect:/login";
            }
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    return "redirect:/admin/schronisko_admin/" + id;
                }
            }
            return "redirect:/main_pracownik";
        }
        @RequestMapping(value = "/schronisko_management/klatka/{schr}/{id}", method = RequestMethod.GET)
        @PreAuthorize("hasRole('ADMIN') or  (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_KLATKA'))")
        public String showKlatka(@PathVariable("schr") int schr, @PathVariable("id") int id, Model model) {
            Klatka klatka = klatkaDAO.get(id);
            if(klatka.getIdSchroniska() != schr) {
                return "redirect:/error";
            }
            model.addAttribute("klatka", klatka);
            return "schronisko_management/klatka";
        }
        @RequestMapping("/schronisko_management/klatka/{schr}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_KLATKA'))")
        public String nowaKlatka(@PathVariable("schr") int schr, Model model) {
            Klatka klatka = new Klatka();
            klatka.setIdSchroniska(schr);
            model.addAttribute("klatka", klatka);
            return "schronisko_management/klatka";
        }

        @RequestMapping("/schronisko_management/klatka_potwierdz")
        public String confirmKlatka(Klatka klatka) {
            if (klatkaDAO.get(klatka.getIdKlatki()) == null) {
                klatkaDAO.save(klatka);
            } else {
                klatkaDAO.update(klatka);
            }
            return schronisko_powrot(klatka.getIdSchroniska());
        }

        @RequestMapping("/schronisko_management/zwierze/{schr}/{id}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_ZWIERZE'))")
        public String showZwierze(@PathVariable("schr") int schr, @PathVariable("id") int id, Model model) {
            Zwierze zwierze = zwierzeDAO.get(id);
            if(klatkaDAO.get(zwierze.getIdKlatki()).getIdSchroniska() != schr) {
                return "redirect:/error";
            }
            model.addAttribute("zwierze", zwierze);
            return "schronisko_management/zwierze";
        }
        @RequestMapping("/schronisko_management/zwierze/{schr}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_ZWIERZE'))")
        public String noweZwierze(@PathVariable("schr") int schr, Model model) {
            Zwierze zwierze = new Zwierze();
            model.addAttribute("zwierze", zwierze);
            return "schronisko_management/zwierze";
        }
        @RequestMapping("/schronisko_management/zwierze_potwierdz")
        public String confirmZwierze(Zwierze zwierze) {
            if (zwierzeDAO.get(zwierze.getIdZwierzecia()) == null) {
                zwierzeDAO.save(zwierze);
            } else {
                zwierzeDAO.update(zwierze);
            }
            return schronisko_powrot(klatkaDAO.get(zwierze.getIdKlatki()).getIdSchroniska());
        }

        @RequestMapping("/user_management/pracownik/{schr}/{id}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_PRACOWNIK'))")
        public String showPracownik(@PathVariable("schr") int schr, @PathVariable("id") int id, Model model) {
            Pracownik pracownik = pracownikDAO.get(id);
            if(pracownik.getIdSchroniska() != schr) {
                return "redirect:/error";
            }
            model.addAttribute("pracownik", pracownik);
            return "user_management/pracownik";
        }
        @RequestMapping("/user_management/pracownik_potwierdz")
        public String confirmPracownik(Pracownik pracownik) {
            pracownikDAO.update(pracownik);
            return schronisko_powrot(pracownik.getIdSchroniska());
        }

        @RequestMapping("/main_admin")
        public String showAdminPage(Model model) {
            List<Schronisko> schroniska = schroniskoDAO.list();
            List<Klient> klienci = klientDAO.list();
            List<Uzytkownik> uzytkownicy = uzytkownikDAO.list();

            model.addAttribute("schroniska", schroniska);
            model.addAttribute("klienci", klienci);
            model.addAttribute("uzytkownicy", uzytkownicy);
            return "admin/main_admin";
        }
        @RequestMapping("/admin/schronisko_admin/{id}")
        public String showSchronisko(@PathVariable("id") int id, Model model) {
            Schronisko schronisko = schroniskoDAO.get(id); // Pobierz schronisko po ID
            if (schronisko == null) {
                return "error"; // Obsługa przypadku, gdy schronisko nie istnieje
            }

            List<Pracownik> pracownicy = pracownikDAO.listBySchroniskoId(id);
            List<Klatka> klatki = klatkaDAO.listBySchroniskoId(id);
            List<Zwierze> zwierzeta = zwierzeDAO.listBySchroniskoId(id);

            model.addAttribute("schronisko", schronisko);
            model.addAttribute("pracownicy", pracownicy);
            model.addAttribute("klatki", klatki);
            model.addAttribute("zwierzeta", zwierzeta);

            return "admin/schronisko_admin"; // Zwróć widok schronisko_admin.html
        }

        @RequestMapping("/admin/addSchronisko")
        public String addSchronisko(Schronisko schronisko, Model model) {
            schroniskoDAO.save(schronisko);
            return showAdminPage(model);
        }

        @RequestMapping("/admin/removeSchronisko")
        public String removeSchronisko(int id, Model model) {
            schroniskoDAO.delete(id);
            return showAdminPage(model);
        }

        @RequestMapping("/user_management/addPracownik")
        public String addPracownik(Pracownik pracownik, Model model) {
            pracownikDAO.save(pracownik);
            return showSchronisko(pracownik.getIdSchroniska(), model);
        }
        @RequestMapping("/user_management/removePracownik/{schr}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_PRACOWNIK'))")
        public String removePracownik(@PathVariable("schr") int schr, int id, Model model) {
            if (schr != pracownikDAO.get(id).getIdSchroniska()) {
                return "redirect:/error";
            }
            pracownikDAO.delete(id);
            return schronisko_powrot(schr);
        }

        @RequestMapping("/schronisko_management/addZwierze")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_ZWIERZE'))")
        public String addZwierze(Zwierze zwierze, Model model) {
            System.out.println("Saving " + zwierze);
            zwierzeDAO.save(zwierze);
            return showSchronisko(klatkaDAO.get(zwierze.getIdKlatki()).getIdSchroniska(), model);
        }
        @RequestMapping("/schronisko_management/removeZwierze/{schr}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_ZWIERZE'))")
        public String removeZwierze(@PathVariable("schr") int schr, int id, Model model) {
            if(schr != klatkaDAO.get(zwierzeDAO.get(id).getIdKlatki()).getIdSchroniska()) {
                return "redirect:/error";
            }
            zwierzeDAO.delete(id);
            return schronisko_powrot(schr);
        }

        @RequestMapping("/schronisko_management/addKlatka")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_KLATKA'))")
        public String addKlatka(Klatka klatka, Model model) {
            System.out.println("Saving " + klatka);
            klatkaDAO.save(klatka);
            return showSchronisko(klatka.getIdSchroniska(), model);
        }

        @RequestMapping("/schronisko_management/removeKlatka/{schr}")
        @PreAuthorize("hasRole('ADMIN') or (authentication.principal.idSchroniska == #schr and hasRole('ZARZADZANIE_KLATKA'))")
        public String removeKlatka(@PathVariable("schr") int schr, int id, Model model) {
            if (schr != klatkaDAO.get(id).getIdSchroniska()) {
                return "redirect:/error";
            }
            klatkaDAO.delete(id);
            return schronisko_powrot(schr);
        }
    }
}
