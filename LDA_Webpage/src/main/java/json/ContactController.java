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
	String pathName = "C:\\Users\\brian\\IdeaProjects\\Test\\src\\main\\java\\LDARoster.xlsx";
//	JSONObject[] people = {person1, person2, person3};
	MyMethods methodCaller = new MyMethods();
	JSONParser jsonParser = new JSONParser();
	String[] headers = {"Last Name", "First Name", "Maiden Name", "Date of Consecration", "Address Indicator",
			"Address 1", "Address 2", "City", "State", "Zip", "Country", "Primary Phone", "Email"};

	JSONObject[] people = jsonParser.JSONFromExcel(headers, pathName);
	ArrayList<JSONObject> contactInfoToDisplay = methodCaller.getJSONObjectsMatchingKeyword(search, people);
	String formattedContactInfoToDisplay = methodCaller.getFormattedHTML(headers, contactInfoToDisplay, search);


	model.addAttribute("results", formattedContactInfoToDisplay);
	model.addAttribute("numberOfResults", contactInfoToDisplay.size());
	return "contacts";
    }

}
