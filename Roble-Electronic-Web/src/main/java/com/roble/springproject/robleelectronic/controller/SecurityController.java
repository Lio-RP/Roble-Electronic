package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.auth.UserDetailsServiceImple;
import com.roble.springproject.RobleElectronic.auth.UserRepository;
import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.services.CategoryService;
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
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public SecurityController(UserDetailsServiceImple userDetailsServiceImple,
                              UserRepository userRepository,
                              CategoryService categoryService) {
        this.userDetailsServiceImple = userDetailsServiceImple;
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
        }else{

            String username = userDetailsServiceImple.loadUserByUsername(user.getUserName()).getUsername();
            User foundedUser = userDetailsServiceImple.findByEmail(user.getEmail());
            System.out.println(foundedUser);
            boolean exists = false;

            if(user.getUserName().equals(username)){

                model.addAttribute("exists", true);
                model.addAttribute("errorMessage", "The userName already exists. Please choose another userName");
                return SECURITY_REGISTRATION_FORM;

            }else if (user.getEmail().equals(foundedUser.getEmail())){

                model.addAttribute("exists", true);
                model.addAttribute("errorMessage", "The Email already exists. Please choose another email");
                return SECURITY_REGISTRATION_FORM;

            }else{

                System.out.println("saving user");
                User savedUser = userDetailsServiceImple.register(user);
                System.out.println(savedUser);
                System.out.println("user saved");
                return "security/register_success";

            }
        }
    }
}
