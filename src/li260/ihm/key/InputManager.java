package li260.ihm.key;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import li260.ihm.events.UpdateEventListener;
import li260.jeu.Jeu;
import li260.voiture.VoitureException;

public class InputManager extends KeyAdapter {
	private InputHandler pilotemn;
	private UpdateEventListener ihm;
	private Jeu game;
	private final Set<Character> pressedKey = new HashSet<Character>();

	public InputManager(Jeu game, UpdateEventListener ihm) {
		super();
		pilotemn = new InputHandler(game);
		this.ihm = ihm;
		this.game = game;
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		/* tue le thread */
		game.interrupt();
		pressedKey.add(e.getKeyChar());
		try {
			pilotemn.process(pressedKey);
		} catch (VoitureException e1) {
			e1.printStackTrace();
		}
		ihm.manageUpdate();
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		pressedKey.remove(e.getKeyChar());
		try {
			pilotemn.process(pressedKey);
		} catch (VoitureException e1) {
			e1.printStackTrace();
		}
		ihm.manageUpdate();

		/* relance des commandes si aucune touche est enfoncée */
		if (pressedKey.isEmpty()) {
			Thread t = new Thread() {
				public void run() {
					try {
						game.getSimulation().playWait();
					} catch (VoitureException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
	}
}