package li260.mains;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.Circuit;
import li260.circuit.factory.CircuitFactoryBruit;
import li260.ihm.IHMSwing;
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.RadarObserver;
import li260.ihm.observer.SimulationObserver;
import li260.ihm.observer.TrajectoireObserver;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.Strategy;
import li260.strategy.StrategyRadar;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class MainPartiel2011 {

	public static void main(String[] args) {
		double[] thetas = CommandeFactory.generateThetas(30, Math.PI / 4);
		Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);

		CircuitFactoryBruit cF = new CircuitFactoryBruit(768, 1024, 10);
		Circuit track = cF.build();
		FerrariFactory f = new FerrariFactory(track);
		Voiture v = f.build();
		Radar r = new RadarDijkstra(thetas, v, track);
		Strategy s = new StrategyRadar(r, allCom, v);
		Simulation simu = new Simulation(v, track, s);

		JFrame fenetre = new JFrame("");

		IHMSwing ihm = new IHMSwing();

		ihm.add(new CircuitObserver(track));
		ihm.add(new RadarObserver(v, r));
		ihm.add(new TrajectoireObserver(simu));
		ihm.add(new SimulationObserver(simu, v));
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
	}
}
