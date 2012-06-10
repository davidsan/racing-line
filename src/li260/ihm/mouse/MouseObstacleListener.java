package li260.ihm.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import li260.circuit.Terrain;
import li260.geometry.Vecteur;
import li260.ihm.events.UpdateEventListener;
import li260.jeu.Jeu;

public class MouseObstacleListener extends MouseAdapter {

	private ArrayList<Vecteur> listeObstacles;
	private UpdateEventListener ihm;
	private Jeu game;
	private static final int[][] trafficConeMatrix = new int[][] {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	public MouseObstacleListener(ArrayList<Vecteur> listeObstacles, Jeu game,
			UpdateEventListener ihm) {
		this.listeObstacles = listeObstacles;
		this.game = game;
		this.ihm = ihm;
	}

	public void mouseReleased(MouseEvent event) {
		listeObstacles.add(new Vecteur(event.getY(), event.getX()));
		int x = event.getX() - trafficConeMatrix.length/2;
		int y = event.getY() - trafficConeMatrix[0].length/2;
		for (int i = 0; i < trafficConeMatrix.length; i++) {
			for (int j = 0; j < trafficConeMatrix[0].length; j++) {
				if (trafficConeMatrix[i][j] == 0) {
					game.getCircuit().setTerrain(y+ i,
							x + j, Terrain.Obstacle);
				}
			}
		}
		Thread t = new Thread() {
			public void run() {
				game.getCircuit().majDijkstra();
			}
		};
		t.start();

		// System.out.println("OBS : Clic detected @ " + event.getX() + " : "
		// + event.getY());

		ihm.manageUpdate();
	}

	public ArrayList<Vecteur> getListeObstacles() {
		return listeObstacles;
	}
}
