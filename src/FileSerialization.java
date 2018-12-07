import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class FileSerialization {
	
	private String path;
	
	public FileSerialization() {
		path = Paths.get(".").toAbsolutePath().toString();
		path = path.substring(0, path.length()-1) + "matrix.ser";
	}
	
	public void writeMatrix(Matrix matrix) {
		try {
			
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(matrix);
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Matrix readMatrix() {
		
		Matrix matrix = null;;
		
		try {
			
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			matrix = (Matrix) ois.readObject();
			ois.close();
			return matrix;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return matrix;
		
	}
	
}
