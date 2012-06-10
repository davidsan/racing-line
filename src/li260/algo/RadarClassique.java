package li260.algo;

import li260.circuit.Circuit;
import li260.circuit.Terrain;
import li260.circuit.ToolsTerrain;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.voiture.Voiture;

public class RadarClassique implements Radar {

	private static final long serialVersionUID = 1L;
	protected static final double EPS = 0.1;
	protected double[] thetas;
	protected transient Voiture v;
	protected transient Circuit c;
	protected double[] scores;
	protected int bestIndex;

	public RadarClassique(double[] thetas, Voiture v, Circuit c) {
		this.thetas = thetas;
		this.v = v;
		this.c = c;
		bestIndex = 0;
		scores = new double[thetas.length];
	}

	public double[] scores() {
		for (int i = 0; i < thetas.length; i++) {
			scores[i] = calcScore(thetas[i]);
			if (scores[i] > scores[bestIndex])
				bestIndex = i;
		}
		return scores;
	}

	public double calcScore(double d) {
		Vecteur p = new Vecteur(v.getPosition());
		Vecteur dir = new Vecteur(v.getDirection());
		VTools.rotation(dir, d);
		int cpt = 0;
		while (ToolsTerrain.isRunnable(c.getTerrain(p))) {
			if (c.getTerrain(p) == Terrain.EndLine
					&& VTools.prodScal(dir, c.getDirectionArrivee()) <= 0) {
				return -Double.POSITIVE_INFINITY;
			}
			if (c.getTerrain(p) == Terrain.EndLine
					&& VTools.prodScal(dir, c.getDirectionArrivee()) > 0) {
				return Integer.MAX_VALUE - cpt;
			}
			cpt++;
			p.autoAdd(VTools.prodDouble(dir, EPS));
		}

		return cpt;
	}

	@Override
	public double[] distancesInPixels() {
		double[] pixel = new double[scores.length];
		for (int i = 0; i < pixel.length; i++) {
			pixel[i] = scores[i] * 0.1;
			if (pixel[i] < 0) {
				pixel[i] = 0;
			}
		}
		return pixel;
	}

	@Override
	public int getBestIndex() {
		return bestIndex;
	}

	@Override
	public double[] thetas() {
		return thetas;
	}

	public void init(Voiture v, Circuit c) {
		this.v = v;
		this.c = c;
	}
}
