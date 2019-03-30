import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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
	
	public ChiefTown getTown(int i) {
		return mapTown.get(i);
	}
	
	public List<ChiefTown> getList(){
		return list;
	}
	
	
	public HashMap<String, Integer> getMapIndex(){
		return mapIndex;
	}
	
	
	public HashMap<Integer, ChiefTown> getMapTown(){
		return mapTown;
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
			JSONObject ret = client.ask(objs.get(j));

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
	
	public void printNearestDistance() {
		MatrixObject[] array = new MatrixObject[5];
		for(int i = 0; i < 109; ++i) {
			for(int j = 0; j < i; ++j) {
				if(i != j) {
					if(array[0] == null) {
						array[0] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() < array[0].getDistance()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = array[0];
							array[0] = matrix[i][j];
							continue;
						}
					}
					if(array[1] == null) {
						array[1] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() < array[1].getDistance()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = matrix[i][j];
							continue;
						}
					}
					if(array[2] == null) {
						array[2] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() < array[2].getDistance()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = matrix[i][j];
							continue;
						}
					}
					if(array[3] == null) {
						array[3] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() < array[3].getDistance()) {
							array[4] = array[3];
							array[3] = matrix[i][j];
							continue;
						}
					}
					if(array[4] == null) {
						array[4] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() < array[4].getDistance()) {
							array[4] = matrix[i][j];
							continue;
						}
					}
				}
			}
		}
		int k = 1;
		for(MatrixObject obj: array) {
			System.out.println(k++ + ") " + obj);
		}
	}
	
	public void printNearestTime() {
		MatrixObject[] array = new MatrixObject[5];
		for(int i = 0; i < 109; ++i) {
			for(int j = 0; j < i; ++j) {
				if(i != j) {
					if(array[0] == null) {
						array[0] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() < array[0].getTime()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = array[0];
							array[0] = matrix[i][j];
							continue;
						}
					}
					if(array[1] == null) {
						array[1] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() < array[1].getTime()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = matrix[i][j];
							continue;
						}
					}
					if(array[2] == null) {
						array[2] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() < array[2].getTime()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = matrix[i][j];
							continue;
						}
					}
					if(array[3] == null) {
						array[3] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() < array[3].getTime()) {
							array[4] = array[3];
							array[3] = matrix[i][j];
							continue;
						}
					}
					if(array[4] == null) {
						array[4] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() < array[4].getTime()) {
							array[4] = matrix[i][j];
							continue;
						}
					}
				}
			}
		}
		int k = 1;
		for(MatrixObject obj: array) {
			System.out.println(k++ + ") " + obj);
		}
	}
	
	public void printLowestAvgSpeed() {
		MatrixObject[] array = new MatrixObject[5];
		for(int i = 0; i < 109; ++i) {
			for(int j = 0; j < i; ++j) {
				if(getTown(i).getRegion().equals("Sardegna")) {
					if(!getTown(j).getRegion().equals("Sardegna")) {
						continue;
					}
				}
				if(getTown(i).getRegion().equals("Sicilia")) {
					if(!getTown(j).getRegion().equals("Sicilia")) {
						continue;
					}
				}
				if(getTown(j).getRegion().equals("Sardegna")) {
					if(!getTown(i).getRegion().equals("Sardegna")) {
						continue;
					}
				}
				if(getTown(j).getRegion().equals("Sicilia")) {
					if(!getTown(i).getRegion().equals("Sicilia")) {
						continue;
					}
				}
				if(i != j) {
					if(array[0] == null) {
						array[0] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() < array[0].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = array[0];
							array[0] = matrix[i][j];
							continue;
						}
					}
					if(array[1] == null) {
						array[1] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() < array[1].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = matrix[i][j];
							continue;
						}
					}
					if(array[2] == null) {
						array[2] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() < array[2].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = matrix[i][j];
							continue;
						}
					}
					if(array[3] == null) {
						array[3] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() < array[3].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = matrix[i][j];
							continue;
						}
					}
					if(array[4] == null) {
						array[4] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() < array[4].getAvgSpeed()) {
							array[4] = matrix[i][j];
							continue;
						}
					}
				}
			}
		}
		int k = 1;
		for(MatrixObject obj: array) {
			System.out.println(k++ + ") " + obj);
		}
	}
	
	public void printFurthestDistance() {
		MatrixObject[] array = new MatrixObject[5];
		for(int i = 0; i < 109; ++i) {
			if(getTown(i).getRegion().equals("Sardegna") || getTown(i).getRegion().equals("Sicilia"))
				continue;
			for(int j = 0; j < i; ++j) {
				if(getTown(j).getRegion().equals("Sardegna") || getTown(j).getRegion().equals("Sicilia"))
					continue;
				if(i != j) {
					if(array[0] == null) {
						array[0] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() > array[0].getDistance()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = array[0];
							array[0] = matrix[i][j];
							continue;
						}
					}
					if(array[1] == null) {
						array[1] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() > array[1].getDistance()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = matrix[i][j];
							continue;
						}
					}
					if(array[2] == null) {
						array[2] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() > array[2].getDistance()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = matrix[i][j];
							continue;
						}
					}
					if(array[3] == null) {
						array[3] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() > array[3].getDistance()) {
							array[4] = array[3];
							array[3] = matrix[i][j];
							continue;
						}
					}
					if(array[4] == null) {
						array[4] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getDistance() > array[4].getDistance()) {
							array[4] = matrix[i][j];
							continue;
						}
					}
				}
			}
		}
		int k = 1;
		for(MatrixObject obj: array) {
			System.out.println(k++ + ") " + obj);
		}
	}
	public void printHighestAvgSpeed() {
		MatrixObject[] array = new MatrixObject[5];
		for(int i = 0; i < 109; ++i) {
			if(getTown(i).getRegion().equals("Sardegna") || getTown(i).getRegion().equals("Sicilia"))
				continue;
			for(int j = 0; j < i; ++j) {
				if(getTown(j).getRegion().equals("Sardegna") || getTown(j).getRegion().equals("Sicilia"))
					continue;
				if(i != j) {
					if(array[0] == null) {
						array[0] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() > array[0].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = array[0];
							array[0] = matrix[i][j];
							continue;
						}
					}
					if(array[1] == null) {
						array[1] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() > array[1].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = matrix[i][j];
							continue;
						}
					}
					if(array[2] == null) {
						array[2] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() > array[2].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = matrix[i][j];
							continue;
						}
					}
					if(array[3] == null) {
						array[3] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() > array[3].getAvgSpeed()) {
							array[4] = array[3];
							array[3] = matrix[i][j];
							continue;
						}
					}
					if(array[4] == null) {
						array[4] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getAvgSpeed() > array[4].getAvgSpeed()) {
							array[4] = matrix[i][j];
							continue;
						}
					}
				}
			}
		}
		int k = 1;
		for(MatrixObject obj: array) {
			System.out.println(k++ + ") " + obj);
		}
	}
	
	public void printFurthestTime() {
		MatrixObject[] array = new MatrixObject[5];
		for(int i = 0; i < 109; ++i) {
			if(getTown(i).getRegion().equals("Sardegna") || getTown(i).getRegion().equals("Sicilia"))
				continue;
			for(int j = 0; j < i; ++j) {
				if(getTown(j).getRegion().equals("Sardegna") || getTown(j).getRegion().equals("Sicilia"))
					continue;
				if(i != j) {
					if(array[0] == null) {
						array[0] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() > array[0].getTime()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = array[0];
							array[0] = matrix[i][j];
							continue;
						}
					}
					if(array[1] == null) {
						array[1] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() > array[1].getTime()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = array[1];
							array[1] = matrix[i][j];
							continue;
						}
					}
					if(array[2] == null) {
						array[2] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() > array[2].getTime()) {
							array[4] = array[3];
							array[3] = array[2];
							array[2] = matrix[i][j];
							continue;
						}
					}
					if(array[3] == null) {
						array[3] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() > array[3].getTime()) {
							array[4] = array[3];
							array[3] = matrix[i][j];
							continue;
						}
					}
					if(array[4] == null) {
						array[4] = matrix[i][j];
						continue;
					}
					else {
						if(matrix[i][j].getTime() > array[4].getTime()) {
							array[4] = matrix[i][j];
							continue;
						}
					}
				}
			}
		}
		int k = 1;
		for(MatrixObject obj: array) {
			System.out.println(k++ + ") " + obj);
		}
	}
	
	public void printRow(int i) {
		MatrixObject[] row = matrix[i];
		for(int j = 0; j < list.size(); ++j) {
			System.out.println(row[j]);
		}
	}
	
	public void printLowestAvgDistance() {
		double avgDistance = 0;
		double nTown = 109 - 1;
		String name = null;
		double best = 9000;
		for(int j = 0; j < 109; ++j) {
			for(int i = 0; i < 109; ++i) {
				avgDistance += matrix[i][j].getDistance();
			}
			avgDistance = avgDistance / nTown;
			if(avgDistance < best) {
				name = getTown(j).getName();
				best = avgDistance;
			}
		}
		System.out.println(name + " average distance = " + best);
	}
	
	public void printHighestAvgDistance() {
		double avgDistance = 0;
		double nTown = 109 - 1;
		String name = null;
		double best = 0;
		for(int j = 0; j < 109; ++j) {
			for(int i = 0; i < 109; ++i) {
				avgDistance += matrix[i][j].getDistance();
			}
			avgDistance = avgDistance / nTown;
			if(avgDistance > best) {
				name = getTown(j).getName();
				best = avgDistance;
			}
		}
		System.out.println(name + " average distance = " + best);
	}
	
	public void printLowestAvgTime() {
		double avgTime = 0;
		double nTown = 109 - 1;
		String name = null;
		double best = 9000;
		for(int j = 0; j < 109; ++j) {
			for(int i = 0; i < 109; ++i) {
				avgTime += matrix[i][j].getTime();
			}
			avgTime = avgTime / nTown;
			if(avgTime < best) {
				name = getTown(j).getName();
				best = avgTime;
			}
		}
		System.out.println(name + " average time = " + best);
	}
	
	public void printHighestAvgTime() {
		double avgTime = 0;
		double nTown = 109 - 1;
		String name = null;
		double best = 0;
		for(int j = 0; j < 109; ++j) {
			for(int i = 0; i < 109; ++i) {
				avgTime += matrix[i][j].getTime();
			}
			avgTime = avgTime / nTown;
			if(avgTime > best) {
				name = getTown(j).getName();
				best = avgTime;
			}
		}
		System.out.println(name + " average time = " + best);
	}
	
	public void printLowestSpeed() {
		double avgSpeed = 0;
		double nTown = 109 - 1;
		String name = null;
		double best = 9000;
		for(int j = 0; j < 109; ++j) {
			for(int i = 0; i < 109; ++i) {
				avgSpeed += matrix[i][j].getAvgSpeed();
			}
			avgSpeed = avgSpeed / nTown;
			if(avgSpeed < best) {
				name = getTown(j).getName();
				best = avgSpeed;
			}
		}
		System.out.println(name + " average speed = " + best);
	}
	
	public void printHighestSpeed() {
		double avgSpeed = 0;
		double nTown = 109 - 1;
		String name = null;
		double best = 0;
		for(int j = 0; j < 109; ++j) {
			for(int i = 0; i < 109; ++i) {
				avgSpeed += matrix[i][j].getAvgSpeed();
			}
			avgSpeed = avgSpeed / nTown;
			if(avgSpeed > best) {
				name = getTown(j).getName();
				best = avgSpeed;
			}
		}
		System.out.println(name + " average speed = " + best);
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
