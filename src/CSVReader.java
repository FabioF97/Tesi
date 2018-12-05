import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;


//il campo 5 è il nome del comune
//il campo 9 è la regione
//il campo 12 è il flag che se è impostato a 1 è un capoluogo di provincia

public class CSVReader {
	
	private final String LINK = "https://www.istat.it/storage/codici-unita-amministrative/Elenco-comuni-italiani.csv";
	
	private String path;
	
	/**
	 * Salva nella cartella del progetto il csv da cui leggere i capoluoghi di provincia e inizializza la variabile path
	 * @throws IOException
	 */
	public CSVReader() throws IOException {
		path = Paths.get(".").toAbsolutePath().toString();
		path = path.substring(0, path.length()-1) + "file.csv";
		URL url = new URL(LINK);
		File file = new File(path);
		FileUtils.copyURLToFile(url, file);
	}
	
	public String getPath() {
		return path;
	}


	private BufferedReader openFile(String path) throws FileNotFoundException {
		return new BufferedReader(new FileReader(path));
	}
	
	private ChiefTown createTown(String[] fields) {
		return new ChiefTown(fields[5], fields[9]);
	}	
	
	//Nella lista non viene messa Urbino che fa provincia con Pesaro che viene invece inserito, secondo l'ISTAT Urbino non fa provincia
	public List<ChiefTown> readCSV(String path) throws FileNotFoundException, IOException { 
		BufferedReader br = openFile(path); 
		String line = br.readLine(); // Reading header, Ignoring 
		List<ChiefTown> list = new ArrayList<ChiefTown>();
		//int n = 0;
		while ((line = br.readLine()) != null && !line.isEmpty()) { 
			if(line.equals(";;;;;;;;;;;;;;;;;;;;;;;;;")) //Il file termina con 10 righe ";;;;;;;;;;;;;;;;;;;;;;;;;" per cui se line è uguale a tale stringa ho letto tutto
				break;
			//++n;
			String[] fields = line.split(";"); 
			if(fields[12].equals("1")) {
				ChiefTown town = createTown(fields);
				list.add(town);
			}
		} 
		Collections.sort(list);
		for(ChiefTown c: list) {
			System.out.println(c);
		}
		System.out.println(list.size());
		//System.out.println(n);
		br.close(); 
		return list;
	}

}
