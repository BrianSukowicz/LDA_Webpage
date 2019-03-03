package search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.io.FileUtils;
import java.net.URL;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;


//Some of these imports are no longer necessary
@Controller
public class TestController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="search", required=false, defaultValue="") String search, Model model) {
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
	   // String[] values = hm.values().toArray(new String[hm.values().size()]);
        boolean containsParameter;
	ArrayList<JSONObject> toAdd = new ArrayList<JSONObject>();
	for(int i=0; i<people.length; i++) {
	    containsParameter = false;		
	    //String[] vals = people[i].values().toArray(new String[people[i].values().size()]);
	    Collection<String> vals = people[i].values();
	    for(String s: vals){
		    if(s.contains(search)){
			    containsParameter = true;
		    }
	    }
	    if(containsParameter){
		    toAdd.add(people[i]);
	    }
	}

//	String prettyOutput;
	Set keys = person1.keySet();
//
//	while(keys.hasNext()) {
//		String key = keys.next();
//		if (jsonObject.get(key) instanceof JSONObject) {
//			// do something with jsonObject here
//		}
//	}


//        String[] arrayOfAttributes = {"p1","p2", "p3", "p4", "p5"};
//	for (int i=0; i<toAdd.size(); i++){
		model.addAttribute("results", keys);

//	}
		//model.addAttribute("p1", toAdd);
	model.addAttribute("numberOfResults", toAdd.size());
	return "greeting";
    }

}
