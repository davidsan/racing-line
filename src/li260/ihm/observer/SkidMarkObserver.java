package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.simulation.Simulation;

public class SkidMarkObserver implements ObserverSwing {

	private Simulation simu;

	public SkidMarkObserver(Simulation simu) {
		super();
		this.simu = simu;
	}

	@Override
	public void print(Graphics g) {
		ArrayList<Vecteur> recordPos = simu.getRecordPos();
		ArrayList<Double> recordVit = simu.getRecordVit();

		for (int i = 0; i < recordVit.size(); i++) {
			if (recordVit.get(i) < 0.9) {
				g.setColor(Color.darkGray);
				Vecteur p = recordPos.get(i);
				g.drawRect((int) p.getY() - 4, (int) p.getX() - 4, 6, 6);
			}
		}
	}

}
