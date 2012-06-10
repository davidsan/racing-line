package li260.jeu;

import li260.algo.Radar;
import li260.circuit.CircuitModifiable;
import li260.ihm.events.UpdateEventListener;
import li260.ihm.events.UpdateEventSender;
import li260.simulation.Simulation;
import li260.strategy.Strategy;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;

public class Jeu implements UpdateEventSender {

	private Voiture v;
	private CircuitModifiable track;
	private Strategy s;
	private Radar r;
	private Simulation simu;

	public Jeu() {
		super();
		this.v = null;
		this.track = null;
		this.s = null;
		this.r = null;
		this.simu = null;
	}

	public Jeu(Voiture v, CircuitModifiable c, Strategy s, Radar r,
			Simulation simu) {
		super();
		this.v = v;
		this.track = c;
		this.s = s;
		this.r = r;
		this.simu = simu;
	}

	public Voiture getVoiture() {
		return v;
	}

	public void setVoiture(Voiture v) {
		this.v = v;
	}

	public CircuitModifiable getCircuit() {
		return track;
	}

	public void setCircuit(CircuitModifiable track) {
		this.track = track;
	}

	public Strategy getStrategy() {
		return s;
	}

	public void setStrategy(Strategy s) {
		this.s = s;
	}

	public Radar getRadar() {
		return r;
	}

	public void setRadar(Radar r) {
		this.r = r;
	}

	public Simulation getSimulation() {
		return simu;
	}

	public void setSimulation(Simulation simu) {
		this.simu = simu;
	}

	@Override
	public void add(UpdateEventListener listener) {
		simu.add(listener);
	}

	@Override
	public void update() {
		simu.update();
	}

	public void play() {
		Thread t = new Thread() {
			public void run() {
				try {
					simu.play();
				} catch (VoitureException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public void interrupt() {
		if (simu != null && simu.isRunning()) {
			simu.interrupt();
		}
	}

}
