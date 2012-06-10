package li260.strategy;

import li260.circuit.Circuit;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyRegulateur implements Strategy {

	private static final long serialVersionUID = 1L;
	private Strategy strategy;
	private double maxAcc;

	public StrategyRegulateur(Strategy strategy, double maxSpeed) {
		super();
		this.strategy = strategy;
		this.maxAcc = maxSpeed;
	}

	@Override
	public Commande getCommande() {
		Commande cmd = strategy.getCommande();
		return new Commande(Math.min(cmd.getAcc(), maxAcc), cmd.getTurn());
	}

	@Override
	public void init(Voiture v, Circuit c) {
	}

}
