package li260.ihm.usercontrol;

import javax.swing.JCheckBox;

import li260.algo.Radar;
import li260.algo.RadarClassique;
import li260.algo.RadarDijkstra;
import li260.ihm.controller.Controleur;
import li260.ihm.observer.ItineraireObserver;
import li260.ihm.observer.MapsObserver;
import li260.ihm.observer.ObstaclesObserver;
import li260.ihm.observer.ArrowObserver;
import li260.ihm.observer.PointeurObserver;
import li260.ihm.observer.PointsObserverAdv;
import li260.ihm.observer.PointsObserverPNG;
import li260.ihm.observer.RadarObserver;
import li260.ihm.observer.SimulationObserver;
import li260.ihm.observer.TrajectoireObserver;
import li260.ihm.observer.VoitureObserverPNG;
import li260.ihm.observer.ZoneObserver;
import li260.optimisation.CommandeFactory;
import li260.simulation.Simulation;
import li260.strategy.Strategy;
import li260.strategy.StrategyCloneAdvance;
import li260.strategy.StrategyLimiteur;
import li260.strategy.StrategyPointAPoint;
import li260.strategy.StrategyRadar;
import li260.strategy.StrategyRegulateur;
import li260.strategy.StrategySelector;
import li260.strategy.StrategySelectorImpl;
import li260.strategy.StrategyToutDroit;
import li260.strategy.selector.SelectorEndLine;
import li260.strategy.selector.SelectorPointAPoint;
import li260.strategy.selector.SelectorRadar;
import li260.strategy.selector.SelectorZone;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.factory.FerrariFactory;

public class PanelInterfaceMaps extends PanelInterface {

	private static final long serialVersionUID = 1L;

	public PanelInterfaceMaps(Controleur controller) {
		super(controller);
	}

	public void launch() {
		g = controller.getGame();
		// attention à l'ordre
		FerrariFactory f = new FerrariFactory(g.getCircuit());
		Voiture v = f.build();
		g.setVoiture(v);
		StrategySelector strat = new StrategySelectorImpl();
		g.setStrategy(strat);
		Simulation simu = new Simulation(g.getVoiture(), g.getCircuit(), strat);
		g.setSimulation(simu);
		simu.add(ihmSwing);

		double[] thetas = CommandeFactory.generateThetas(radarCount.getValue(),
				(radarAngle.getValue() / 10.0));
		Commande[] allCom = CommandeFactory.generateAllComm(thetas, 1);

		Radar r = null;
		g.setRadar(r);

		/* suppression des anciens observeurs de l'ihm */
		ihmSwing.init();
		/* ajout des nouveaux observeurs de l'ihm */
		ihmSwing.add(new MapsObserver(g.getCircuit()));
		ihmSwing.add(new ObstaclesObserver(listeObstacles));
		ihmSwing.add(new PointsObserverPNG(listePoints));
		ihmSwing.add(new ZoneObserver(listeZones));

		/* initialisation des stratégies en fonction du menu */
		for (JCheckBox b : controlPanel.getMenuStrat().getListStrategy()) {
			if (b.isSelected()) {
				if (b.getText().compareTo("Radar") == 0) {
					r = new RadarClassique(thetas, g.getVoiture(),
							g.getCircuit());
					g.setRadar(r);
					strat.add(new StrategyRadar(r, allCom, g.getVoiture()),
							new SelectorRadar());
				}
				if (b.getText().compareTo("Dijkstra") == 0) {
					r = new RadarDijkstra(thetas, g.getVoiture(),
							g.getCircuit());
					g.setRadar(r);
					strat.add(new StrategyRadar(r, allCom, g.getVoiture()),
							new SelectorRadar());
				}

				if (b.getText().compareTo("Arrivée") == 0) {
					strat.add(new StrategyToutDroit(),
							new SelectorEndLine(g.getVoiture(), g.getCircuit()));
				}
				if (b.getText().compareTo("Clone") == 0) {
					strat.add(
							new StrategyCloneAdvance(g, new RadarDijkstra(
									thetas, g.getVoiture(), g.getCircuit()),
									CommandeFactory.generateAllCommCustom(
											thetas, 1., 1., 1., 1., 1.), 30),
							new SelectorZone(listeZones, g.getVoiture()));
				}
				if (b.getText().compareTo("Points") == 0) {
					StrategyPointAPoint spap = new StrategyPointAPoint(
							g.getVoiture(), listePoints);
					strat.add(
							new StrategyRegulateur(spap, 1),
							new SelectorPointAPoint(spap, g.getVoiture(), g
									.getCircuit()));
					ihmSwing.add(new PointsObserverAdv(spap));
				}
				if (b.getText().compareTo("Piéton") == 0) {
					Strategy deco = new StrategyLimiteur(strat, g.getVoiture(),
							0.2);
					g.setStrategy(deco);
					g.getSimulation().setStrategy(deco);
				}

			}
		}

		/* initialisation des observateurs en fonction du menu */
		ihmSwing.add(new SimulationObserver(g.getSimulation(), g.getVoiture()));

		for (JCheckBox b : controlPanel.getMenuObs().getListCamera()) {
			if (b.isSelected()) {
				if (b.getText().compareTo("Trajectoire") == 0) {
					ihmSwing.add(new TrajectoireObserver(g.getSimulation()));
				}
				if (b.getText().compareTo("Itinéraire") == 0) {
					ihmSwing.add(new ItineraireObserver(g.getSimulation()));
				}
				if (b.getText().compareTo("Radar") == 0) {
					if (g.getRadar() != null) {
						ihmSwing.add(new RadarObserver(g.getVoiture(), g
								.getRadar()));
					}
				}
				if (b.getText().compareTo("Voiture") == 0) {
					ihmSwing.add(new VoitureObserverPNG(g.getVoiture()));
				}
				if (b.getText().compareTo("Pointeur") == 0) {
					ihmSwing.add(new PointeurObserver(g.getVoiture()));
				}
				if (b.getText().compareTo("Flèche") == 0) {

					ihmSwing.add(new ArrowObserver(g.getVoiture()));
				}
			}
		}

		Thread t = new Thread() {
			public void run() {
				g.play();
			}
		};
		t.start();

	}
}
