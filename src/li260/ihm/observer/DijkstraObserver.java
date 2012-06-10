package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;

import li260.algo.Dijkstra;

public class DijkstraObserver implements ObserverSwing {

	private Dijkstra dijk;

	public DijkstraObserver(Dijkstra dijk) {
		super();
		this.dijk = dijk;
	}

	@Override
	public void print(Graphics g) {
		for (int i = 0; i < dijk.getDist().length; i++) {
			for (int j = 0; j < dijk.getDist()[0].length; j++) {
				if (dijk.getDist(i, j) < (int) Double.POSITIVE_INFINITY) {
					g.setColor(new Color((int) (dijk.getDist(i, j) % 255.), 0,
							0));
					g.drawLine(j, i, j, i);
				}
			}
		}
	}
}
