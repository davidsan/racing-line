package li260.mains.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.CircuitModifiable;
import li260.circuit.factory.CircuitFactory;
import li260.ihm.IHMSwing;
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.RadarObserver;
import li260.ihm.observer.SimulationObserver;
import li260.ihm.observer.ItineraireObserver;
import li260.jeu.Jeu;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.Strategy;
import li260.strategy.StrategyCloneAdvance;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestStrategyClone {

	public static void main(String[] args) {

		String name = "t2009";
		String filename = "track/" + name + ".trk";
		CircuitFactory cF = new CircuitFactory(filename);
		CircuitModifiable track = cF.build();

		FerrariFactory f = new FerrariFactory(track);
		Voiture v = f.build();

		double[] thetas = CommandeFactory.generateThetas(30, Math.PI / 2.3);
		Commande[] allCom = CommandeFactory.generateAllCommCustom(thetas, 1.,
				1., 1., 1., 1.);

		Radar radarDijkstra = new RadarDijkstra(thetas, v, track);

		Jeu j = new Jeu(v, track, null, radarDijkstra, null);

		Strategy strategy = new StrategyCloneAdvance(j,
				radarDijkstra, allCom, 30);

		Simulation simu = new Simulation(v, track, strategy);
		j.setStrategy(strategy);
		j.setSimulation(simu);

		JFrame fenetre = new JFrame("Simulation LI260 (" + filename + ")");

		IHMSwing ihm = new IHMSwing();
		ihm.add(new CircuitObserver(track));
		ihm.add(new RadarObserver(v, radarDijkstra));
		ihm.add(new ItineraireObserver(simu));
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
			j.getSimulation().play();
		} catch (VoitureException e) {
			e.printStackTrace();
		}
	}
}
