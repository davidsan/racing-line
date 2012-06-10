package li260.strategy;

import li260.algo.Radar;
import li260.circuit.Circuit;
import li260.jeu.Jeu;
import li260.simulation.EtatSimulation;
import li260.simulation.Simulation;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.VoitureImpl;

public class StrategyClone implements Strategy {

	private static final long serialVersionUID = 1L;
	private Jeu j;
	private Radar r;
	private Commande[] allCom;
	private int coupAnticipe;

	public StrategyClone(Jeu j, Radar r, Commande[] allCom, int coupAnticipe) {
		super();
		this.j = j;
		this.r = r;
		this.allCom = allCom;
		this.coupAnticipe = coupAnticipe;
	}

	public Commande getCommande() {
		Commande res = null;
		int vitesse;
		for (vitesse = 10; vitesse >= -0; vitesse--) {
			int coupSucces = 0;
			Jeu clone = new Jeu();
			clone.setCircuit(j.getCircuit());
			clone.setVoiture(new VoitureImpl((VoitureImpl) j.getVoiture()));
			clone.setStrategy(new StrategyRegulateur(new StrategyRadar(r,
					allCom, clone.getVoiture()), vitesse / 10.0));
			clone.setRadar(j.getRadar());
			clone.setSimulation(new Simulation(clone.getVoiture(), clone
					.getCircuit(), clone.getStrategy()));

			res = clone.getStrategy().getCommande();

			for (int nbCoup = 0; nbCoup < coupAnticipe; nbCoup++) {
				if (!clone.getSimulation().isPlaying()
						&& clone.getSimulation().getState() != EtatSimulation.Succes) {
					break;
				} else {
					try {
						clone.getSimulation().playOneShot();
						coupSucces++;
					} catch (VoitureException e) {
						e.printStackTrace();
					}
				}
			}
			if (coupSucces == coupAnticipe) {
				break;
			}
		}

		return new Commande(vitesse / 10.0, res.getTurn());
	}

	public void init(Voiture v, Circuit c) {
	}
}
