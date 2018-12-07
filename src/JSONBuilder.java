import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONBuilder {
	
	private List<ChiefTown> list;
	
	public JSONBuilder(List<ChiefTown> list) {
		this.list = list;
	}
	
	/**
	 * Questo metodo accetta due stringhe (due città o due coppie di coordinate)
	 * e ritorna un file json che permetterà di andare a fare il routing tra due città
	 * @param src
	 * @param dst
	 * @return
	 */
	public JSONObject simpleJSON(int src, int dst) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		array.add(list.get(src).getCoordinates());
		array.add(list.get(dst).getCoordinates());
		obj2.put("allToAll", false);
		obj2.put("manyToOne", false);
		obj2.put("oneToMany", true);
		obj.put("locations", array);
		obj.put("options", obj2);
		return obj;
	}
	
	/**
	 * src è l'indice della riga che ora voglio riempire
	 * @param src
	 * @return
	 */
	public List<JSONObject> prepareJSON(int src) {
		int index = 0;
		List<JSONObject> objs = new ArrayList<JSONObject>();
		
		while(index < list.size()) {
			JSONArray array = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			ChiefTown town = list.get(src); //metto nel jsonobject come primo elemento sempre la città che identifica la riga da riempire
			array.add(town.getCoordinates());
			
			for(int i = index; i < index + 25; ++i) {
				if(i >= list.size())
					break;
				town = list.get(i);
				array.add(town.getCoordinates());
			}
	
			index += 25; //incremento index di 25 così la prossima città sara quella dopo l'ultima inserita in lista
			
			obj2.put("allToAll", false);
			obj2.put("manyToOne", false);
			obj2.put("oneToMany", true);
			obj.put("locations", array);
			obj.put("options", obj2);
			objs.add(obj);
		}

		return objs;
	}
	

}
