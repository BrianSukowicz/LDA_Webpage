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
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, 
		    @RequestParam(name="age", required=false, defaultValue="30") String age, Model model) {
	JSONObject person1 = new JSONObject();
	JSONObject person2 = new JSONObject();
	person1.put("Name", "Amanda");
	person2.put("Name", "Jake");
	JSONObject[] people = {person1, person2};
	   // String[] values = hm.values().toArray(new String[hm.values().size()]);
        boolean containsParameter;
	ArrayList<JSONObject> toAdd = new ArrayList<JSONObject>();
	for(int i=0; i<people.length; i++) {
	    containsParameter = false;		
	    //String[] vals = people[i].values().toArray(new String[people[i].values().size()]);
	    Collection<String> vals = people[i].values();
	    for(String s: vals){
		    if(s.contains(name)){
			    containsParameter = true;
		    }
	    }
	    if(containsParameter){
		    toAdd.add(people[i]);
	    }
	}
	    

	model.addAttribute("name", toAdd);
	model.addAttribute("age", age);
        return "greeting";
    }

}
