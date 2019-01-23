package ru.coddebattle.oidc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author Igor_Petrov@epam.com
 * Created at 1/16/2019
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String userProfile(@AuthenticationPrincipal Principal user, Model model) {
        model.addAttribute("user", user);
        return "profile.html";
    }
}
