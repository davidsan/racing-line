package li260.mains.test;

import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.CircuitModifiable;
import li260.circuit.factory.CircuitFactory;
import li260.jeu.Jeu;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.StrategyRadar;
import li260.strategy.StrategySelectorImpl;
import li260.strategy.selector.SelectorRadar;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.factory.FerrariFactory;

public class TestBatch {
	public static void main(String[] args) {
		Jeu g = new Jeu();
		String filename = args[0];
		CircuitFactory cF = new CircuitFactory(filename);
		CircuitModifiable track = cF.build();
		g.setCircuit(track);

		for (int i = 0; i < 300; i++) {
			FerrariFactory f = new FerrariFactory(g.getCircuit());
			Voiture v = f.build();
			g.setVoiture(v);
			StrategySelectorImpl strat = new StrategySelectorImpl();
			double[] thetas = CommandeFactory.generateThetas(5 + i,
					Math.PI / 2.1);
			Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);
			Radar r = new RadarDijkstra(thetas, g.getVoiture(), g.getCircuit());
			g.setRadar(r);
			strat.add(new StrategyRadar(r, allCom, g.getVoiture()),
					new SelectorRadar());
			g.setStrategy(strat);

			Simulation simu = new Simulation(g.getVoiture(), g.getCircuit(),
					strat);
			g.setSimulation(simu);
			g.play();
		}

	}

}
