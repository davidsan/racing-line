package li260.mains.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;
import li260.circuit.factory.CircuitFactory;
import li260.simulation.Simulation;
import li260.strategy.Strategy;
import li260.strategy.StrategyListeCommande;
import li260.tools.CommandeTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestListeCommande {
	public static void main(String[] args) {
		ArrayList<Commande> l = null;
		try {
			l = CommandeTools.loadListeCommande("tcoms/1_safe.com");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String filename = "track/1_safe.trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit c = cF.build();
		FerrariFactory f = new FerrariFactory(c);
		Voiture v = f.build();
		Strategy strategy;
		strategy = new StrategyListeCommande(l);
		// strategy = new StrategyToutDroit();
		Simulation s = new Simulation(v, c, strategy);
		BufferedImage im = ToolsTerrain.imageFromCircuit(c);
		try {
			while (s.isPlaying()) {
				s.playOneShot();
				if (v.getDerapage()) {
					im.setRGB((int) v.getPosition().getY(), (int) v
							.getPosition().getX(), Color.red.getRGB());
				} else {
					im.setRGB((int) v.getPosition().getY(), (int) v
							.getPosition().getX(), Color.orange.getRGB());
				}

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
