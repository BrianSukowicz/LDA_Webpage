package json;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
//upload an excel file; immediately convert to json. Store json file. read from json
public class JSONConverter {
    static XSSFRow row;
    JSONArray JSONFromExcel (String[] desiredHeaders, String pathName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("blank");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(pathName));
            try {
                workbook = new XSSFWorkbook(fis);
                spreadsheet = workbook.getSheetAt(0);
                fis.close();
            }
            catch (IOException e){
                System.out.println("IO Exception");
                System.exit(0);
            }

        }
        catch (FileNotFoundException e ){
            System.out.println("File not found Exception");
            System.exit(0);
        }
        DataFormatter formatter = new DataFormatter();

        Iterator < Row >  rowIterator = spreadsheet.iterator();
        row = (XSSFRow) rowIterator.next();
        Iterator < Cell >  cellIterator = row.cellIterator();

        ArrayList<String> allHeaders = new ArrayList<String>();
//        ArrayList<JSONObject> people = new ArrayList<JSONObject>();
        JSONArray people = new JSONArray();
        ArrayList<Integer> listOfIndeces = new ArrayList<Integer>();
        Cell cell;
        String str_cell;
        boolean containsFolderID = false;
        int indexOfFolderID = 0;
        int counter = 0;

        //get array that contains the indeces of the desired headers then add those each loop
        Set <String> setOfDesiredHeaders = new HashSet<String>(Arrays.asList(desiredHeaders));

        while ( cellIterator.hasNext()) {
            cell = cellIterator.next();
            str_cell = formatter.formatCellValue(cell);

            if (setOfDesiredHeaders.contains(str_cell)){
                listOfIndeces.add(counter);
            }
            else if(str_cell.equals("Folder ID")){
                containsFolderID = true;
                indexOfFolderID = counter;
            }

            allHeaders.add(str_cell);
            counter++;
        }

        ArrayList<String> usedIDs = new ArrayList<String>();

//keep track of the position of all the valid headers. Only add to json object these specific headers
        if(containsFolderID) {
            while (rowIterator.hasNext()) {
                JSONObject obj = new JSONObject();
                row = (XSSFRow) rowIterator.next();
                cell = row.getCell(indexOfFolderID);
                str_cell = formatter.formatCellValue(cell);
                if (!usedIDs.contains(str_cell)) {
                    usedIDs.add(str_cell);
                    for (int i : listOfIndeces) {
                        cell = row.getCell(i);
                        str_cell = formatter.formatCellValue(cell);
                        obj.put(allHeaders.get(i), str_cell);

                    }
                    people.add(obj);
//                    System.out.println(obj);
                }
            }
        }
        else {
            while (rowIterator.hasNext()) {
                JSONObject obj = new JSONObject();
                row = (XSSFRow) rowIterator.next();
                for (int i: listOfIndeces) {
                    cell = row.getCell(i);
                    str_cell = formatter.formatCellValue(cell);
                    obj.put(allHeaders.get(i), str_cell);
                }
                people.add(obj);
//                System.out.println(obj);
            }
        }
//        JSONObject[] arr = new JSONObject[people.size()];
//        arr = people.toArray(arr);
        return people;
    }
}

