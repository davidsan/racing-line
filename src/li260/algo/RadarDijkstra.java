package li260.algo;

import li260.circuit.Circuit;
import li260.circuit.Terrain;
import li260.circuit.ToolsTerrain;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.voiture.Voiture;

public class RadarDijkstra extends RadarClassique {

	private static final long serialVersionUID = 1L;

	public RadarDijkstra(double[] thetas, Voiture v, Circuit c) {
		super(thetas, v, c);
	}

	public double calcScore(double d) {
		Vecteur p = new Vecteur(v.getPosition());
		Vecteur dir = new Vecteur(v.getDirection());
		VTools.rotation(dir, d);
		int score = (int) Double.POSITIVE_INFINITY;
		int coup = 0; /* optimisation */
		while (ToolsTerrain.isRunnable(c.getTerrain(p))) {
			if (c.getTerrain(p) == Terrain.EndLine
					&& VTools.prodScal(dir, c.getDirectionArrivee()) <= 0) {
				return -Double.POSITIVE_INFINITY;
			}
			int tmp = (int) c.getDist((int) p.getX(), (int) p.getY());
			if (tmp == 0) {
				tmp = Integer.MAX_VALUE + coup;
			}
			if (tmp < score) {
				score = tmp;
			}
			p.autoAdd(VTools.prodDouble(dir, EPS));
			coup++;
		}
		return -1 * score;
	}

	public double[] distancesInPixels() {
		double[] pixel = new double[scores.length];
		for (int i = 0; i < pixel.length; i++) {
			pixel[i] = 0.1 * (scores[i]) * -1;
			if (pixel[i] < 0) {
				pixel[i] = 0;
			}
		}
		return pixel;
	}

}