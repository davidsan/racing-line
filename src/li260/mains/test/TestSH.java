package li260.mains.test;

import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.Circuit;
import li260.circuit.factory.CircuitFactory;
import li260.optimisation.CommandeFactory;
import li260.simulation.EtatSimulation;
import li260.simulation.Simulation;
import li260.strategy.StrategyRadar;
import li260.strategy.StrategySelectorImpl;
import li260.strategy.selector.SelectorRadar;
import li260.tools.CommandeTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestSH {

	public static void main(String[] args) {

		double[] thetas = CommandeFactory.generateThetas(
				Integer.parseInt(args[1]), Double.parseDouble(args[2]));
		Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);
		String name = args[0];
		String filename = "track/" + name + ".trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit track = cF.build(); 

		FerrariFactory f = new FerrariFactory(track);
		Voiture v = f.build();

		Radar r = new RadarDijkstra(thetas, v, track);
		// Radar r = new RadarClassique(thetas, v, track);
		// Strategy strategy = new StrategyRadar(r, allCom, v);

		StrategySelectorImpl strategy = new StrategySelectorImpl();
		strategy.add(new StrategyRadar(r, allCom, v), new SelectorRadar());
		Simulation simu = new Simulation(v, track, strategy);

		try {
			simu.play();
		} catch (VoitureException e1) {
			e1.printStackTrace();
		}

		if (simu.getState() == EtatSimulation.Succes) {
			CommandeTools.saveListeCommande(simu.getRecord(), "tcoms/ssh/"
					+ name + "/" + name + "_" + simu.getRecord().size()
					+ ".com");
		}

		System.out.println(simu.getRecord().size() + " (" + simu.getState()
				+ ")");
	}

}
