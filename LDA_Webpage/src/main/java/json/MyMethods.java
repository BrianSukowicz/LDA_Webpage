package json;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class MyMethods {

    ArrayList<JSONObject> getJSONObjectsMatchingKeyword(String keyword, JSONObject [] arrayOfPeople){
        boolean containsParameter;
        ArrayList<JSONObject> matchingObjects = new ArrayList<JSONObject>();
        for(int i=0; i<arrayOfPeople.length; i++) {
            containsParameter = false;
            Collection<String> vals = arrayOfPeople[i].values();
            for(String s: vals){
                if(s.contains(keyword)){
                    containsParameter = true;
                }
            }
            if(containsParameter){
                matchingObjects.add(arrayOfPeople[i]);
            }
        }
        return matchingObjects;
    }

    String getFormattedHTML(String[] headers, ArrayList<JSONObject> jsonObjects, String keyword){
        String openingTag = "<mark class=\"found\">";
        String closingTag = "</mark>";
        StringBuffer jsonValue;
        int indexOfSearch;
        StringBuffer formattedHTML = new StringBuffer();
        for(JSONObject jsonPerson : jsonObjects) {
            for(String header: headers) {
                formattedHTML.append(header);
                formattedHTML.append(": ");
                jsonValue = new StringBuffer((String)jsonPerson.get(header));
                indexOfSearch = jsonValue.toString().toLowerCase().indexOf(keyword.toLowerCase());
                if (indexOfSearch > -1 & keyword.length() > 0) {
                    //jsonValue = jsonValue.toLowerCase().replace(search,openingTag + search + closingTag);
                    jsonValue.insert(indexOfSearch + keyword.length(), closingTag);
                    jsonValue.insert(indexOfSearch, openingTag);
                }
                formattedHTML.append(jsonValue); //if using the iteraetor, then replace header with key
                formattedHTML.append("<br>");
            }
            formattedHTML.append("<br>");
        }
        return formattedHTML.toString();
    }

	void createExcelWorkBook() {
	    OutputStream out = null;
            URLConnection conn = null;
            InputStream in = null;
            String address = "https://valpo0-my.sharepoint.com/personal/brian_vidal_valpo_edu/_layouts/15/download.aspx?share=EUJC8tSlo3tEpZTM7wfm9fgB7y_sTKStV2lSWL6FfTNZGQ";
            String destination = "./Book.xlsx"; //address for directory with filename

            try { //this function connects to the url and downloads the file
                URL source = new URL(address);
                out = new BufferedOutputStream(new FileOutputStream(destination));
                conn = source.openConnection();
                in = conn.getInputStream();
                byte[] buffer = new byte[1024];

	        int numRead;
                long numWritten = 0;

                while ((numRead = in.read(buffer)) != -1) { //Overwrites existing file
                    out.write(buffer, 0, numRead);
                    numWritten += numRead;
	        }

            }   
            catch (IOException exception) {
            }
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }
                catch (IOException IOE) {
                }    
            } 
        }
}	
