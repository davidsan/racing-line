package li260.strategy;

import java.util.ArrayList;

import li260.circuit.Circuit;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.optimisation.CommandeFactory;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategyPointAPoint implements Strategy {
	private static final long serialVersionUID = 1L;
	private ArrayList<Vecteur> listePoints;
	private transient Voiture v;
	private int index;

	public StrategyPointAPoint(Voiture v, ArrayList<Vecteur> listePoints) {
		super();
		this.v = v;
		this.listePoints = listePoints;
		this.index = 0;
	}

	public Commande getCommande() {

		Vecteur direction = v.getDirection().cloneAsVecteur();
		Vecteur position = v.getPosition().cloneAsVecteur();

		if (v.getPosition().equalsPoint(listePoints.get(index))) {
			index++;
			if (index >= listePoints.size()) {
				return new Commande(0, 0);
			}
		}

		/* permet de zapper les points qui sont derrière la voiture */
		// if (VTools.prodScal(v.getDirection(), new Vecteur(v.getPosition(),
		// listePoints.get(index))) < 0) {
		// index++;
		// if (index >= listePoints.size()) {
		// return new Commande(0, 0);
		// }
		// }

		Vecteur vecteurPoint = new Vecteur(position, listePoints.get(index));

		/* vitesse selon le tableau allCom */
		Commande cmd = CommandeFactory.generateOneComm(
				VTools.angle(direction, vecteurPoint), 1);

		cmd.controlCommand();
		if (Math.abs(cmd.getTurn()) >= v.getMaxTurnSansDerapage()) {
			return new Commande(cmd.getAcc(), Math.signum(cmd.getTurn())
					* v.getMaxTurnSansDerapage());
		}
		return cmd;
	}

	public ArrayList<Vecteur> getListePoints() {
		return listePoints;
	}

	public int getIndex() {
		return index;
	}

	public void init(Voiture v, Circuit c) {
	}
}
