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
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.PointsObserverAdv;
import li260.ihm.observer.TrajectoireObserver;
import li260.ihm.observer.PointeurObserver;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.StrategyPointAPoint;
import li260.strategy.StrategyRadar;
import li260.strategy.StrategySelectorImpl;
import li260.strategy.StrategyToutDroit;
import li260.strategy.StrategyIterate;
import li260.strategy.selector.SelectorEndLine;
import li260.strategy.selector.SelectorPointAPoint;
import li260.strategy.selector.SelectorRadar;
import li260.strategy.selector.SelectorIterate;
import li260.tools.CommandeTools;
import li260.tools.VecteurTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;
import li260.voiture.factory.FerrariFactory;

public class TestPointAPoint {

	public static void main(String[] args) {

		double[] thetas = CommandeFactory.generateThetas(400, 0.3);
		Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);

		String name = "1_safe";
		String filename = "track/" + name + ".trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit track = cF.build();
		FerrariFactory f = new FerrariFactory(track);
		Voiture v = f.build();
		ArrayList<Vecteur> listePoints = null;
		try {
			listePoints = VecteurTools.loadListeVecteur("tcoms/" + name
					+ ".pos");
			System.out.println(listePoints.size() + " points lus");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Radar r = new RadarDijkstra(thetas, v, track);

		StrategySelectorImpl strategy = new StrategySelectorImpl();
		StrategyPointAPoint PaP = new StrategyPointAPoint(v, listePoints);
		strategy.add(new StrategyIterate(1., 0.09), new SelectorIterate(40));
		strategy.add(PaP, new SelectorPointAPoint(PaP, v, track));
		strategy.add(new StrategyToutDroit(), new SelectorEndLine(v, track));
		strategy.add(new StrategyRadar(r, allCom, v), new SelectorRadar());
		// strategy.add(new StrategyDoNothing(), new SelectorRadar());
		Simulation simu = new Simulation(v, track, strategy);

		JFrame fenetre = new JFrame("Simulation LI260 (" + filename + ")");

		IHMSwing ihm = new IHMSwing();
		ihm.add(new CircuitObserver(track));
		ihm.add(new TrajectoireObserver(simu));
		// ihm.add(new VoitureObserveurSwingModele(v));
		ihm.add(new PointeurObserver(v));
		// ihm.add(new RadarObserveur(v, r));
		ihm.add(new PointsObserverAdv(PaP));
		simu.add(ihm);

		fenetre.setResizable(false);
		fenetre.setLayout(new BorderLayout());

		// fenetre.getContentPane().add(new ControlPanel(), BorderLayout.EAST);
		// fenetre.getContentPane().add(new MapsCombo(), BorderLayout.SOUTH);
		fenetre.getContentPane().add(ihm, BorderLayout.CENTER);

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);

		// try {
		//
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// SwingUtilities.updateComponentTreeUI(fenetre);
		// // force chaque composant de la fenêtre à appeler sa méthode
		// // updateUI
		// } catch (InstantiationException e) {
		// } catch (ClassNotFoundException e) {
		// } catch (UnsupportedLookAndFeelException e) {
		// } catch (IllegalAccessException e) {
		// }

		fenetre.setVisible(true);

		try {
			simu.play();
		} catch (VoitureException e1) {
			e1.printStackTrace();
		}

		CommandeTools.saveListeCommande(simu.getRecord(), "tcoms/pap" + name
				+ ".com");

		System.err.println("Nombre d'iterations : " + simu.getRecord().size()
				+ " " + simu.getState());

	}

}
