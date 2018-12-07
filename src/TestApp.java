import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class TestApp {
	
	public static void main(String[] args) throws IOException, ResourceException, ParseException {
		
		Matrix matrix = InitializeMatrix.loadMatrix();
		System.out.println(matrix.get(1, 57));
		
		/*
		FileSerialization read = new FileSerialization();
		Matrix matrix = read.readMatrix();
		matrix.print2D();
		*/
		
	}

}
