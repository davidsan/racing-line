package li260.mains.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import li260.geometry.Vecteur;

public class TestVecteurLoadSave {

	public static void main(String[] args) {
		Vecteur v1 = new Vecteur(4, 5);
		System.out.println(v1.toString());

		// sauvegarde
		try {
			FileOutputStream fos = new FileOutputStream(new File("v1.vect"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(v1);
			fos.close();
		} catch (java.io.FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
		// chargement
		Vecteur v2 = null;
		try {
			FileInputStream fin = new FileInputStream(new File("v1.vect"));
			ObjectInputStream oos = new ObjectInputStream(fin);
			v2 = (Vecteur) oos.readObject();
			fin.close();
		} catch (java.io.FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(v2.toString());

	}
}
