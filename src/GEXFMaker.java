import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GEXFMaker {

	Matrix matrix;
	
	public GEXFMaker(Matrix matrix) {
		this.matrix = matrix;
		try {
			write();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write() throws IOException {
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
					"	<graph defaultedgetype=\"undirected\" idtype=\"string\" mode=\"static\">\r\n" + 
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
					"		<edges>\r\n"); //"		<edges count=\"11772\">\r\n");
			insertEdges(writer);
			writer.write("		</edges>\r\n" + 
					"	</graph>\r\n" + 
					"</gexf>");
		}
		finally {
			writer.close();
		}
	}
	
	private void insertNode(Writer writer, ChiefTown town, int n) throws IOException {
		
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
	
	private boolean checkEdge(ChiefTown src, ChiefTown dst, ArrayList<String> borders) {
		if(borders.contains(dst.getRegion()))
			return true;
		if(src.getRegion().equals(dst.getRegion()))
			return true;
		if("Palermo".equals(src.getName()) && "Cagliari".equals(dst.getName()))
			return true;
		return false;
	}
	
	private void insertEdges(Writer writer) throws IOException {
		int i;
		int j;
		int n = 0;
		ChiefTown src;
		ChiefTown dst;
		ArrayList<String> borders;
		//Per ogni capoluogo
		for(i = 0; i < 109; ++i) {
			src = matrix.getTown(i);
			borders = EdgesHandler.getBorder(src.getRegion());
			//per ogni possibile destinazione
			for(j = 0; j < i; ++j) {  //SE SI PASSA A DIRECTED BISOGNA METTERE J < 109
				if(i != j) {
					dst = matrix.getTown(j);
					//Se i capoluoghi sono in regioni confinanti o nella stessa regione creo un edge
					if(checkEdge(src, dst, borders)) {
						insertEdge(writer, i, j, n++);
					}
				}
			}
		}
	}
	
	private void insertEdge(Writer writer, int src, int dst, int n) throws IOException {
		MatrixObject obj = matrix.getMatrix()[src][dst];
		double km = obj.getDistance();
		double minutes = obj.getTime();
		
		writer.write("			<edge id=\"" + n + "\" source=\"" + src + "\" target=\"" + dst + "\" weight=\"" + minutes + "\">\r\n" + 
				"				<attvalues>\r\n" + 
				"					<attvalue for=\"0\" value=\"" + km + "\"/>\r\n" + 
				"					<attvalue for=\"1\" value=\"" + minutes + "\"/>\r\n" + 
				"				</attvalues>\r\n" +
				"			</edge>\r\n");
	}
	
}
