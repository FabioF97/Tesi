
public class MatrixObject {
	
	ChiefTown src;
	ChiefTown dest;
	double distance;
	double time;
	
	public MatrixObject(ChiefTown src, ChiefTown dest, double distance, double time) {
		this.src = src;
		this.dest = dest;
		this.distance = distance/0.62137;
		this.time = time/60;
	}
	
	

	public ChiefTown getSrc() {
		return src;
	}



	public void setSrc(ChiefTown src) {
		this.src = src;
	}



	public ChiefTown getDest() {
		return dest;
	}



	public void setDest(ChiefTown dest) {
		this.dest = dest;
	}



	public double getDistance() {
		return distance;
	}



	public void setDistance(double distance) {
		this.distance = distance;
	}



	public double getTime() {
		return time;
	}



	public void setTime(double time) {
		this.time = time;
	}



	@Override
	public String toString() {
		return "distance=" + distance + ", time=" + time;
	}

}
