package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import li260.geometry.Vecteur;

public class ZoneObserver implements ObserverSwing {

	private ArrayList<Vecteur> listeZones;

	public ZoneObserver(ArrayList<Vecteur> listeZones) {
		super();
		this.listeZones = listeZones;
	}

	@Override
	public void print(Graphics g) {
		for (int i = 0; i < listeZones.size(); i++) {
			int x = (int) listeZones.get(i).getX();
			int y = (int) listeZones.get(i).getY();
			// BufferedImage point = (BufferedImage) PngTools
			// .loadPng("./icons/fugue/magnet-small.png");
			g.setColor(new Color(173, 200, 228, 100));
			g.fillOval(y - 15, x - 15, 30, 30);
			g.setColor(new Color(36, 104, 172, 100));

			g.drawOval(y - 15, x - 15, 30, 30);
			// g.drawImage(point, y - 8,
			// x - 8, null);
			//

		}
	}
}
