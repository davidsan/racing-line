package li260.ihm.key;

import java.util.Set;

import li260.jeu.Jeu;
import li260.voiture.Commande;
import li260.voiture.VoitureException;

public class InputHandler {

	private Jeu game;
	// private SteeringWheel volant;

	public InputHandler(Jeu game) {
		super();
		this.game = game;
		// volant = new SteeringWheel();
	}

	public double keyAccel(char c) {
		if (c == 'z') {
			return 1;
		}
		if (c == 's') {
			return -1;
		}
		return 0;
	}

	public double keyTurn(char c) {
		if (c == 'q') {
			return 0.7;
		}
		if (c == 'd') {
			return -0.7;
		}
		return 0;
	}

	public boolean isValidKey(char c) {
		return (c == 'z' || c == 's' || c == 'q' || c == 'd');
	}

	public void process(Set<Character> pressedKey) throws VoitureException {
		double acc = 0;
		double turn = 0;
		for (Character c : pressedKey) {
			if (!isValidKey(c)) {
				continue;
			}
			acc += keyAccel(c);
			turn += keyTurn(c);
		}

		// Commande cmd = new Commande(acc, volant.getTurn());
		Commande cmd = new Commande(acc, turn);
		if (Math.abs(cmd.getTurn()) >= game.getVoiture()
				.getMaxTurnSansDerapage()) {
			cmd = new Commande(cmd.getAcc(), Math.signum(cmd.getTurn())
					* game.getVoiture().getMaxTurnSansDerapage());
		}
		game.getSimulation().playCommande(cmd);
	}

}
