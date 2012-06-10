package li260.strategy.selector;

import li260.circuit.Circuit;
import li260.circuit.Terrain;
import li260.circuit.ToolsTerrain;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.voiture.Voiture;

public class SelectorEndLine implements Selector {

	private Voiture v;
	private Circuit c;

	public SelectorEndLine(Voiture v, Circuit c) {
		super();
		this.v = v;
		this.c = c;
	}

	@Override
	public boolean isSelected() {
		Vecteur position = v.getPosition().cloneAsVecteur();
		while (ToolsTerrain.isRunnable(c.getTerrain(position))
				&& c.getTerrain(position) != Terrain.EndLine) {
			position.autoAdd(VTools.prodDouble(v.getDirection(), 0.01));
		}
		if (c.getTerrain(position) == Terrain.EndLine) {
			return true;
		}
		return false;
	}

}
