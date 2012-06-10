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

public class StrategyCloneAdvance implements Strategy {

	private static final long serialVersionUID = 1L;
	private Jeu j;
	private Radar r;
	private Commande[] allCom;
	private int coupAnticipe;

	public StrategyCloneAdvance(Jeu j, Radar r, Commande[] allCom,
			int coupAnticipe) {
		super();
		this.j = j;
		this.r = r;
		this.allCom = allCom;
		this.coupAnticipe = coupAnticipe;
	}

	public Commande getCommande() {
		Commande res = null;

		double[] vitesseArray = new double[] { -1, -0.5, 0, 0.5, 1 };
		double[] scoreArray = new double[5];

		for (int i = 0; i < vitesseArray.length; i++) {
			/* clonage du jeu */
			Jeu clone = new Jeu();
			clone.setCircuit(j.getCircuit());
			clone.setVoiture(new VoitureImpl((VoitureImpl) j.getVoiture()));
			clone.setStrategy(new StrategyRegulateur(new StrategyRadar(r,
					allCom, clone.getVoiture()), vitesseArray[i]));
			clone.setRadar(j.getRadar());
			clone.setSimulation(new Simulation(clone.getVoiture(), clone
					.getCircuit(), clone.getStrategy()));

			/* récupération de la commande par défaut */
			res = clone.getStrategy().getCommande();
			int nbCoup;
			for (nbCoup = 0; nbCoup < coupAnticipe; nbCoup++) {
				if (clone.getSimulation().getState() != EtatSimulation.Run) {
					break;
				}
				try {
					clone.getSimulation().playOneShot();
				} catch (VoitureException e) {
					e.printStackTrace();
				}
			}

			if (clone.getSimulation().getState() == EtatSimulation.Run) {
				scoreArray[i] = clone.getCircuit().getDist(
						(int) clone.getVoiture().getPosition().getX(),
						(int) clone.getVoiture().getPosition().getY());
			} else {
				if (clone.getSimulation().getState() == EtatSimulation.Succes) {
					scoreArray[scoreArray.length - 1] = -Double.MAX_VALUE;
				} else { /* Echec */
					scoreArray[i] = Double.MAX_VALUE;
				}
			}

		}
		int min = 0;
		for (int i = 0; i < scoreArray.length; i++) {
			if (scoreArray[i] < scoreArray[min]) {
				min = i;
			}
		}

		return new Commande(vitesseArray[min], res.getTurn());
	}

	public static String arrayToString(double[] a, String separator) {
		String result = "";
		if (a.length > 0) {
			result = "" + a[0]; // start with the first element
			for (int i = 1; i < a.length; i++) {
				result = result + separator + a[i];
			}
		}
		return result;
	}

	public void init(Voiture v, Circuit c) {
	}
}
