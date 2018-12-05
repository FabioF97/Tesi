import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class Matrix {
	
	private MatrixObject[][] matrix;
	List<ChiefTown> list;
	HashMap<String, Integer> mapIndex;
	HashMap<Integer, ChiefTown> mapTown;
	
	public Matrix(int dim, List<ChiefTown> list, HashMap<String, Integer> mapIndex, HashMap<Integer, ChiefTown> mapTown) {
		this.matrix = new MatrixObject[dim][dim];	
		this.list = list;
		this.mapIndex = mapIndex;
		this.mapTown = mapTown;
	}
	
	public void insert(MatrixObject obj, int i, int j) {
		matrix[i][j] = obj;
	}
	
	public MatrixObject get(int i, int j) {
		return matrix[i][j];
	}
	
	public MatrixObject[][] getMatrix(){
		return matrix;
	}
	
	
	public void fillRow(int i) throws ResourceException, IOException, ParseException {
		JSONBuilder builder = new JSONBuilder(list);
		JSONClientResource client = new JSONClientResource();
		
		for(ChiefTown t: list) {
			List<JSONObject> objs = builder.prepareJSON(i); //Per ogni città si preparano i JSONObject necessari
			for(int j = 0; j< objs.size(); ++j) {
				JSONObject ret = client.ask(objs.get(j));
				if(builder.isGood(ret)) {
					JSONArray distance = (JSONArray) ret.get("distance");
					JSONArray time = (JSONArray) ret.get("time");
					for(int k = 0; k < distance.size(); ++k) {
						MatrixObject ris = new MatrixObject(mapTown.get(i), mapTown.get(j),1,1);
					}
				}
			}
		}
	}
	
}
