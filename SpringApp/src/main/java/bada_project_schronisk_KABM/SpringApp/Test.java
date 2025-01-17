package bada_project_schronisk_KABM.SpringApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/")
    public String asgklj() {
        return "asdasd";
    }
}
