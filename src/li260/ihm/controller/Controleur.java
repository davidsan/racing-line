package li260.ihm.controller;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import li260.circuit.CircuitModifiable;
import li260.circuit.factory.CircuitFactory;
import li260.ihm.events.UpdateEventListener;
import li260.ihm.observer.CircuitObserver;
import li260.ihm.observer.ObstaclesObserver;
import li260.ihm.observer.PointsObserverPNG;
import li260.ihm.observer.ZoneObserver;
import li260.ihm.usercontrol.PanelInterface;
import li260.jeu.Jeu;

public class Controleur implements UpdateEventListener, ItemListener {
	
	protected Jeu game;
	protected PanelInterface panel;

	public Controleur() {
		game = new Jeu();
	}

	@Override
	public void manageUpdate() {
		game.update();
	}

	@Override
	public void itemStateChanged(final ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			game.interrupt();
			panel.getControlPanel().disableAllBtn();
			Thread t = new Thread() {
				public void run() {
					String filename = PanelInterface.pathToTrack
							+ e.getItem().toString();
					CircuitFactory cF = new CircuitFactory(filename);
					CircuitModifiable track = cF.build();
					game.setCircuit(track);
					panel.clearListePoints();
					panel.clearListeObstacles();
					panel.clearListeZones();
					panel.getIhmSwing().setSize(
							new Dimension(track.getWidth(), track.getHeight()));

					panel.getIhmSwing().add(
							new CircuitObserver(game.getCircuit()));
					panel.getIhmSwing().add(
							new PointsObserverPNG(panel.getListePoints()));
					panel.getIhmSwing().add(
							new ObstaclesObserver(panel.getListeObstacles()));
					panel.getIhmSwing().add(
							new ZoneObserver(panel.getListeZones()));

					panel.getIhmSwing().manageUpdate();
					panel.getControlPanel().getBtnObstacle().setEnabled(true);
					panel.getControlPanel().getBtnPoint().setEnabled(true);
					panel.getControlPanel().getBtnSpeedBump().setEnabled(true);
					panel.getControlPanel().getBtnUndoZone().setEnabled(true);
					panel.getControlPanel().getBtnDeleteZone().setEnabled(true);
					panel.getControlPanel().getBtnUndoPoint().setEnabled(true);
					panel.getControlPanel().getBtnDeletePoints()
							.setEnabled(true);
					panel.getControlPanel().getBtnStart().setEnabled(true);
				}
			};
			t.start();
			return;
		}
	}

	public Jeu getGame() {
		return game;
	}

	public void setGame(Jeu game) {
		this.game = game;
	}

	public PanelInterface getPanel() {
		return panel;
	}

	public void setPanel(PanelInterface panel) {
		this.panel = panel;
	}

}
