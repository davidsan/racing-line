package li260.voiture;

import java.io.Serializable;

public class Commande implements Serializable {

	private static final long serialVersionUID = 1L;
	private double acc;
	private double turn;

	public Commande(double acc, double turn) {
		super();
		this.acc = acc;
		this.turn = turn;
	}

	public void controlCommand() {
		if (acc > 1)
			acc = 1;
		else if (acc < -1)
			acc = -1;
		if (turn < -1)
			turn = -1;
		else if (turn > 1)
			turn = 1;
	}

	public double getTurn() {
		return turn;
	}

	public double getAcc() {
		return acc;
	}

	@Override
	public String toString() {
		return "Commande [acc=" + acc + ", turn=" + turn + "]";
	}

}
