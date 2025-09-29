package com.example.SMG_BBS.controller.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(HttpSession session, Model model) {

        String errorMessage = (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            String account = (String) session.getAttribute("account");
            model.addAttribute("account", account);

            session.removeAttribute("errorMessage");
            session.removeAttribute("account");
        }
        return "login";
    }

}
