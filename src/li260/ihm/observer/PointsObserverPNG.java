package li260.ihm.observer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.tools.PngTools;

public class PointsObserverPNG implements ObserverSwing {

	private ArrayList<Vecteur> listePoints;

	public PointsObserverPNG(ArrayList<Vecteur> listePoints) {
		super();
		this.listePoints = listePoints;
	}

	@Override
	public void print(Graphics g) {
		for (int i = 0; i < listePoints.size(); i++) {
			BufferedImage point = (BufferedImage) PngTools
					.loadPng("./icons/fugue/marker.png");
			g.drawImage(point, (int) listePoints.get(i).getY() - 8,
					(int) listePoints.get(i).getX() - 16, null);
		}
	}
}