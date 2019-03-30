package json;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.simple.JSONObject;

import java.util.*;


//Some of these imports are no longer necessary
@Controller
public class ContactController {

    @GetMapping("/contacts")
    public String contacts(@RequestParam(name= "search", required=false, defaultValue="") String search, Model model) {
	JSONObject person1 = new JSONObject();
	JSONObject person2 = new JSONObject();
	JSONObject person3 = new JSONObject();

	person1.put("First Name", "Amanda");
	person1.put("Last Name", "Clark");
	person1.put("Address", "123 South Street");

	person2.put("First Name", "Jake");
	person2.put("Last Name", "Jones");
	person2.put("Address", "245 North Street");

	person3.put("First Name", "Brian");
	person3.put("Last Name", "Bananas");
	person3.put("Address", "100 Rocky Road");

	JSONObject[] people = {person1, person2, person3};
	MyMethods methodCaller = new MyMethods();
	ArrayList<JSONObject> contactInfoToDisplay = methodCaller.getJSONObjectsMatchingKeyword(search, people);


	String[] headers = {"First Name", "Last Name", "Address"};
	String formattedContactInfoToDisplay = methodCaller.getFormattedHTML(headers, contactInfoToDisplay, search);


	model.addAttribute("results", formattedContactInfoToDisplay);
	model.addAttribute("numberOfResults", contactInfoToDisplay.size());
	return "contacts";
    }

}
