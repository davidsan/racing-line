package li260.ihm.key;

public class SteeringWheel {
	private double turn;

	public SteeringWheel() {
		this.turn = 0;
	}

	public void update(double cmdTurn) {
		if (cmdTurn == 0) {
			if (this.turn > 0) {
				this.turn -= 0.1;
			} else {
				if (this.turn < 0) {
					this.turn += 0.1;
				}
			}
		} else {
			this.turn += (cmdTurn / 10.0);
		}
		if (this.turn > 1)
			this.turn = 1;
		if (this.turn < -1)
			this.turn = -1;
	}

	public double getTurn() {
		return turn;
	}

}
