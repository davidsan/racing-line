package li260.mains;

import li260.algo.RadarDijkstra;
import li260.circuit.factory.CircuitFactory;
import li260.jeu.Jeu;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.StrategyRadar;
import li260.voiture.Commande;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class BatchJeu {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Usage : java -classpath bin li260.mains.BatchJeu file.png");
			return;
		}

		// File dir = new File("track/");
		// String[] files;
		// FilenameFilter filter = new FilenameFilter() {
		// public boolean accept(File dir, String name) {
		// return name.endsWith(".trk");
		// }
		// };
		// files = dir.list(filter);
		// Arrays.sort(files);

		Jeu g = new Jeu();
		String sep = "==========================================================";
		int nbAngle = 60;
		String absolutePath = args[0];
		System.err.println(args[0]);
		for (int amplitude = 3; amplitude < 10; amplitude = amplitude + 2) {
			for (int v1 = -7; v1 >= -10; v1--) {
				for (int v2 = 10; v2 >= -10; v2 = v2 - 2) {
					for (int v3 = 10; v3 >= -10; v3 = v3 - 2) {
						for (int v4 = 10; v4 >= -10; v4 = v4 - 2) {
							for (int v5 = 10; v5 >= -10; v5 = v5 - 2) {
								for (nbAngle = 20; nbAngle < 100; nbAngle = nbAngle + 5) {
									System.out.print("nbAngle : " + nbAngle);
									System.out.print(", amplitude : "
											+ (2 + amplitude / 10.));
									System.out
											.printf(", vitesse : %.1f %.1f %.1f %.1f %.1f\n",
													v1 / 10., v2 / 10.,
													v3 / 10., v4 / 10.,
													v5 / 10.);
									System.out.println(sep);
									double[] thetas = CommandeFactory
											.generateThetas(nbAngle, Math.PI
													/ (2 + (amplitude / 10.)));
									Commande[] allCom = CommandeFactory
											.generateAllCommCustom(thetas,
													v1 / 10., v2 / 10.,
													v3 / 10., v4 / 10., 1.);
									CircuitFactory cFac = new CircuitFactory(
											absolutePath);
									g.setCircuit(cFac.build());
									FerrariFactory vFac = new FerrariFactory(
											g.getCircuit());
									g.setVoiture(vFac.build());
									g.setRadar(new RadarDijkstra(thetas, g
											.getVoiture(), g.getCircuit()));
									g.setStrategy(new StrategyRadar(g
											.getRadar(), allCom, g.getVoiture()));
									g.setSimulation(new Simulation(g
											.getVoiture(), g.getCircuit(), g
											.getStrategy()));
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
		}
	}
}
