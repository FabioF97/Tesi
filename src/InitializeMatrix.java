import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;

public class InitializeMatrix {

	public static void createMatrix() throws IOException, ParseException {
		
		//Scarica dal sito dell'istat un csv da cui seleziona i capoluoghi di provincia, ritorna una lista coi capoluoghi in ordine alfabetico
		CSVReader csv = new CSVReader();
		List<ChiefTown> list = csv.readCSV(csv.getPath());
		HashMap<String, Integer> mapIndex = new HashMap<String, Integer>();
		HashMap<Integer, ChiefTown> mapTown = new HashMap<Integer, ChiefTown>();
		int n = 0;
		
		//I capoluoghi vengono messi in due hash map che permettono di trovare indice di ciascun capoluogo e capoluogo dato l'indice 
		for(ChiefTown t: list) {
			mapIndex.put(t.getName(), n);
			++n;
		}
		
		//Si aggiungono a ogni capoluogo le sue coordinate
		Coordinates str = new Coordinates();
		str.addCoordinates(list, mapIndex);
		//Roma ha coordinate orrende, conviene cambiarle manualmente altrimenti non fa nessun routing
		//list.get(mapIndex.get("Roma")).setCoordinates("41.8919300", "12.5113300");
		
		n = 0;
		for(ChiefTown t: list) {
			mapTown.put(n, t);
			++n;
		}
		
		//Viene creata e riempita la matrice
		Matrix matrix = new Matrix(list.size(), list, mapIndex, mapTown);
		matrix.fillMatrix();
		matrix.print2D();
		
		//La matrice viene salvata all'interno di un file "matrix.ser"
		FileSerialization ser = new FileSerialization();
		ser.writeMatrix(matrix);
	}
	
	public static Matrix loadMatrix() {
		FileSerialization ser = new FileSerialization();
		Matrix matrix = ser.readMatrix();
		return matrix;
	}
}
