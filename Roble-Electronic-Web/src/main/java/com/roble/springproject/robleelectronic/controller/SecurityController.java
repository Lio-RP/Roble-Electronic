package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.auth.UserDetailsServiceImple;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SecurityController {

    public static final String SECURITY_REGISTRATION_FORM = "security/registrationForm";

    private final UserDetailsServiceImple userDetailsServiceImple;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public SecurityController(UserDetailsServiceImple userDetailsServiceImple,
                              UserService userService,
                              UserRepository userRepository,
                              CategoryService categoryService) {
        this.userDetailsServiceImple = userDetailsServiceImple;
        this.userService = userService;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
    }

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }

    @RequestMapping("/registering")
    public String RegistrationForm(Model model){
        model.addAttribute("user", new User());
        return SECURITY_REGISTRATION_FORM;
    }

    @PostMapping("/registering")
    public String submitRegistration(@Valid @ModelAttribute("user") User user,
                                     BindingResult result,
                                     Model model){
        if(result.hasErrors()){
            return SECURITY_REGISTRATION_FORM;
        }

        System.out.println("saving user");
        User savedUser = userService.register(user);
        System.out.println(savedUser);
        System.out.println("user saved");
        return "security/register_success";

        /*String username = userDetailsServiceImple.loadUserByUsername(user.getUserName()).getUsername();
        boolean exists = false;

        if(!user.getUserName().equals(username) && !userService.emailExists(user.getEmail())){

            System.out.println("saving user");
            User savedUser = userService.register(user);
            System.out.println(savedUser);
            System.out.println("user saved");
            return "security/register_success";


        }else if(userService.emailExists(user.getEmail())){
            System.out.println(userService.emailExists(user.getEmail()));
            model.addAttribute("exists", true);
            model.addAttribute("errorMessage", "The email already exists. Please choose another email");
            return SECURITY_REGISTRATION_FORM;
        }

        model.addAttribute("exists", true);
        model.addAttribute("errorMessage", "The userName already exists. Please choose another userName");
        return SECURITY_REGISTRATION_FORM;*/
    }
}
