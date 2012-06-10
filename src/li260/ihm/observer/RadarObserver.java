package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;

import li260.algo.Radar;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.voiture.Voiture;

public class RadarObserver implements ObserverSwing {

	private Radar r;
	private Voiture voiture;

	public RadarObserver(Voiture voiture, Radar r) {
		this.voiture = voiture;
		this.r = r;
	}

	private int getX() {
		return (int) voiture.getPosition().getX();
	}

	private int getY() {
		return (int) voiture.getPosition().getY();
	}

	@Override
	public void print(Graphics g) {
		double[] thetas = r.thetas();
		double[] distPixel = r.distancesInPixels();
		g.setColor(new Color(255, 215, 0));
		for (int i = 0; i < thetas.length; i++) {
			Vecteur v = voiture.getDirection().cloneAsVecteur();
			VTools.rotation(v, thetas[i]);
			v.autoProdDouble(distPixel[i]);
			v.autoAdd(voiture.getPosition());
			int x1 = getX();
			int y1 = getY();
			int x2 = (int) v.getX();
			int y2 = (int) v.getY();
			if (i == r.getBestIndex()) {
				g.setColor(new Color(71, 160, 204, 100));
			} else {
				g.setColor(new Color(249, 229, 85, 100));
			}
			g.drawLine(y2, x2, y1, x1);
		}
	}
}
