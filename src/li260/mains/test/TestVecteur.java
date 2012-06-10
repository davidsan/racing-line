package li260.mains.test;

import li260.geometry.VTools;
import li260.geometry.Vecteur;

public class TestVecteur {

	public static void main(String[] args) {

		// Création de deux vecteurs
		Vecteur v1 = new Vecteur(1999, 0);
		Vecteur v2 = new Vecteur(0, 1);
		System.out.println("v1 : " + v1);
		System.out.println("v2 : " + v2);

		// Addition
		System.out.println("L'addition de v1 et v2 est le "
				+ VTools.addition(v1, v2));

		// Soustraction
		System.out.println("Le soustraction de v1 et v2 est "
				+ VTools.soustraction(v1, v2));

		// Produit Scalaire
		System.out.println("Le produit scalaire de v1 et v2 est "
				+ VTools.prodScal(v1, v2));

		// Produit Vectoriel
		System.out.println("Le produit vectoriel de v1 et v2 est "
				+ VTools.prodVect(v1, v2));

		// Normalisation des vecteurs
		System.out.println("La normalisation du vecteur v1 est le "
				+ VTools.normalisation(v1));

		System.out.println("La normalisation du vecteur v2 est le "
				+ VTools.normalisation(v2));

		// Norme des vecteurs
		System.out.println("La norme du vecteur v1 est " + VTools.norme(v1));
		System.out.println("La norme du vecteur v2 est " + VTools.norme(v2));

		// Angle entre v1 et v2
		System.out.println("L'angle entre v1 et v2 est " + VTools.angle(v1, v2)
				+ " radian, soit " + (VTools.angle(v1, v2) * 180) / Math.PI
				+ " degré");

		// Orthogonalité de deux vecteurs
		System.out.println("v1 isOrthogonaux v2? "
				+ VTools.isOrthogonaux(v1, v2));

		// Croissement de deux vecteurs
		System.out
				.println("v1 se croise avec v2? " + VTools.seCroisent(v1, v2));

		// Random vecteur
		System.out.println("Random Vecteur :\n" + VTools.random());
		System.out.println("Composante Z : " + VTools.composanteZ(v1, v2));
		VTools.rotation(v1, Math.PI / 2);
		// VTools.normalisation(v1);

		System.out.println("Rotation : " + v1.toString());
	}

}