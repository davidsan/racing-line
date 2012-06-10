package li260.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import li260.voiture.Commande;

public class CommandeTools {
	public static void saveListeCommande(ArrayList<Commande> liste,
			String filename) {
		try {
			File file = new File(filename);
			if (file.exists()) {
				System.out.println("Liste de commandes déjà existante : "
						+ filename);
				return;
			}
			DataOutputStream os = new DataOutputStream(new FileOutputStream(
					filename));
			for (Commande c : liste) {
				os.writeDouble(c.getAcc());
				os.writeDouble(c.getTurn());
			}
			os.close();
			System.out.println("Liste de commandes sauvegardée avec succès : "
					+ filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Commande> loadListeCommande(String filename)
			throws IOException {
		ArrayList<Commande> liste = null;

		try {
			DataInputStream os = new DataInputStream(new FileInputStream(
					filename));

			liste = new ArrayList<Commande>();
			double a, t;
			while (true) { // on attend la fin de fichier
				a = os.readDouble();
				t = os.readDouble();
				liste.add(new Commande(a, t));
			}

		} catch (EOFException e) {
			return liste;
		}

	}
}
