package br.com.motosecurityx.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";                 // templates/home.html
    }

    @GetMapping("/motos")
    public String motos() {
        return "motos/list";           // templates/motos/list.html
    }

    @GetMapping("/patios")
    public String patios() {
        return "patios/list";          // templates/patios/list.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";                // templates/login.html
    }
}
