package bada_project_schronisk_KABM.SpringApp;

import bada_project_schronisk_KABM.SpringApp.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppController implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
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
                return "redirect:/index";
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
        @RequestMapping("/main_user")
        public String showUserPage(Model model) {
            return "user/main_user";
        }

        @RequestMapping("/index")
        public String showIndexPage(Model model) {
            List<Zwierze> zwierzeta = zwierzeDAO.list();
            System.out.println(zwierzeta);
            model.addAttribute("zwierzeta", zwierzeta);
            return "/index";
        }

        @RequestMapping("/admin/addPracownik")
        public String addPracownik(Pracownik pracownik, Model model) {
            pracownikDAO.save(pracownik);
            return showAdminPage(model);
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

        @RequestMapping("/admin/addZwierze")
        public String addZwierze(Zwierze zwierze, Model model) {
            System.out.println("Saving " + zwierze);
            zwierzeDAO.save(zwierze);
            return showAdminPage(model);
        }
    }
}
