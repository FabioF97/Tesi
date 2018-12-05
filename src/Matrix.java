
public class Matrix {
	
	private MatrixObject[][] matrix;
	
	public Matrix(int dim) {
		this.matrix = new MatrixObject[dim][dim];	
	}
	
	public void insert(MatrixObject obj, int i, int j) {
		matrix[i][j] = obj;
	}
	
	public MatrixObject get(int i, int j) {
		return matrix[i][j];
	}
	
	public MatrixObject[][] getMatrix(){
		return matrix;
	}
	
}
