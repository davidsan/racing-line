package li260.strategy;

import java.util.ArrayList;

import li260.algo.Radar;
import li260.circuit.Circuit;
import li260.geometry.Vecteur;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyRadar implements Strategy {

	private static final long serialVersionUID = 1L;
	private Radar r;
	private Commande allCom[];
	private transient Voiture v;
	/* récupération de la liste de point à jouer pour la stratégie Point à Point */
	private ArrayList<Vecteur> recordPos;
	private int cpt;

	public StrategyRadar(Radar r, Commande allCom[], Voiture v) {
		super();
		this.r = r;
		this.allCom = allCom;
		this.v = v;
		recordPos = new ArrayList<Vecteur>();
		cpt = 0;
	}

	@Override
	public Commande getCommande() {
		r.scores();
		cpt++;
		if (cpt % 500 == 0) {
			recordPos.add(v.getPosition().cloneAsVecteur());
		}
		if (Math.abs(allCom[r.getBestIndex()].getTurn()) >= v
				.getMaxTurnSansDerapage()) {
			return new Commande(allCom[r.getBestIndex()].getAcc(),
					Math.signum(allCom[r.getBestIndex()].getTurn())
							* v.getMaxTurnSansDerapage());
		}
		// System.err.println("Radar");
		return allCom[r.getBestIndex()];
	}

	public ArrayList<Vecteur> getRecordPos() {
		return recordPos;
	}

	public void init(Voiture v, Circuit c) {
		r.init(v, c);
		this.v = v;
	}
}
