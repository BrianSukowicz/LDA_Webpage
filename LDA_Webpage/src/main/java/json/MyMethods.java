package json;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class MyMethods {

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
