package li260.ihm.usercontrol;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import li260.algo.Radar;
import li260.algo.RadarClassique;
import li260.algo.RadarDijkstra;
import li260.geometry.Vecteur;
import li260.ihm.IHMSwing;
import li260.ihm.controller.Controleur;
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.ItineraireObserver;
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
import li260.jeu.Jeu;
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

public class PanelInterface extends JPanel {

	private static final long serialVersionUID = 1L;
	public static String pathToTrack = "track/";
	protected IHMSwing ihmSwing;
	protected Controleur controller;
	private JComboBox mapsCB;
	protected ControlPanel controlPanel;
	protected Jeu g;
	protected JSlider radarCount;
	protected JSlider radarAngle;
	protected ArrayList<Vecteur> listePoints;
	protected ArrayList<Vecteur> listeObstacles;
	protected ArrayList<Vecteur> listeZones;

	public void clearListeObstacles() {
		listeObstacles.clear();
	}

	public void clearListePoints() {
		listePoints.clear();
	}

	public void undoListePoints() {
		if (listePoints.size() > 0) {
			listePoints.remove(listePoints.size() - 1);
		}
	}

	public void clearListeZones() {
		listeZones.clear();
	}

	public void undoListeZones() {
		if (listeZones.size() > 0) {
			listeZones.remove(listeZones.size() - 1);
		}
	}

	public PanelInterface(Controleur controller) {
		this.controller = controller;
		ihmSwing = new IHMSwing();
		listePoints = new ArrayList<Vecteur>();
		listeObstacles = new ArrayList<Vecteur>();
		listeZones = new ArrayList<Vecteur>();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JPanel ihmConfig = new JPanel();
		ihmConfig.setLayout(new BorderLayout());

		File dir = new File(pathToTrack);
		String[] files;
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".trk");
			}
		};
		files = dir.list(filter);
		Arrays.sort(files);
		mapsCB = new MapsComboBoxAuto(files, controller);
		mapsCB.setBorder(new EmptyBorder(new Insets(10, 0, 10, 0)));

		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BorderLayout());
		mapsPanel.add(mapsCB, BorderLayout.CENTER);

		Border loweredetched = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title;
		title = BorderFactory.createTitledBorder(loweredetched, "Circuit");

		mapsPanel.setBorder(title);

		radarCount = new JSlider(50, 250, 150);
		radarCount.setFont(getFont().deriveFont((float) 10));
		radarCount.setMajorTickSpacing(100);
		radarCount.setMinorTickSpacing(25);
		radarCount.setPaintTicks(true);
		radarCount.setPaintLabels(true);

		radarAngle = new JSlider((int) (Math.PI / 60 * 10),
				(int) (Math.PI * 10), (int) (Math.PI / 2.5 * 10));
		radarAngle.setFont(getFont().deriveFont((float) 10));
		radarAngle.setMajorTickSpacing(5);
		radarAngle.setMinorTickSpacing(1);
		radarAngle.setPaintTicks(true);
		radarAngle.setPaintLabels(true);

		JPanel radarCountPanel = new JPanel();
		radarCountPanel.setLayout(new BorderLayout());
		radarCountPanel.add(radarCount, BorderLayout.CENTER);
		title = BorderFactory.createTitledBorder(loweredetched,
				"Intensité du radar");
		radarCountPanel.setBorder(title);

		JPanel radarAnglePanel = new JPanel();
		radarAnglePanel.setLayout(new BorderLayout());
		radarAnglePanel.add(radarAngle, BorderLayout.CENTER);
		title = BorderFactory.createTitledBorder(loweredetched, "Champ visuel");

		radarAnglePanel.setBorder(title);

		JPanel radarParamPanel = new JPanel();
		radarParamPanel.setLayout(new BorderLayout());
		radarParamPanel.add(radarCountPanel, BorderLayout.NORTH);
		radarParamPanel.add(radarAnglePanel, BorderLayout.SOUTH);

		JPanel topContainer = new JPanel();
		topContainer.setLayout(new BorderLayout());
		topContainer.add(mapsPanel, BorderLayout.NORTH);
		topContainer.add(radarParamPanel, BorderLayout.SOUTH);

		controlPanel = new ControlPanel(controller);
		ihmConfig.add(topContainer, BorderLayout.NORTH);
		ihmConfig.add(controlPanel, BorderLayout.CENTER);
		ihmConfig.setPreferredSize(new Dimension(250, 0));
		ihmConfig.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		this.setLayout(new BorderLayout());
		this.add(ihmSwing, BorderLayout.CENTER);
		this.add(ihmConfig, BorderLayout.EAST);

		add(ihmSwing);
		controller.setPanel(this);
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
		ihmSwing.add(new CircuitObserver(g.getCircuit()));
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

	public IHMSwing getIhmSwing() {
		return ihmSwing;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public ArrayList<Vecteur> getListePoints() {
		return listePoints;
	}

	public ArrayList<Vecteur> getListeObstacles() {
		return listeObstacles;
	}

	public ArrayList<Vecteur> getListeZones() {
		return listeZones;
	}

}
