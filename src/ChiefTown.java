
public class ChiefTown implements Comparable<ChiefTown>{

	private String name;
	private String region;
	private String lat;
	private String lon;
	
	public ChiefTown(String name, String region, String lat, String lon) {
		this.name = name;
		this.region = region;
		this.lat = lat;
		this.lon = lon;
	}
	
	public ChiefTown(String name, String region) {
		this.name = name;
		this.region = region;
		this.lat = null;
		this.lon = null;
	}
	
	public ChiefTown(String name) {
		this.name = name;
		this.region = null;
		this.lat = null;
		this.lon = null;
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}
	
	public void setCoordinates(String lat, String lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	public String getCoordinates() {
		return lat + "," + lon;
	}


	@Override
	public int compareTo(ChiefTown town) {
		if(name.compareTo(town.getName()) < 0)
			return -1;
		if(name.compareTo(town.getName()) == 0)
			return 0;
		else
			return 1;
	}

	@Override
	public String toString() {
		return "ChiefTown [name=" + name + ", region=" + region + "]";
	}
	
}
