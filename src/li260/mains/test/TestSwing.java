package li260.mains.test;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import li260.algo.Radar;
import li260.algo.RadarDijkstra;
import li260.circuit.Circuit;
import li260.circuit.factory.CircuitFactory;
import li260.geometry.Vecteur;
import li260.ihm.IHMSwing;
import li260.ihm.mouse.MousePositionListener;
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.PointsObserverAdv;
import li260.ihm.observer.RadarObserver;
import li260.ihm.observer.SimulationObserver;
import li260.ihm.observer.TrajectoireObserver;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.StrategyPointAPoint;
import li260.strategy.StrategyRadar;
import li260.strategy.StrategySelectorImpl;
import li260.strategy.selector.SelectorPointAPoint;
import li260.strategy.selector.SelectorRadar;
import li260.tools.VecteurTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestSwing {

	public static void main(String[] args) {

		double[] thetas = CommandeFactory.generateThetas(10, Math.PI / 3);
		Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);
		String name = "2_safe";
		String filename = "track/" + name + ".trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit track = cF.build();
		// Circuit trackRaw = cF.build();
		// Tools.preProcess((CircuitImpl) trackRaw, (CircuitImpl) track);

		FerrariFactory f = new FerrariFactory(track);
		Voiture v = f.build();
		Radar r = null;

		// r = new RadarClassique(thetas, v, track);

		r = new RadarDijkstra(thetas, v, track);
		StrategySelectorImpl strategy = new StrategySelectorImpl();
		Simulation simu = new Simulation(v, track, strategy);
		// strategy.add(new StrategyUTurn(1, 0.08), new SelectorUTurn(20));
		ArrayList<Vecteur> listePoints = null;
		try {
			listePoints = VecteurTools.loadListeVecteur("tcoms/" + name
					+ ".pos");
		} catch (IOException e) {
			e.printStackTrace();
		}
		StrategyPointAPoint pap = new StrategyPointAPoint(v, listePoints);
		strategy.add(pap, new SelectorPointAPoint(pap, v, track));
		strategy.add(new StrategyRadar(r, allCom, v), new SelectorRadar());

		JFrame fenetre = new JFrame("Simulation LI260 (" + filename + ")");

		IHMSwing ihm = new IHMSwing();
		MousePositionListener mpl = new MousePositionListener(listePoints, ihm);
		ihm.addMouseListener(mpl);
		ihm.add(new CircuitObserver(track));
		ihm.add(new RadarObserver(v, r));
		ihm.add(new TrajectoireObserver(simu)); /* road lazer */
		ihm.add(new SimulationObserver(simu, v));
		ihm.add(new PointsObserverAdv(pap));
		// ihm.add(new VoitureObserveurModele(v));
		// ihm.add(new VoitureObserveur(v));
		// ihm.add(new DijkObserveurBg(dijk));

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

		/* sauvegarde de la liste des points générés à la souris */
		// VecteurTools.saveListeVecteur(mpl.getListePoints(), "tcoms/" + name
		// + ".pos");
	}
}
