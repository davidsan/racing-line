package li260.voiture.factory;

import li260.circuit.Circuit;
import li260.geometry.Vecteur;
import li260.voiture.Voiture;
import li260.voiture.VoitureImpl;

public class FerrariFactory implements VoitureFactory {

	private double vmax = 0.9;
	private double alpha_c = 0.02;
	private double braquage = 0.2;
	private double alpha_f = 0.001;
	private double beta_f = 0.002;
	private double alpha_derapage = 0.003;
	// private double masse = 1;
	private double vitesse_sortie_derapage = 0.15;
	private double vitesse = 0.;
	private Vecteur position;
	private Vecteur direction;

	public FerrariFactory(Circuit track) {
		super();
		position = track.getPointDepart().cloneAsVecteur();
		direction = track.getDirectionDepart().cloneAsVecteur();
	}

	public Voiture build() {
		return new VoitureImpl(vmax, braquage, alpha_c, alpha_f, beta_f,
				alpha_derapage, vitesse, position, direction,
				vitesse_sortie_derapage);
	}

}
