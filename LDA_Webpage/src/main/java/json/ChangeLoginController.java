package json;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangeLoginController {

    @GetMapping("/changeLogin")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        return "changeLogin";
    }

    @PostMapping("/changeLogin")
    public String greetingSubmit(@ModelAttribute User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(user.getId()));
        System.out.println(passwordEncoder.encode(user.getPassword()));
        return "result";
    }

}

