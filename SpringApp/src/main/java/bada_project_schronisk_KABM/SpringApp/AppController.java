package bada_project_schronisk_KABM.SpringApp;

import bada_project_schronisk_KABM.SpringApp.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    }

    @Controller
    public class DashboardController {
        @RequestMapping("/main")
        public String defaultAfterLogin(HttpServletRequest request) {
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/main_admin";
            } else if (request.isUserInRole("USER")) {
                return "redirect:/main_user";
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
            return "user/main_user";
        }

        @RequestMapping(value={"index", "/"})
        public String showIndexPage(Model model) {
            List<Zwierze> zwierzeta = zwierzeDAO.list();
            System.out.println(zwierzeta);
            model.addAttribute("zwierzeta", zwierzeta);
            return "index";
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

            redirectAttributes.addFlashAttribute("successMessage", "Rejestracja przebiegła pomyślnie. Możesz się zalogować.");
            return "redirect:/login";
        }
        @RequestMapping("/main_admin")
        public String showAdminPage(Model model) {
            List<Schronisko> schroniska = schroniskoDAO.list();
            List<Pracownik> pracownicy = pracownikDAO.list();
            List<Klatka> klatki = klatkaDAO.list();
            List<Zwierze> zwierzeta = zwierzeDAO.list();
            List<Klient> klienci = klientDAO.list();
            System.out.println(zwierzeta);

            model.addAttribute("schroniska", schroniska);
            model.addAttribute("pracownicy", pracownicy);
            model.addAttribute("klatki", klatki);
            model.addAttribute("zwierzeta", zwierzeta);
            model.addAttribute("klienci", klienci);
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

        @RequestMapping("/admin/addPracownik")
        public String addPracownik(Pracownik pracownik, Model model) {
            pracownikDAO.save(pracownik);
            return showSchronisko(pracownik.getIdSchroniska(), model);
        }
        @RequestMapping("/admin/removePracownik")
        public String removePracownik(int id, Model model) {
            int schr = pracownikDAO.get(id).getIdSchroniska();
            pracownikDAO.delete(id);
            return showSchronisko(schr, model);
        }

        @RequestMapping("/admin/addZwierze")
        public String addZwierze(Zwierze zwierze, Model model) {
            System.out.println("Saving " + zwierze);
            zwierzeDAO.save(zwierze);
            return showSchronisko(klatkaDAO.get(zwierze.getIdKlatki()).getIdSchroniska(), model);
        }
        @RequestMapping("/admin/removeZwierze")
        public String removeZwierze(int id, Model model) {
            int schr = klatkaDAO.get(zwierzeDAO.get(id).getIdKlatki()).getIdSchroniska();
            zwierzeDAO.delete(id);
            return showSchronisko(schr, model);
        }

        @RequestMapping("/admin/addKlatka")
        public String addKlatka(Klatka klatka, Model model) {
            System.out.println("Saving " + klatka);
            klatkaDAO.save(klatka);
            return showSchronisko(klatka.getIdSchroniska(), model);
        }

        @RequestMapping("/admin/removeKlatka")
        public String removeKlatka(int id, Model model) {
            int schr = klatkaDAO.get(id).getIdSchroniska();
            klatkaDAO.delete(id);
            return showSchronisko(schr, model);
        }
    }
}
