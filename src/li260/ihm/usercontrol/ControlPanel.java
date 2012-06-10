package li260.ihm.usercontrol;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import li260.ihm.controller.Controleur;
import li260.ihm.key.InputManager;
import li260.ihm.mouse.MouseObstacleListener;
import li260.ihm.mouse.MousePositionListener;
import li260.voiture.VoitureException;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MenuObsJCheckBox menuObs;
	private MenuStratJCheckBox menuStrat;

	private JButton btnSpeedBump;
	private JButton btnUndoZone;
	private JButton btnDeleteZone;
	private JButton btnObstacle;
	private JButton btnPoint;
	private JButton btnUndoPoint;
	private JButton btnDeletePoints;
	private JButton btnPilote;
	private JButton btnStart;
	private JButton btnPause;
	private JButton btnStop;

	public ControlPanel(final Controleur controller) {
		super();
		Border loweredetched = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title;

		JPanel containerListStrat = new JPanel();
		containerListStrat.setLayout(new BorderLayout());
		menuStrat = new MenuStratJCheckBox(controller);
		containerListStrat.add(menuStrat, BorderLayout.CENTER);
		title = BorderFactory.createTitledBorder(loweredetched, "Stratégies");
		containerListStrat.setBorder(title);

		JPanel containerListObs = new JPanel();
		containerListObs.setLayout(new BorderLayout());
		menuObs = new MenuObsJCheckBox(controller);
		containerListObs.add(menuObs, BorderLayout.CENTER);
		title = BorderFactory
				.createTitledBorder(loweredetched, "Visualisation");
		containerListObs.setBorder(title);

		JPanel gridButtons = new JPanel();
		gridButtons.setLayout(new GridLayout(8, 2));

		btnSpeedBump = new JButton("Zone", new ImageIcon(
				"icons/fugue/layer--plus.png"));
		btnSpeedBump
				.setToolTipText("Ajouter des zones (un clic sur la carte ajoute une zone)");
		btnSpeedBump.setMnemonic(KeyEvent.VK_L);
		btnSpeedBump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MouseListener[] listeners = controller.getPanel().getIhmSwing()
						.getMouseListeners();
				for (MouseListener m : listeners) {
					controller.getPanel().getIhmSwing().removeMouseListener(m);
				}
				controller
						.getPanel()
						.getIhmSwing()
						.addMouseListener(
								new MousePositionListener(controller.getPanel()
										.getListeZones(), controller.getPanel()
										.getIhmSwing()));
				btnSpeedBump.setEnabled(false);
				btnObstacle.setEnabled(true);
				btnPoint.setEnabled(true);
			}
		});

		btnUndoZone = new JButton("Annuler zone", new ImageIcon(
				"icons/fugue/layer--minus.png"));
		btnUndoZone.setToolTipText("Annuler la dernière zone");
		btnUndoZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getPanel().undoListeZones();
				controller.getPanel().getIhmSwing().manageUpdate();
			}
		});

		btnDeleteZone = new JButton("Suppr. tous", new ImageIcon(
				"icons/fugue/layer--exclamation.png"));
		btnDeleteZone.setToolTipText("Supprimer toutes les zones");
		btnDeleteZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getPanel().clearListeZones();
				controller.getPanel().getIhmSwing().manageUpdate();
			}
		});

		btnObstacle = new JButton("Obstacles", new ImageIcon(
				"icons/fugue/traffic-cone--plus.png"));
		btnObstacle
				.setToolTipText("Ajouter des obstacles (un clic sur la carte ajoute un obstacle)");
		btnObstacle.setMnemonic(KeyEvent.VK_O);
		btnObstacle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MouseListener[] listeners = controller.getPanel().getIhmSwing()
						.getMouseListeners();
				for (MouseListener m : listeners) {
					controller.getPanel().getIhmSwing().removeMouseListener(m);
				}
				controller
						.getPanel()
						.getIhmSwing()
						.addMouseListener(
								new MouseObstacleListener(controller.getPanel()
										.getListeObstacles(), controller
										.getGame(), controller.getPanel()
										.getIhmSwing()));
				btnObstacle.setEnabled(false);
				btnPoint.setEnabled(true);
				btnSpeedBump.setEnabled(true);
			}
		});

		btnPoint = new JButton("Points", new ImageIcon(
				"icons/fugue/marker--plus.png"));
		btnPoint.setToolTipText("Ajouter des points (un clic sur la carte ajoute un checkpoint)");
		btnPoint.setMnemonic(KeyEvent.VK_P);
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MouseListener[] listeners = controller.getPanel().getIhmSwing()
						.getMouseListeners();
				for (MouseListener m : listeners) {
					controller.getPanel().getIhmSwing().removeMouseListener(m);
				}
				controller
						.getPanel()
						.getIhmSwing()
						.addMouseListener(
								new MousePositionListener(controller.getPanel()
										.getListePoints(), controller
										.getPanel().getIhmSwing()));
				btnPoint.setEnabled(false);
				btnObstacle.setEnabled(true);
				btnSpeedBump.setEnabled(true);
			}
		});

		btnUndoPoint = new JButton("Annuler pt.", new ImageIcon(
				"icons/fugue/marker--minus.png"));
		btnUndoPoint.setToolTipText("Annuler le dernier point");
		btnUndoPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getPanel().undoListePoints();
				controller.getPanel().getIhmSwing().manageUpdate();
			}
		});

		btnDeletePoints = new JButton("Suppr. tous", new ImageIcon(
				"icons/fugue/marker--exclamation.png"));
		btnDeletePoints.setToolTipText("Supprimer tout les points");
		btnDeletePoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getPanel().clearListePoints();
				controller.getPanel().getIhmSwing().manageUpdate();
			}
		});

		btnPilote = new JButton("Piloter", new ImageIcon(
				"icons/fugue/keyboard-full.png"));
		btnPilote.setMnemonic(KeyEvent.VK_G);
		btnPilote.setToolTipText("Piloter la voiture (touche ZQSD)");
		btnPilote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getPanel().getIhmSwing().requestFocus();
				controller
						.getPanel()
						.getIhmSwing()
						.addKeyListener(
								new InputManager(controller.getGame(),
										controller.getPanel().getIhmSwing()));
				Thread t = new Thread() {
					public void run() {
						try {
							controller.getGame().getSimulation().playWait();
						} catch (VoitureException e) {
							e.printStackTrace();
						}
					}
				};
				t.start();
			}
		});

		btnStart = new JButton("Démarrer", new ImageIcon(
				"icons/fugue/control.png"));
		btnStart.setMnemonic(KeyEvent.VK_D);
		btnStart.setToolTipText("Démarrer la course");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPilote.setEnabled(false);
				controller.getGame().interrupt();
				controller.getPanel().launch();
				btnPause.setEnabled(true);
				btnStop.setEnabled(true);
			}
		});

		btnPause = new JButton("Pause", new ImageIcon(
				"icons/fugue/control-pause.png"));
		btnPause.setMnemonic(KeyEvent.VK_S);
		btnPause.setToolTipText("Mettre en pause/Reprendre la course");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.getGame().getSimulation() == null) {
					return;
				}
				if (controller.getGame().getSimulation().isPlaying())
					if (!controller.getGame().getSimulation().isRunning()) {
						btnStart.setEnabled(false);
						btnPilote.setEnabled(false);
						btnPause.setText("Pause");
						controller.getGame().play();
					} else {
						btnStart.setEnabled(true);
						btnPilote.setEnabled(true);
						btnPause.setText("Reprendre");
						controller.getGame().interrupt();
					}
				return;
			}
		});

		btnStop = new JButton("Arrêter", new ImageIcon(
				"icons/fugue/control-stop-square.png"));
		btnStop.setMnemonic(KeyEvent.VK_R);
		btnStop.setToolTipText("Arrêter la course");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getGame().interrupt();
				// btnSpeedBump.setEnabled(true);
				// btnObstacle.setEnabled(false);
				// btnPoint.setEnabled(true);
				btnUndoZone.setEnabled(true);
				btnDeleteZone.setEnabled(true);
				btnUndoPoint.setEnabled(true);
				btnDeletePoints.setEnabled(true);
				btnPilote.setEnabled(true);
				btnStart.setEnabled(true);
				btnPause.setEnabled(false);
				btnStop.setEnabled(false);
			}
		});

		disableAllBtn();
		gridButtons.add(btnSpeedBump);
		gridButtons.add(btnUndoZone);

		gridButtons.add(btnDeleteZone);
		gridButtons.add(btnObstacle);

		gridButtons.add(btnPoint);
		gridButtons.add(btnUndoPoint);

		gridButtons.add(btnDeletePoints);

		gridButtons.add(btnStart);
		gridButtons.add(btnPause);

		gridButtons.add(btnPilote);
		gridButtons.add(btnStop);

		JLabel footer = new JLabel("<html><p align=right>LI260</p></html>");
		footer.setFont(getFont().deriveFont((float) 12));
		footer.setHorizontalAlignment(SwingConstants.RIGHT);

		JPanel containerGridButtons = new JPanel();
		containerGridButtons.setLayout(new BorderLayout());
		containerGridButtons.add(gridButtons, BorderLayout.NORTH);
		containerGridButtons.add(footer, BorderLayout.SOUTH);

		JPanel containerCheckBox = new JPanel();
		containerCheckBox.setLayout(new BorderLayout());
		containerCheckBox.add(containerListStrat, BorderLayout.NORTH);
		containerCheckBox.add(containerListObs, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(containerCheckBox, BorderLayout.NORTH);
		this.add(containerGridButtons, BorderLayout.CENTER);
	}

	public MenuObsJCheckBox getMenuObs() {
		return menuObs;
	}

	public MenuStratJCheckBox getMenuStrat() {
		return menuStrat;
	}

	public JButton getBtnSpeedBump() {
		return btnSpeedBump;
	}

	public JButton getBtnUndoZone() {
		return btnUndoZone;
	}

	public JButton getBtnDeleteZone() {
		return btnDeleteZone;
	}

	public JButton getBtnObstacle() {
		return btnObstacle;
	}

	public JButton getBtnPoint() {
		return btnPoint;
	}

	public JButton getBtnUndoPoint() {
		return btnUndoPoint;
	}

	public JButton getBtnDeletePoints() {
		return btnDeletePoints;
	}

	public JButton getBtnPilote() {
		return btnPilote;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public JButton getBtnPause() {
		return btnPause;
	}

	public JButton getBtnStop() {
		return btnStop;
	}

	public void disableAllBtn() {
		btnSpeedBump.setEnabled(false);
		btnUndoZone.setEnabled(false);
		btnDeleteZone.setEnabled(false);
		btnStart.setEnabled(false);
		btnPause.setEnabled(false);
		btnStop.setEnabled(false);
		btnObstacle.setEnabled(false);
		btnPoint.setEnabled(false);
		btnDeletePoints.setEnabled(false);
		btnUndoPoint.setEnabled(false);
		btnPilote.setEnabled(false);
	}
}
