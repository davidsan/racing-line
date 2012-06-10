package li260.strategy;

import li260.circuit.Circuit;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyLimiteur implements Strategy {

	private static final long serialVersionUID = 1L;
	private Strategy s;
	private transient Voiture v;
	private double maxSpeed;

	public StrategyLimiteur(Strategy s, Voiture v, double maxSpeed) {
		super();
		this.s = s;
		this.v = v;
		this.maxSpeed = maxSpeed;
	}

	@Override
	public Commande getCommande() {
		Commande res = s.getCommande();
		if (v.getVitesse() > maxSpeed) {
			return new Commande(-1, res.getTurn());
		}
		return res;
	}

	public void init(Voiture v, Circuit c) {
		this.v = v;
		s.init(v, c);
	}
}
