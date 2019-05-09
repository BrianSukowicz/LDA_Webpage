package json;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


@Controller
public class ContactController {

    @GetMapping("/contacts")
    public String contacts(@RequestParam(name= "search", required=false, defaultValue="") String search,
						   @RequestParam(name="category", defaultValue = "Last Name") String category, Model model) throws IOException, ParseException {

	MyMethods methodCaller = new MyMethods();
	String[] headers = {"Last Name", "First Name", "Maiden Name", "Date of Consecration", "Address Indicator",
			"Address 1", "Address 2", "City", "State", "Zip", "Country", "Primary Phone", "Email"};
	JSONParser parser = new JSONParser();
	Object parsedJsonObject = parser.parse(new FileReader("roster.json"));
	JSONArray arrayOfJSONPeople = (JSONArray) parsedJsonObject;
	ArrayList<JSONObject> people = new ArrayList<JSONObject>();
		for (int i = 0; i < arrayOfJSONPeople.size(); i++) {
			people.add((JSONObject)arrayOfJSONPeople.get(i));
		}
	ArrayList<JSONObject> contactInfoToDisplay = methodCaller.getJSONObjectsMatchingKeyword(search, category, people);
	String formattedContactInfoToDisplay = methodCaller.getFormattedHTML(headers, contactInfoToDisplay, search, category);


	model.addAttribute("results", formattedContactInfoToDisplay);
	model.addAttribute("numberOfResults", contactInfoToDisplay.size());
	model.addAttribute("category", category);
	return "contacts";
    }

}
