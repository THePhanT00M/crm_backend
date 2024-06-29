package site.shcrm.shcrm_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP()
    {
        return "main";
    }
}
