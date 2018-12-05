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
	
	public JSONObject prepareJSON(List<ChiefTown> list) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		
		for(ChiefTown t: list) {
			array.add(t.getCoordinates());
		}
		
		/*
		for(ChiefTown t: list) {
			String name = t.getName();
			switch(name) {
			case "Cremona":
				array.add(name + ",IT,26100");
				break;
			case "Forlì":
				array.add(name + ",IT,47121");
				break;
			case "Genova":
				array.add("44.4070624,8.9339889");
				break;
			case "Grosseto":
				array.add(name + ",IT,58100");
				break;
			case "Lodi":
				array.add(name + ",IT,26900");
				break;
			case "Macerata":
				array.add(name + "IT,62100");
				break;
			// eventuali altri case
			default:
				array.add(name + ",IT");
			}
		}
		*/

		obj2.put("allToAll", false);
		obj2.put("manyToOne", false);
		obj2.put("oneToMany", true);
		obj.put("locations", array);
		obj.put("options", obj2);
		System.out.println(obj.toString());
		return obj;
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
	
	public void ask(JSONObject query) throws ResourceException, IOException, ParseException {
		String response = rs01.post(query.toString()).getText();
		//System.out.println(response);
		JSONObject jobj = (JSONObject) parser.parse(response);
		Matrix matrix = new Matrix(24);
		//matrix[0][1] = new MatrixObject(Double.parseDouble(((JSONArray)((JSONArray) jobj.get("distance")).get(0)).get(1).toString()), Double.parseDouble(((JSONArray)((JSONArray) jobj.get("time")).get(0)).get(1).toString()));
		for(int i = 0; i < 24; ++i) {
			for(int j = 0; j < 24; ++j) {
				matrix.insert(readJSON(i, j, jobj), i, j);
			}
		}
		print2D(matrix.getMatrix());
	}

}
