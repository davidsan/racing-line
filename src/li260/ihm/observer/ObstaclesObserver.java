package li260.ihm.observer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.tools.PngTools;

public class ObstaclesObserver implements ObserverSwing {
	private ArrayList<Vecteur> listeObstacles;

	public ObstaclesObserver(ArrayList<Vecteur> listeObstacles) {
		super();
		this.listeObstacles = listeObstacles;
	}

	public void print(Graphics g) {
		for (int i = 0; i < listeObstacles.size(); i++) {
			BufferedImage point = (BufferedImage) PngTools
					.loadPng("./icons/fugue/traffic-cone.png");
			g.drawImage(point, (int) listeObstacles.get(i).getY() - 7,
					(int) listeObstacles.get(i).getX() - 7, null);
		}
	}
}
