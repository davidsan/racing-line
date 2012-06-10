package li260.mains.test;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.Circuit;
import li260.circuit.factory.CircuitFactory;
import li260.ihm.IHMSwing;
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.PointeurObserver;
import li260.ihm.observer.SkidMarkObserver;
import li260.optimisation.CommandeFactory;
import li260.simulation.EtatSimulation;
import li260.simulation.Simulation;
import li260.strategy.Strategy;
import li260.strategy.StrategyRadar;
import li260.tools.CommandeTools;
import li260.tools.StrategyTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestSelector {

	public static void main(String[] args) {

		String name = "1_safe";
		String filename = "track/" + name + ".trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit track = cF.build();
		// Circuit trackRaw = cF.build();
		// Tools.preProcessLonely((CircuitImpl) trackRaw, (CircuitImpl) track);

		FerrariFactory f = new FerrariFactory(track);
		Voiture v = f.build();

		File stratfile = new File("tcoms/" + name + ".strat");
		Strategy strategy = null;
		if (stratfile.isFile()) {
			System.out.println("loading a strategy");
			try {
				strategy = StrategyTools.loadStrategy("tcoms/" + name
						+ ".strat");
			} catch (IOException e) {
				e.printStackTrace();
			}
			strategy.init(v, track);
		} else {
			double[] thetas = CommandeFactory.generateThetas(400, 0.1);
			Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);
			Radar radarDijkstra = new RadarDijkstra(thetas, v, track);
			// Radar radarClassique = new RadarClassique(thetas, v, track);

			StrategyRadar strategyRadar = new StrategyRadar(radarDijkstra,
					allCom, v);
			strategy = strategyRadar;

		}
		Simulation simu = new Simulation(v, track, strategy);

		// strategy.add(new StrategyToutDroit(), new SelectorEndLine(v, track));

		// StrategyRadar strategyRadar = new StrategyRadar(radarDijkstra,
		// allCom,
		// v);
		// strategy.add(new StrategyDecorator(strategyRadar), new
		// SelectorRadar());

		// strategy.add(strategyRadar, new SelectorRadar());
		JFrame fenetre = new JFrame("Simulation LI260 (" + filename + ")");

		IHMSwing ihm = new IHMSwing();
		ihm.add(new CircuitObserver(track));
		// ihm.add(new RadarObserver(v, radarDijkstra));
		// ihm.add(new TrajectoireObserver(simu));
		ihm.add(new SkidMarkObserver(simu));
		// ihm.add(new VoitureObserver(v));
		ihm.add(new PointeurObserver(v));
		simu.add(ihm);

		fenetre.setResizable(false);
		fenetre.setLayout(new BorderLayout());
		fenetre.getContentPane().add(ihm, BorderLayout.CENTER);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);

		try {
			simu.play();
		} catch (VoitureException e1) {
			e1.printStackTrace();
		}

		if (simu.getState() == EtatSimulation.Succes) {
			CommandeTools.saveListeCommande(simu.getRecord(), "tcoms/" + name
					+ "_" + simu.getRecord().size() + ".com");
		}

		StrategyTools.saveStrategy(strategy, "tcoms/" + name + ".strat");

		System.err.println("Nombre d'iterations : " + simu.getRecord().size()
				+ " " + simu.getState());

	}
}
