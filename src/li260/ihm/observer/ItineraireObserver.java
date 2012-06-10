package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.simulation.Simulation;

public class ItineraireObserver implements ObserverSwing {

	private Simulation simu;

	public ItineraireObserver(Simulation simu) {
		super();
		this.simu = simu;
	}

	@Override
	public void print(Graphics g) {
		ArrayList<Vecteur> recordPos = simu.getRecordPos();

		for (int i = 0; i < recordPos.size()-1; i++) {
			int size = 4;
			g.setColor(new Color(0x8c8cff));
			if (simu.getRecordVit().get(i) < 0.9) {
				g.setColor(new Color(0x7373ff));
			}
			g.fillOval((int) (recordPos.get(i).getY() - size / 2),
					(int) (recordPos.get(i).getX() - size / 2), size, size);

		}
	}
}
