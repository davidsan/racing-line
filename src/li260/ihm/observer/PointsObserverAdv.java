package li260.ihm.observer;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.strategy.StrategyPointAPoint;
import li260.tools.PngTools;

public class PointsObserverAdv implements ObserverSwing {

	private StrategyPointAPoint strat;

	public PointsObserverAdv(StrategyPointAPoint strat) {
		super();
		this.strat = strat;
	}

	@Override
	public void print(Graphics g) {
		ArrayList<Vecteur> listePoints = strat.getListePoints();
		for (int i = 0; i < listePoints.size(); i++) {
			Image point = null;
			if (i >= strat.getIndex()) {
				point = PngTools.loadPng("./icons/fugue/marker.png");
			} else {
				point = PngTools.loadPng("./icons/fugue/marker--off.png");
			}
			g.drawImage(point, (int) listePoints.get(i).getY() - 8,
					(int) listePoints.get(i).getX() - 16, null);
		}
	}

}
