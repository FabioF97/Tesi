import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Coordinates {

	private final String LINK = "https://raw.githubusercontent.com/MatteoHenryChinaski/Comuni-Italiani-2018-Sql-Json-excel/master/italy_geo.json";
	
	private String path;
	
	/**
	 * Salva nella cartella il file json contente in ogni oggetto nome del comune  e coordinate
	 * @throws IOException
	 */
	public Coordinates() throws IOException {
		path = Paths.get(".").toAbsolutePath().toString();
		path = path.substring(0, path.length()-1) + "file.json";
		URL url = new URL(LINK);
		File file = new File(path);
		FileUtils.copyURLToFile(url, file);
	}
	
	public String getPath() {
		return path;
	}
	
	//per qualche strana ragione non riesce a calcolare la distanza da agrigento a l'aquila, ma il contrario sì
	//stessa cosa per andare a pistoia e roma ma se si mettono i nomi invece che le coordinate va tutto bene
	public void addCoordinates(List<ChiefTown> list, HashMap<String,Integer> map) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		//int n = 0;
		JSONArray array = (JSONArray) parser.parse(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		for(Object o: array) {
			JSONObject chiefTown = (JSONObject) o;
			String name = (String) chiefTown.get("comune");
			if(map.get(name) == null)
				continue;
			else {
			//	++n;
				String lat = (String) chiefTown.get("lat");
				String lon = (String) chiefTown.get("lng");
				ChiefTown town = list.get(map.get(name));
				town.setCoordinates(lat, lon);
				//System.out.println(n + ") " + town.getName() + " ha coordinate " + town.getCoordinates());
			}
		}
	}
	
}
