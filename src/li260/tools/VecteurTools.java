package li260.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import li260.geometry.Vecteur;

public class VecteurTools {
	public static void saveListeVecteur(ArrayList<Vecteur> liste,
			String filename) {
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream(
					filename));
			for (Vecteur v : liste) {
				os.writeDouble(v.getX());
				os.writeDouble(v.getY());
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Vecteur> loadListeVecteur(String filename)
			throws IOException {
		ArrayList<Vecteur> liste = null;

		try {
			DataInputStream os = new DataInputStream(new FileInputStream(
					filename));

			liste = new ArrayList<Vecteur>();
			double x, y;
			while (true) { // on attend la fin de fichier
				x = os.readDouble();
				y = os.readDouble();
				liste.add(new Vecteur(x, y));
			}

		} catch (EOFException e) {
			return liste;
		}

	}
}
