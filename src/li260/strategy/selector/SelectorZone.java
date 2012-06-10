package li260.strategy.selector;

import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.voiture.Voiture;

public class SelectorZone implements Selector {

	private ArrayList<Vecteur> listeZones;
	private Voiture v;

	public SelectorZone(ArrayList<Vecteur> listeZones, Voiture v) {
		super();
		this.listeZones = listeZones;
		this.v = v;
	}

	@Override
	public boolean isSelected() {
		for (Vecteur zone : listeZones) {
			if (zone.equalsZone(v.getPosition(), 15)) {
				return true;
			}
		}
		return false;
	}

}
