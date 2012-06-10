package li260.voiture;

import li260.geometry.Vecteur;

public interface Voiture {
	public void drive(Commande c) throws VoitureException;

	public double getMaxTurnSansDerapage();

	public double getVitesse();

	public Vecteur getPosition();

	public Vecteur getDirection();

	public boolean getDerapage();

	public double getBraquage();
}
