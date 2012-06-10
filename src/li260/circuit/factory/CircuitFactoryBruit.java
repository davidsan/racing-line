package li260.circuit.factory;

import java.util.ArrayList;
import java.util.Random;

import li260.circuit.CircuitImpl;
import li260.circuit.CircuitModifiable;
import li260.circuit.Terrain;
import li260.geometry.Vecteur;

public class CircuitFactoryBruit {
	private int height;
	private int width;
	private int borderSize;
	private static final Vecteur sensDepart = new Vecteur(0, 1);
	private static final Vecteur sensArrivee = new Vecteur(0, 1);
	private Vecteur ptDepart;
	private ArrayList<Vecteur> listeArrivees;

	public CircuitFactoryBruit(int height, int width, int borderSize) {
		this.height = height;
		this.width = width;
		this.borderSize = borderSize;
		this.ptDepart = null;
		this.listeArrivees = new ArrayList<Vecteur>();
	}

	public CircuitModifiable build() {
		Terrain[][] track;
		track = new Terrain[height][width];
		init(track);
		ajouterBruit(track);
		initDepartArrivee(track);
		return new CircuitImpl(track, ptDepart, sensDepart, sensArrivee,
				listeArrivees, "random" + height + width + borderSize);
	}

	private void init(Terrain[][] track) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				track[i][j] = Terrain.Route;
				if (j < borderSize || j > width - borderSize) {
					track[i][j] = Terrain.Herbe;
				}
				if (i < borderSize || i > (height - borderSize)) {
					track[i][j] = Terrain.Herbe;
				}
			}
		}
	}

	private void initDepartArrivee(Terrain[][] track) {
		Random rand = new Random();
		int separator = 5;
		int size = 40;
		int x = rand.nextInt(size) + borderSize + separator;
		int y = rand.nextInt(size) + borderSize + separator;
		track[x][y] = Terrain.StartPoint;
		ptDepart = new Vecteur(x, y);
		int j = width - borderSize;
		for (int i = 0; i < size; i++) {
			track[i + height - borderSize - size + 1][j] = Terrain.EndLine;
			listeArrivees
					.add(new Vecteur(i + height - borderSize - size + 1, j));
		}
	}

	private void ajouterBruit(Terrain[][] track) {
		Random rand = new Random();
		for (int i = 0; i < track.length; i++) {
			for (int j = 0; j < track[0].length; j++) {
				if (track[i][j] == Terrain.Route) {
					double p = rand.nextDouble();
					if (p < 0.003) {
						track[i][j + 1] = Terrain.Herbe;
						track[i][j - 1] = Terrain.Herbe;
						track[i + 1][j] = Terrain.Herbe;
						track[i - 1][j] = Terrain.Herbe;
					}

				}
			}
		}

	}
}
