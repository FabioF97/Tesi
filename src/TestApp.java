import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class TestApp {
	
	//Si può fare un metodo che stampa una linea intera
	public static void main(String[] args) throws IOException, ResourceException, ParseException {
		
		
		Matrix matrix = InitializeMatrix.loadMatrix();
		System.out.println(matrix.get(88, 89));
		
	}

}
