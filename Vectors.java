package default;

public class Vectors {

  	public double dotProduct(double[] u, double[] v) {
		return u[0] * v[0] + u[1] * v[1];
	}
	
	public double magnitude(double[] v) {
		return Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2));
	}
	
	/** [0,360) */
	public double angle(double[] u, double[] v) {
		return Math.atan2(v[0] * u[1] - v[1] * u[0], v[0] * u[0] + v[1] * u[1]);
	}
	
	public double euclideanDistance(double[] fin, double[] ini) {
		return Math.sqrt((Math.pow((fin[0] - ini[0]), 2)) + (Math.pow((fin[1] - ini[1]), 2)));
	}
	
}
