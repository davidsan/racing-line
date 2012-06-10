package li260.mains.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import li260.algo.RadarDijkstra;
import li260.circuit.factory.CircuitFactory;
import li260.jeu.Jeu;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.StrategyCloneAdvance;
import li260.voiture.Commande;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestBatchClone {

	public static void main(String[] args) {

		File dir = new File("track/");
		String[] files;
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".trk");
			}
		};
		files = dir.list(filter);
		Arrays.sort(files);

		Jeu g = new Jeu();
		String sep = "==========================================================";

		for (int amplitude = 3; amplitude < 10; amplitude = amplitude + 1) {
			for (int nbAngle = 20; nbAngle <= 60; nbAngle = nbAngle + 20) {
				for (int nbCoupAvance = 20; nbCoupAvance <= 50; nbCoupAvance = nbCoupAvance + 10) {

					System.out.println(sep);
					System.out.println("nbAngle : " + nbAngle);
					System.out.println("amplitude : " + (2 + amplitude / 10.));

					double[] thetas = CommandeFactory.generateThetas(nbAngle,
							Math.PI / (2 + (amplitude / 10.)));
					Commande[] allCom = CommandeFactory.generateAllCommCustom(
							thetas, 1, 1, 1, 1, 1);

					for (String filename : files) {
						String absolutePath = "track/" + filename;
						CircuitFactory cFac = new CircuitFactory(absolutePath);
						g.setCircuit(cFac.build());
						FerrariFactory vFac = new FerrariFactory(g.getCircuit());
						g.setVoiture(vFac.build());
						g.setRadar(new RadarDijkstra(thetas, g.getVoiture(), g
								.getCircuit()));
						g.setStrategy(new StrategyCloneAdvance(g, g.getRadar(),
								allCom, 30));
						g.setSimulation(new Simulation(g.getVoiture(), g
								.getCircuit(), g.getStrategy()));
						try {
							g.getSimulation().play();
						} catch (VoitureException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
