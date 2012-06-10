package li260.algo;

import java.util.Comparator;

import li260.geometry.Vecteur;

public class ComparatorDijk implements Comparator<Vecteur> {
	private double[][] dist;

	public ComparatorDijk(double[][] dist) {
		this.dist = dist;
	}

	public int compare(Vecteur o1, Vecteur o2) {
		if (dist[(int) o1.getX()][(int) o1.getY()] > dist[(int) o2.getX()][(int) o2
				.getY()])
			return 1;
		else if (dist[(int) o1.getX()][(int) o1.getY()] == dist[(int) o2.getX()][(int) o2
				.getY()])
			return 0;
		return -1;
	}
}