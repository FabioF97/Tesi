import java.io.Serializable;

public class MatrixObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	ChiefTown src;
	ChiefTown dest;
	double distance;
	double time;
	double avgSpeed;
	
	public MatrixObject(ChiefTown src, ChiefTown dest, double distance, double time) {
		this.src = src;
		this.dest = dest;
		this.distance = distance/0.62137;
		this.time = time/60;
		this.distance = Math.round(this.distance);
		this.time = Math.round(this.time);
	}
	
	

	public ChiefTown getSrc() {
		return src;
	}



	public void setSrc(ChiefTown src) {
		this.src = src;
	}
	
	public double getAvgSpeed() {
		return avgSpeed;
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

	public void computeAvgSpeed() {
		if(time == 0 || distance == 0) {
			this.avgSpeed = .0;
			return;
		}
		this.avgSpeed = distance/(time/60);
	}

	@Override
	public String toString() {
		return src.getName() + "-->" + dest.getName() + " " + distance + "km, " + time + "m, " + avgSpeed + "km/h";
	}

}
