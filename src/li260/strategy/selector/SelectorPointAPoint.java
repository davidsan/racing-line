package li260.strategy.selector;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.strategy.StrategyPointAPoint;
import li260.voiture.Voiture;

public class SelectorPointAPoint implements Selector {
	private StrategyPointAPoint strat;
	private Voiture v;
	private Circuit c;

	public SelectorPointAPoint(StrategyPointAPoint strat, Voiture v, Circuit c) {
		super();
		this.strat = strat;
		this.v = v;
		this.c = c;
	}

	@Override
	public boolean isSelected() {
		/* si la liste des points est vide */
		if (strat.getIndex() > strat.getListePoints().size() - 1) {
			return false;
		}

		/* si on ne voit pas le point suivant */
		Vecteur position = v.getPosition().cloneAsVecteur();
		Vecteur dir = new Vecteur(position, strat.getListePoints().get(
				strat.getIndex()));
		while (!position.equalsPoint(strat.getListePoints().get(
				strat.getIndex()))) {
			if (!ToolsTerrain.isRunnable(c.getTerrain(position))) {
				return false;
			}
			position.autoAdd(VTools.prodDouble(dir, 0.01));
		}
		return true;
	}
}
