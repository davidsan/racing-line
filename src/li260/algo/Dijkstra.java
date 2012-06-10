package li260.algo;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.ihm.events.UpdateEventListener;
import li260.ihm.events.UpdateEventSender;

public class Dijkstra implements UpdateEventSender {

	private Circuit circuit;
	private double[][] dist;
	private Vecteur current;
	private PriorityBlockingQueue<Vecteur> q;
	private ArrayList<UpdateEventListener> l;

	public Dijkstra(Circuit circuit) {
		super();
		this.circuit = circuit;
		dist = new double[circuit.getHeight()][circuit.getWidth()];
		/* Initialisation du tableau de distance */
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[0].length; j++) {
				dist[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		q = new PriorityBlockingQueue<Vecteur>(100, new ComparatorDijk(dist));
		l = new ArrayList<UpdateEventListener>();
		/* Initialisation du tas */
		for (Vecteur v : circuit.getListeArrivees()) {
			q.add(v);
			dist[(int) v.getX()][(int) v.getY()] = 0;
		}
		compute();
	}

	/* Constructeur pour le chargeur */
	public Dijkstra(double[][] dist) {
		this.dist = dist;
		this.circuit = null;
		this.current = null;
		this.q = null;
		this.l = new ArrayList<UpdateEventListener>();
	}

	public void compute() {
		/* Tant qu'il reste des points dans le tas */
		while (!q.isEmpty()) {
			/* Extraire le point le plus proche de l'arrivée */
			try {
				current = q.take();
				// update();
				/* appelle les obs */
				/* Trouver les voisins & MAJ -> dans une autre méthode */
				trouverVoisinEtMaj(current);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void trouverVoisinEtMaj(Vecteur point) {
		/* Autour du point (i,j) */
		int x = (int) point.getX();
		int y = (int) point.getY();
		/* Boucle: pour di allant de -1 à 1 et dj allant de -1 à 1 */
		for (int di = -1; di <= 1; di++) {
			for (int dj = -1; dj <= 1; dj++) {
				Vecteur d = new Vecteur(x + di, y + dj);
				/* Eliminer les indices inintéressants (continue) */
				if (di == 0 && dj == 0) {
					continue;
				}
				/* Eliminer les mauvais voisins */
				if (!ToolsTerrain.isRunnable(circuit.getTerrain(d))) {
					continue;
				}

				if ((di + x >= circuit.getHeight())
						|| (dj + y >= circuit.getWidth()) || (di + x < 0)
						|| (dj + y < 0)) {
					continue;
				}
				if (dist[x][y] == 0) {
					if (VTools.prodScal(circuit.getDirectionArrivee(),
							new Vecteur(di, dj)) > 0) {
						continue;
					}
				}
				/* Compare la nouvelle distance avec la distance actuelle */
				int poids = poids(di, dj);
				if (dist[x + di][y + dj] > dist[x][y] + poids) {
					q.remove(d);
					dist[x + di][y + dj] = dist[x][y] + poids;
					q.add(d);
				}
			}
		}
	}

	private int poids(int di, int dj) {
		if (((di == -1) && (dj == -1)) || ((di == 1) && (dj == -1))
				|| ((di == -1) && (dj == 1)) || ((di == 1) && (dj == 1))) {
			return 20; // distance de Manhattan
		}
		return 10;
	}

	@Override
	public void add(UpdateEventListener listener) {
		l.add(listener);
	}

	@Override
	public void update() {
		for (UpdateEventListener listener : l) {
			listener.manageUpdate();
		}
	}

	public Vecteur getCurrent() {
		return current;
	}

	public double getDist(int x, int y) {
		return dist[x][y];
	}

	public double[][] getDist() {
		return dist;
	}
}
