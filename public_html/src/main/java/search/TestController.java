package search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, 
		    @RequestParam(name="age", required=false, defaultValue="30") String age, Model model) {
        if(name.contains("b")){
		name = "Banana";
	}
	model.addAttribute("name", name);
	model.addAttribute("age", age);
        return "greeting";
    }

}
