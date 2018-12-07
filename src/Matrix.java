import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class Matrix implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private MatrixObject[][] matrix;
	private List<ChiefTown> list;
	private HashMap<String, Integer> mapIndex;
	private HashMap<Integer, ChiefTown> mapTown;
	
	public Matrix(int dim, List<ChiefTown> list, HashMap<String, Integer> mapIndex, HashMap<Integer, ChiefTown> mapTown) {
		this.matrix = new MatrixObject[dim][dim];	
		this.list = list;
		this.mapIndex = mapIndex;
		this.mapTown = mapTown;
	}
	
	public MatrixObject get(int i, int j) {
		return matrix[i][j];
	}
	
	public String print(int i, int j) {
		return get(i,j).toString();
	}
	
	public MatrixObject[][] getMatrix(){
		return matrix;
	}
	
	public int getDim() {
		return matrix.length;
	}
	
	public void insert(MatrixObject obj, int i, int j) {
		matrix[i][j] = obj;
	}
	
	private MatrixObject buildObject(int i, int j, double distance, double time) {
		MatrixObject ret = new MatrixObject(mapTown.get(i), mapTown.get(j), distance, time);
		return ret;
	}
	
	
	public void fillMatrix() throws ResourceException, IOException, ParseException {
		for(int i = 0; i < getDim(); ++i) {
			fillRow(i);
		}
	}
	
	public void fillRow(int i) throws ResourceException, IOException, ParseException {
		JSONBuilder builder = new JSONBuilder(list);
		JSONClientResource client = new JSONClientResource();
		int r = 0;
		
		List<JSONObject> objs = builder.prepareJSON(i); //Si preparano i JSONObject necessari per la città
		
		for(int j = 0; j < objs.size(); ++j) {
			//System.out.println("Questo è quello che viene richiesto:");
			//System.out.println(objs.get(j).toString());
			JSONObject ret = client.ask(objs.get(j));
			//System.out.println("Questo è quello che risposto:");
			//System.out.println(ret.toString());
			
			//Leggo gli array con distanze e tempi
			JSONArray distance = (JSONArray) ret.get("distance");
			JSONArray time = (JSONArray) ret.get("time");
			
			for(int k = 1; k < distance.size(); ++k) {
				double miles = Double.parseDouble((String) distance.get(k).toString());
				double sec = Double.parseDouble((String) time.get(k).toString());
				//Se è riuscito a fare il routing ho trovato dati non nulli  oppure appartengono alla diagonale quindi li inserisco in matrice
				if(((miles != 0) && (sec != 0)) || (r == i)) {
					MatrixObject val = buildObject(i, r, miles, sec);
					insert(val, i, r);
					++r;
				}
				//Altrimenti vado a correggere l'errore nel routing facendo una nuova query
				else {
					JSONObject query = builder.simpleJSON(r, i);
					System.out.println("Va a correggere questo errore:");
					System.out.println(query.toString());
					JSONObject correction = client.ask(query);
					System.out.println("Risposta:");
					System.out.println(correction.toString());
					JSONArray tmpDist = (JSONArray) correction.get("distance");
					JSONArray tmpTime = (JSONArray) correction.get("time");
					
					miles = Double.parseDouble((String) tmpDist.get(1).toString());
					sec = Double.parseDouble((String) tmpTime.get(1).toString());
					
					if((miles != 0) && (sec != 0)) {
						MatrixObject val = buildObject(i, r, miles, sec);
						insert(val, i, r);
						++r;
					}
					else {
						System.out.println("ERROR ERROR!!!");
						++r;
					}
				}
			}
		}
	}
	
	public void print2D() {
		
		MatrixObject mat[][] = matrix;
        // Loop through all rows 
        for (MatrixObject[] row : mat) 
  
            // converting each row as string 
            // and then printing in a separate line 
            System.out.println(Arrays.toString(row)); 
    }
	
}
