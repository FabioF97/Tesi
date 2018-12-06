import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class TestApp {
	
	public static void main(String[] args) throws IOException, ResourceException, ParseException {
		CSVReader csv = new CSVReader();
		List<ChiefTown> list = csv.readCSV(csv.getPath());
		HashMap<String, Integer> mapIndex = new HashMap<String, Integer>();
		HashMap<Integer, ChiefTown> mapTown = new HashMap<Integer, ChiefTown>();
		int n = 0;
		for(ChiefTown t: list) {
			mapIndex.put(t.getName(), n);
			++n;
		}
		
		Coordinates str = new Coordinates();
		str.addCoordinates(list, mapIndex);
		
		n = 0;
		for(ChiefTown t: list) {
			mapTown.put(n, t);
			++n;
		}
		
		Matrix matrix = new Matrix(list.size(), list, mapIndex, mapTown);
		//matrix.fillRow(0);
		matrix.print2D(matrix.getMatrix());
		System.out.println("Ritorno informazioni sulla tratta Agrigento -> Milano");
		System.out.println(matrix.print(mapIndex.get("Agrigento"), mapIndex.get("Milano")));
	}

}
