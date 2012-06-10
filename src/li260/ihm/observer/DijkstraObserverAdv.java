package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;

import li260.circuit.Circuit;

public class DijkstraObserverAdv implements ObserverSwing{

	private Circuit track;

	public DijkstraObserverAdv(Circuit track) {
		super();
		this.track = track;
	}

	@Override
	public void print(Graphics g) {
		for (int i = 0; i < track.getHeight(); i++) {
			for (int j = 0; j < track.getWidth(); j++) {
				if (track.getDist(i, j) < (int) Double.POSITIVE_INFINITY) {
					g.setColor(new Color((int) (track.getDist(i, j) % 255.), 0,
							0));
					g.drawLine(j, i, j, i);
				}
			}
		}
	}
}
