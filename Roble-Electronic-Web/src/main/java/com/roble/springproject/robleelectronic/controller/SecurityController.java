package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SecurityController {

    @Autowired
    //private UserDetailsServiceImple userDetailsServiceImple;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("registring")
    public String regitraionForm(Model model){
        model.addAttribute("user", new User());
        return "security/registrationForm";
    }

    @PostMapping("/registring")
    public String submitRegistration(User user, Model model){
        //userDetailsServiceImple.register(user);
        return "redirect:/login";
    }
}
