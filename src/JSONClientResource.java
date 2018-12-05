import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class JSONClientResource {
	
    ClientResource rs01;
    JSONParser parser;
	
	public JSONClientResource() {
		rs01 = new ClientResource("http://www.mapquestapi.com/directions/v2/routematrix?key=fZMuFFGhqbQR6fwzuIyxo8shhxso0xtI");
		parser = new JSONParser();
	}
	
	public MatrixObject readJSON(int r, int c, JSONObject jobj) {
		MatrixObject ret;
		double distance, time;
		distance = Math.floor(Double.parseDouble(((JSONArray)((JSONArray) jobj.get("distance")).get(r)).get(c).toString()));
		time = Math.floor(Double.parseDouble(((JSONArray)((JSONArray) jobj.get("time")).get(r)).get(c).toString()));
		ret = new MatrixObject(null, null, Math.floor(distance), Math.floor(time));
		return ret;
	}
	
	public static void print2D(MatrixObject mat[][]) 
    { 
        // Loop through all rows 
        for (MatrixObject[] row : mat) 
  
            // converting each row as string 
            // and then printing in a separate line 
            System.out.println(Arrays.toString(row)); 
    }
	
	public JSONObject ask(JSONObject query) throws ResourceException, IOException, ParseException {
		String response = rs01.post(query.toString()).getText();
		JSONObject jobj = (JSONObject) parser.parse(response);
		return jobj;
	}

}
