package li260.voiture;

import li260.geometry.VTools;
import li260.geometry.Vecteur;

public class VoitureImpl implements Voiture {

	private Vecteur position;
	private Vecteur direction;
	private double vitesse;
	private boolean derapage;
	private double vmax;
	private double alpha_c;
	private double braquage;
	private double alpha_f;
	private double beta_f;
	private double alpha_derapage;
	// private double masse;
	private double vitesse_sortie_derapage;
	private final double[] tabVitesse = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7,
			0.8, 0.9, 1. };
	private final double[] tabTurn = { 1., 1., 0.8, 0.7, 0.6, 0.4, 0.3, 0.2,
			0.1, 0.075 };
	private Vecteur direction_derapage;
	private double sens_derapage;

	public VoitureImpl(double vmax, double braquage, double alpha_c,
			double alpha_f, double beta_f, double alpha_derapage,
			double vitesse, Vecteur position, Vecteur direction,
			double vitesse_sortie_derapage) {
		this.position = position;
		this.direction = direction;
		this.vitesse = vitesse;
		this.vmax = vmax;
		this.alpha_c = alpha_c;
		this.braquage = braquage;
		this.alpha_f = alpha_f;
		this.beta_f = beta_f;
		this.alpha_derapage = alpha_derapage;
		// this.masse = masse;
		this.vitesse_sortie_derapage = vitesse_sortie_derapage;
		derapage = false;

	}

	public VoitureImpl(VoitureImpl model) {
		this.position = model.position.cloneAsVecteur();
		this.direction = model.direction.cloneAsVecteur();
		this.vitesse = model.vitesse;
		this.vmax = model.vmax;
		this.alpha_c = model.alpha_c;
		this.braquage = model.braquage;
		this.alpha_f = model.alpha_f;
		this.beta_f = model.beta_f;
		this.alpha_derapage = model.alpha_derapage;
		// this.masse = model.masse;
		this.vitesse_sortie_derapage = model.vitesse_sortie_derapage;
		derapage = model.derapage;

	}

	@Override
	public void drive(Commande c) throws VoitureException {
		c.controlCommand();

		if (!derapage && detection_derapage(c)) {
			debut_derapage(c);
		}
		if (derapage) {
			driveAvecDerapage(c);
		} else {
			driveSansDerapage(c);
		}
	}

	@Override
	public double getMaxTurnSansDerapage() {
		for (int i = 0; i < tabVitesse.length; i++) {
			if (vitesse < tabVitesse[i] * vmax) {
				return tabTurn[i];
			}
		}
		return tabTurn[tabTurn.length - 1];
	}

	@Override
	public double getVitesse() {
		return vitesse;
	}

	@Override
	public Vecteur getPosition() {
		return position;
	}

	@Override
	public Vecteur getDirection() {
		return direction;
	}

	@Override
	public boolean getDerapage() {
		return derapage;
	}

	public double getBraquage() {
		return braquage;
	}

	private void driveAvecDerapage(Commande c) {
		// freinage quelque soit la commande
		vitesse -= alpha_derapage;
		vitesse = Math.max(0., vitesse);
		// maj de la direction
		VTools.rotation(direction, Math.signum(c.getTurn())
				* getMaxTurnSansDerapage() * braquage * 0.5);
		VTools.rotation(direction_derapage, sens_derapage
				* getMaxTurnSansDerapage() * braquage * 1.2);
		// avance un peu selon
		position.autoAdd(VTools.prodDouble(direction, vitesse));
		if (vitesse < vitesse_sortie_derapage) {
			fin_derapage();
			System.out.println("fin_derapage");
		}
	}

	private void driveSansDerapage(Commande c) {
		// approche normale
		// 1) gestion du volant
		VTools.rotation(direction, (c.getTurn() * braquage));
		// 2.1) gestion des frottements
		vitesse -= alpha_f;
		vitesse -= beta_f * vitesse;
		// 2.2) gestion de l'acceleration/freinage
		vitesse += c.getAcc() * alpha_c;
		// 2.3) garanties, bornes...
		VTools.normalisation(direction);
		vitesse = Math.max(0., vitesse); // pas de vitesse négative
		vitesse = Math.min(vmax, vitesse);
		// 3) mise à jour de la position
		position.autoAdd(VTools.prodDouble(direction, vitesse));
	}

	private boolean detection_derapage(Commande c) {
		if (Double.compare(Math.abs(c.getTurn()), getMaxTurnSansDerapage()) > 0) {
			return true;
		}
		return false;
	}

	private void fin_derapage() {
		derapage = false;
		direction = direction_derapage.cloneAsVecteur();
	}

	private void debut_derapage(Commande c) {
		derapage = true;
		sens_derapage = Math.signum(c.getTurn());
		direction_derapage = direction.cloneAsVecteur();
	}

}
