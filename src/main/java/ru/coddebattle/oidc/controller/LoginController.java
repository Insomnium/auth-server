package ru.coddebattle.oidc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Igor_Petrov@epam.com
 * Created at 1/15/2019
 */
@Controller
public class LoginController {

    @GetMapping("/login/oauth2")
    public String login() {
        return "login.html";
    }
}
