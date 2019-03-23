import java.util.ArrayList;

public class EdgesHandler {
	
	//Accetta in ingresso il nome di una regione e restituisce la lista
	//delle regioni con cui confina
	public static ArrayList<String> getBorder(String src){
		ArrayList<String> ret = ret = new ArrayList<String>();;
		if("Valle d'Aosta/Vallée d'Aoste".equals(src)) {
			ret.add("Piemonte");
		}
		if("Piemonte".equals(src)) {
			ret.add("Valle d'Aosta/Vallée d'Aoste");
			ret.add("Liguria");
			ret.add("Lombardia");
			ret.add("Emilia-Romagna");
		}
		if("Lombardia".equals(src)) {
			ret.add("Piemonte");
			ret.add("Emilia-Romagna");
			ret.add("Trentino-Alto Adige/Südtirol");
			ret.add("Veneto");
		}
		if("Trentino-Alto Adige/Südtirol".equals(src)) {
			ret.add("Lombardia");
			ret.add("Veneto");
		}
		if("Veneto".equals(src)) {
			ret.add("Trentino-Alto Adige/Südtirol");
			ret.add("Lombardia");
			ret.add("Friuli-Venezia Giulia");
			ret.add("Emilia-Romagna");
		}
		if("Friuli-Venezia Giulia".equals(src)) {
			ret.add("Veneto");
		}
		if("Liguria".equals(src)) {
			ret.add("Piemonte");
			ret.add("Emilia-Romagna");
			ret.add("Toscana");
		}
		if("Emilia-Romagna".equals(src)) {
			ret.add("Veneto");
			ret.add("Lombardia");
			ret.add("Piemonte");
			ret.add("Liguria");
			ret.add("Toscana");
			ret.add("Marche");
		}
		if("Toscana".equals(src)) {
			ret.add("Liguria");
			ret.add("Emilia-Romagna");
			ret.add("Umbria");
			ret.add("Lazio");
			ret.add("Marche");
		}
		if("Marche".equals(src)) {
			ret.add("Emilia-Romagna");
			ret.add("Toscana");
			ret.add("Umbria");
			ret.add("Abruzzo");
			ret.add("Lazio");
		}
		if("Umbria".equals(src)) {
			ret.add("Marche");
			ret.add("Toscana");
			ret.add("Lazio");
		}
		if("Lazio".equals(src)) {
			ret.add("Toscana");
			ret.add("Umbria");
			ret.add("Marche");
			ret.add("Abruzzo");
			ret.add("Molise");
			ret.add("Campania");
		}
		if("Abruzzo".equals(src)) {
			ret.add("Marche");
			ret.add("Lazio");
			ret.add("Molise");
		}
		if("Molise".equals(src)) {
			ret.add("Abruzzo");
			ret.add("Lazio");
			ret.add("Campania");
			ret.add("Puglia");
		}
		if("Campania".equals(src)) {
			ret.add("Lazio");
			ret.add("Molise");
			ret.add("Puglia");
			ret.add("Basilicata");
		}
		if("Puglia".equals(src)) {
			ret.add("Molise");
			ret.add("Campania");
			ret.add("Basilicata");
		}
		if("Basilicata".equals(src)) {
			ret.add("Puglia");
			ret.add("Campania");
			ret.add("Calabria");
		}
		if("Calabria".equals(src)) {
			ret.add("Basilicata");
		}
		if(ret.size() == 0) {
			System.out.println("Non sono state trovate regioni confinanti");
			System.out.println(src);
		}
		return ret;
	}
	
	
}
