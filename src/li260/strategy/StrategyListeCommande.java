package li260.strategy;

import java.util.ArrayList;

import li260.circuit.Circuit;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyListeCommande implements Strategy {

	private static final long serialVersionUID = 1L;
	private int index;
	private ArrayList<Commande> liste;

	public StrategyListeCommande(ArrayList<Commande> liste) {
		index = 0;
		this.liste = liste;
	}

	public Commande getCommande() {
		if (liste.size() > index)
			return liste.get(index++);
		return null;
	}

	public void init(Voiture v, Circuit c) {
	}
}
