package li260.mains.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;
import li260.circuit.factory.CircuitFactory;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestVoiture {

	public static void main(String[] args) {
		String filename = "track/1_safe.trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit c = cF.build();
		FerrariFactory f = new FerrariFactory(c);
		Voiture v = f.build();
		BufferedImage im = ToolsTerrain.imageFromCircuit(c);
		Commande commande = new Commande(1, 0);

		try {
			for (int i = 0; i < 100; i++) {
				v.drive(commande);
				im.setRGB((int) (v.getPosition().getY()),
						(int) (v.getPosition().getX()), Color.blue.getRGB());
			}
			commande = new Commande(1, 1);
			for (int i = 0; i < 100; i++) {
				v.drive(commande);
				im.setRGB((int) (v.getPosition().getY()),
						(int) (v.getPosition().getX()), Color.red.getRGB());
			}
			commande = new Commande(1, -1);
			for (int i = 0; i < 100; i++) {
				v.drive(commande);
				im.setRGB((int) (v.getPosition().getY()),
						(int) (v.getPosition().getX()), Color.yellow.getRGB());
			}
		} catch (VoitureException e) {
			e.printStackTrace();
		}

		try {
			ImageIO.write(im, "png", new File(filename + "_drive.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
