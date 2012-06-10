package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.simulation.Simulation;

public class TrajectoireObserver implements ObserverSwing {

	private Simulation simu;

	public TrajectoireObserver(Simulation simu) {
		super();
		this.simu = simu;
	}

	@Override
	public void print(Graphics g) {
		ArrayList<Vecteur> recordPos = simu.getRecordPos();
		boolean print = true;
		for (int i = 0; i < recordPos.size(); i++) {

			if (i % 20 == 0) {
				print = !print;
			}
			if (print) {
				g.setColor(new Color(0x8c8cff));
				int size = 2;
				g.fillOval((int) (recordPos.get(i).getY() - size / 2),
						(int) (recordPos.get(i).getX() - size / 2), size, size);

			}
		}
	}

}
