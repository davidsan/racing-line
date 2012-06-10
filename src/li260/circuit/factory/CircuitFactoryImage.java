package li260.circuit.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import li260.circuit.Circuit;
import li260.circuit.CircuitImpl;
import li260.circuit.Terrain;
import li260.circuit.ToolsTerrain;
import li260.geometry.Vecteur;

public class CircuitFactoryImage {
	private String filename;

	public CircuitFactoryImage(String filename) {
		this.filename = filename;
	}

	public Circuit build() {
		Terrain[][] matrice;
		Vecteur ptDepart = null;
		Vecteur sensDepart = new Vecteur(0, 1);
		Vecteur sensArrivee = new Vecteur(0, 1);
		ArrayList<Vecteur> listeArrivees = new ArrayList<Vecteur>();
		BufferedImage im = null;
		File file = new File(filename);

		String filenameWithOutExt = file.getName();
		int pos = filenameWithOutExt.lastIndexOf(".");
		if (pos > 0) {
			filenameWithOutExt = filenameWithOutExt.substring(0, pos);
		}

		try {
			im = ImageIO.read(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		matrice = new Terrain[im.getHeight()][im.getWidth()];

		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				matrice[i][j] = ToolsTerrain.terrainFromColor(im.getRGB(j, i));
				if (matrice[i][j] == Terrain.StartPoint)
					ptDepart = new Vecteur(i, j);
				if (matrice[i][j] == Terrain.EndLine)
					listeArrivees.add(new Vecteur(i, j));
			}
		}
		return new CircuitImpl(matrice, ptDepart, sensDepart, sensArrivee,
				listeArrivees, filenameWithOutExt);
	}
}
