package json;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Controller
public class ChangeLoginController {

    @GetMapping("/changeLogin")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        return "changeLogin";
    }

    @GetMapping("/changeLoginAdmin")
    public String greetingFormAdmin(Model model) {
        model.addAttribute("admin", new Admin());
        return "changeLoginAdmin";
    }

    @PostMapping("/changeLogin")
    public String greetingSubmit(@ModelAttribute User user) throws IOException, Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String[] credentials = new String[4];
        Path file = Paths.get("LDA_Webpage\\src\\main\\java\\json\\credentials.txt");
        credentials = Files.readAllLines(file).toArray(credentials);
        credentials[0] = user.getId();
        credentials[1] = (passwordEncoder.encode(user.getPassword()));
        Files.write(file, Arrays.asList(credentials));

        return "result";
    }

    @PostMapping("/changeLoginAdmin")
    public String greetingSubmitAdmin(@ModelAttribute Admin admin) throws IOException, Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String[] credentials = new String[4];
        Path file = Paths.get("LDA_Webpage\\src\\main\\java\\json\\credentials.txt");
        credentials = Files.readAllLines(file).toArray(credentials);
        credentials[2] = admin.getId();
        credentials[3] = (passwordEncoder.encode(admin.getPassword()));
        Files.write(file, Arrays.asList(credentials));

        return "result";
    }

}

