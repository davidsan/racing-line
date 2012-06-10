package li260.mains.test;

import java.io.File;
import java.io.IOException;

import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.Circuit;
import li260.circuit.factory.CircuitFactory;
import li260.optimisation.CommandeFactory; 
import li260.simulation.Simulation;
import li260.strategy.Strategy; 
import li260.strategy.StrategyRadar;
import li260.tools.StrategyTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestSimulation {

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
			System.out.println("Loading a strategy");
			try {
				strategy = StrategyTools.loadStrategy("tcoms/" + name
						+ ".strat");
			} catch (IOException e) {
				e.printStackTrace();
			}
			strategy.init(v, track);
		} else {
			System.out.println("No strategy found");
			System.out.println("Building a strategy");
			double[] thetas = CommandeFactory.generateThetas(70, Math.PI / 3);
			Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);
			Radar radarDijkstra = new RadarDijkstra(thetas, v, track);
			strategy = new StrategyRadar(radarDijkstra, allCom, v);
		}
		System.out.println("Strategy OK");
		Simulation simu = new Simulation(v, track, strategy);
 
		// strategy.add(new StrategyToutDroit(), new SelectorEndLine(v, track));

 
		// StrategyRadar strategyRadar = new StrategyRadar(radarDijkstra,
		// allCom,
		// v);
		// strategy.add(new StrategyDecorator(strategyRadar), new
		// SelectorRadar());

		// strategy.add(strategyRadar, new SelectorRadar());

		try {
			simu.play();
		} catch (VoitureException e1) {
			e1.printStackTrace();
		}


		// strategyRadar.getRecordPos().add(
		// new Vecteur(track.getListeArrivees().get(0).getX(), track
		// .getListeArrivees().get(0).getY() - 1));
		// VecteurTools.saveListeVecteur(strategyRadar.getRecordPos(), "tcoms/"
		// + name + ".pos");
 
		StrategyTools.saveStrategy(strategy, "tcoms/" + name + ".strat");

	}
}
