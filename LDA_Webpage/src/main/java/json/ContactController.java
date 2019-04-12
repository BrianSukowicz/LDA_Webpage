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


//Some of these imports are no longer necessary
@Controller
public class ContactController {

    @GetMapping("/contacts")
    public String contacts(@RequestParam(name= "search", required=false, defaultValue="") String search, Model model) throws IOException, ParseException {
//	String pathName = "C:\\Users\\brian\\IdeaProjects\\Test\\src\\main\\java\\LDARoster.xlsx";
	MyMethods methodCaller = new MyMethods();
	String[] headers = {"Last Name", "First Name", "Maiden Name", "Date of Consecration", "Address Indicator",
			"Address 1", "Address 2", "City", "State", "Zip", "Country", "Primary Phone", "Email"};
	JSONParser parser = new JSONParser();
	Object parsedJsonObject = parser.parse(new FileReader("LDA_Webpage\\target\\roster.json"));
	JSONArray arrayOfJSONPeople = (JSONArray) parsedJsonObject;
	ArrayList<JSONObject> people = new ArrayList<JSONObject>();
		for (int i = 0; i < arrayOfJSONPeople.size(); i++) {
			people.add((JSONObject)arrayOfJSONPeople.get(i));
		}
	//JSONObject[] people = jsonConverter.JSONFromExcel(headers, pathName); //here we just read from future json file
	ArrayList<JSONObject> contactInfoToDisplay = methodCaller.getJSONObjectsMatchingKeyword(search, people);
	String formattedContactInfoToDisplay = methodCaller.getFormattedHTML(headers, contactInfoToDisplay, search);


	model.addAttribute("results", formattedContactInfoToDisplay);
	model.addAttribute("numberOfResults", contactInfoToDisplay.size());
	return "contacts";
    }

}
