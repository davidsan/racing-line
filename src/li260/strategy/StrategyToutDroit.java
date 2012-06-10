package li260.strategy;

import li260.circuit.Circuit;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyToutDroit implements Strategy {

	private static final long serialVersionUID = 1L;

	@Override
	public Commande getCommande() {
		return new Commande(1, 0);
	}

	public void init(Voiture v, Circuit c) {
	}
}
