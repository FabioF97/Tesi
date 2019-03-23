import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.restlet.resource.ResourceException;

public class TestApp {
	
	/*
	static Matrix matrix;
	
	public static void insertNode(Writer writer, ChiefTown town, int n) throws IOException {
		
		String name = town.getName();
		String region = town.getRegion();
		String latitude = town.getLat();
		String longitude = town.getLon();
		
		writer.write("			<node id=\"" + n + "\" label=\"" + name + "\">\r\n" + 
				"				<attvalues>\r\n" + 
				"					<attvalue for=\"0\" value=\"" + region + "\"/>\r\n" + 
				"					<attvalue for=\"1\" value=\"" + latitude + "\"/>\r\n" + 
				"					<attvalue for=\"2\" value=\"" + longitude + "\"/>\r\n" + 
				"				</attvalues>\r\n" + 
				"			</node>\r\n");
	}
	
	public static void insertEdge(Writer writer, int src, int dst, int n) throws IOException {
		MatrixObject obj = matrix.getMatrix()[src][dst];
		double km = obj.getDistance();
		double minutes = obj.getTime();
		
		writer.write("			<edge id=\"" + n + "\" source=\"" + src + "\" target=\"" + dst + "\">\r\n" + 
				"				<attvalues>\r\n" + 
				"					<attvalue for=\"0\" value=\"" + km + "\"/>\r\n" + 
				"					<attvalue for=\"1\" value=\"" + minutes + "\"/>\r\n" + 
				"				</attvalues>\r\n" +
				"			</edge>\r\n");
	}
	
	*/
	
	
	//QUESTO ERA NEL MAIN
	/*
	matrix = InitializeMatrix.loadMatrix();
	
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	Writer writer = null;
	
	try  {
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filename.gexf"), "utf-8"));
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">\r\n" + 
				"	<meta lastmodifieddate=\"" + date + "\">\r\n" + 
				"		<creator>Fabio Fontana</creator>\r\n" + 
				"		<description>Input file for Gephi</description>\r\n" + 
				"	</meta>\r\n" +
				"	<graph defaultedgetype=\"directed\" idtype=\"string\" mode=\"static\">\r\n" + 
				"		<attributes class=\"node\" mode=\"static\">\r\n" +
				"			<attribute id=\"0\" title=\"Region\" type=\"string\"/>\r\n" +  
				"           <attribute id=\"1\" title=\"Latitude\" type=\"double\"/>\r\n" +  
				"           <attribute id=\"2\" title=\"Longitude\" type=\"double\"/>\r\n" +  
				"		</attributes>\r\n" + 
				"		<attributes class=\"edge\" mode=\"static\">\r\n" +
				"			<attribute id=\"0\" title=\"km\" type=\"double\"/>\r\n" +  
				"           <attribute id=\"1\" title=\"minutes\" type=\"double\"/>\r\n" +  
				"		</attributes>\r\n" + 
				"		<nodes count=\"109\">\r\n");
		int n = 0;
		for(int i = 0; i < matrix.getList().size(); ++i) {
			insertNode(writer, matrix.getList().get(i), n);
			++n;
		}
		writer.write("		</nodes>\r\n" + 
				"		<edges count=\"11772\">\r\n");
		int k = 0;
		for(int i = 0; i < 109; ++i) {
			for(int j = 0; j < 109; ++j) {
				if(i != j) {
					insertEdge(writer, i, j, k);
					++k;
				}
			}
		}
		writer.write("		</edges>\r\n" + 
				"	</graph>\r\n" + 
				"</gexf>");
	}
	finally {
		writer.close();
	}
	*/
	
	public static void main(String[] args) throws IOException, ResourceException, ParseException {
		//reso il grafico undirected
		Matrix matrix = InitializeMatrix.loadMatrix();
		new GEXFMaker(matrix);
		System.out.println("Fine");
		
		
	}

}
