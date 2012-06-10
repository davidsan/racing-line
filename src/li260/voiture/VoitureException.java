package li260.voiture;

public class VoitureException extends Exception {

	private static final long serialVersionUID = 1L;

	public VoitureException() {
		super("Commande non gérée");
	}

}
