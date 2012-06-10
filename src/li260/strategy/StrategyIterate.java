package li260.strategy;

import li260.circuit.Circuit;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyIterate implements Strategy {

	private static final long serialVersionUID = 1L;
	private double acc;
	private double turn;

	public StrategyIterate(double accel, double turn) {
		super();
		this.acc = accel;
		this.turn = turn;
	}

	@Override
	public Commande getCommande() {
		return new Commande(acc, turn);
	}

	public void init(Voiture v, Circuit c) {
	}
}
