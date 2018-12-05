import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONBuilder {
	
	List<ChiefTown> list;
	
	public JSONBuilder(List<ChiefTown> list) {
		this.list = list;
	}
	
	public boolean isGood(JSONObject obj) {
		JSONArray array = (JSONArray) obj.get("distance");
		for (int i = 1; i < array.size(); i++) {
			  String value = (String) array.get(i).toString();
			  if(value.equals("0"))
				  return false;
		}
		return true;
	}
	

	/**
	 * Questo metodo accetta due stringhe (due città o due coppie di coordinate)
	 * e ritorna un file json che permetterà di andare a fare il routing tra due città
	 * @param src
	 * @param dst
	 * @return
	 */
	public static JSONObject simpleJSON(String src, String dst) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		array.add(src);
		array.add(dst);
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
		int index = src + 1;
		List<JSONObject> objs = new ArrayList<JSONObject>();
		
		while(index < list.size()) {
			JSONArray array = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			ChiefTown town = list.get(src); //metto nel jsonobject come primo elemento sempre la città che identifica la riga da riempire
			array.add(town.getName());
			
			for(int i = index; i < index + 25; ++i) {
				if(i >= list.size())
					break;
				town = list.get(i);
				array.add(town.getName());
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
