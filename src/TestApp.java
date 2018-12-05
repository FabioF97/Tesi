import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class TestApp {
	
	public static void main(String[] args) throws IOException, ResourceException, ParseException {
		CSVReader csv = new CSVReader();
		List<ChiefTown> list = csv.readCSV(csv.getPath());
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int n = 0;
		for(ChiefTown t: list) {
			map.put(t.getName(), n);
			++n;
		}
		
		Coordinates str = new Coordinates();
		str.addCoordinates(list, map);
		
		//System.out.println("Quale è la linea di Forlì?");
		//System.out.println(map.get("Forlì"));
		
		JSONClientResource JSON = new JSONClientResource();
		JSONObject query = JSON.prepareJSON(list);
		
		//JSON.ask(query);
	}

}
