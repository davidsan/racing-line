package li260.simulation;

import java.util.ArrayList;

import li260.circuit.Circuit;
import li260.circuit.Terrain;
import li260.circuit.ToolsTerrain;
import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.ihm.events.UpdateEventListener;
import li260.ihm.events.UpdateEventSender;
import li260.strategy.Strategy;
import li260.tools.CommandeTools;
import li260.voiture.Commande;
import li260.voiture.Voiture;
import li260.voiture.VoitureException;

public class Simulation implements UpdateEventSender {

	private Voiture v;
	private Circuit track;
	private Strategy s;
	private EtatSimulation state;
	private ArrayList<Commande> record;
	private ArrayList<UpdateEventListener> liste;
	private ArrayList<Vecteur> recordPos; /* dessin de la trajectoire */
	private ArrayList<Double> recordVit; /*
										 * observateur de trajectoire (vitesse
										 * maximale)
										 */
	private boolean running;

	public Simulation(Voiture v, Circuit track, Strategy s) {
		this.v = v;
		this.track = track;
		this.s = s;
		state = EtatSimulation.Run;
		record = new ArrayList<Commande>();
		liste = new ArrayList<UpdateEventListener>();
		recordPos = new ArrayList<Vecteur>();
		recordVit = new ArrayList<Double>();
	}

	public void play() throws VoitureException {
		running = true;
		while (isPlaying() && running) {
			playOneShot();
			update();
//			if (getRecord().size() > 6555) { // pour éviter les simulations
//												// infinies
//				System.out.println(track.getName() + " : "
//						+ "Course arrêtée après " + getRecord().size()
//						+ " itérations (" + getState() + ")");
//				return;
//			}
		}

		if (!isPlaying() && running) {
			System.out.println(track.getName() + " : " + "Course finie en "
					+ getRecord().size() + " itérations (" + getState() + ")");
		}

		if (getState() == EtatSimulation.Succes) {
			CommandeTools.saveListeCommande(getRecord(),
					"tcoms/" + track.getName() + "_" + getRecord().size()
							+ ".com");
		}
	}

	public void playWait() throws VoitureException {
		running = true;
		while (running) {
			if (v.getVitesse() == 0) {
				return;
			}
			playOneWaitShot();
			update();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void playCommande(Commande c) throws VoitureException {
		record.add(c);
		recordPos.add(v.getPosition().cloneAsVecteur());
		recordVit.add(v.getVitesse());
		v.drive(c);
		state = updateState();
	}

	public void playOneShot() throws VoitureException {
		Commande c = s.getCommande();
		record.add(c);
		recordPos.add(v.getPosition().cloneAsVecteur());
		recordVit.add(v.getVitesse());
		v.drive(c);
		state = updateState();
	}
	
	public void playOneWaitShot() throws VoitureException {
		Commande c = new Commande(0, 0);
		record.add(c);
		recordPos.add(v.getPosition().cloneAsVecteur());
		recordVit.add(v.getVitesse());
		v.drive(c);
		state = updateState();
	}

	private EtatSimulation updateState() {
		Terrain t = track.getTerrain((int) v.getPosition().getX(), (int) v
				.getPosition().getY());
		if (!ToolsTerrain.isRunnable(t)) {
			return EtatSimulation.Echec;

		}
		if (t == Terrain.EndLine
				&& VTools.prodScal(track.getDirectionArrivee(),
						v.getDirection()) <= 0) {
			return EtatSimulation.Echec;
		}
		if (t == Terrain.EndLine
				&& VTools.prodScal(track.getDirectionArrivee(),
						v.getDirection()) > 0) {
			return EtatSimulation.Succes;
		}
		return EtatSimulation.Run;
	}

	public boolean isPlaying() {
		return state == EtatSimulation.Run;
	}

	@Override
	public void update() {
		for (UpdateEventListener listener : liste)
			listener.manageUpdate();
	}

	@Override
	public void add(UpdateEventListener l) {
		liste.add(l);
	}

	public boolean isRunning() {
		return running;
	}

	public void interrupt() {
		/* permet d'interrompre le thread */
		this.running = false;
	}

	public ArrayList<Vecteur> getRecordPos() {
		return recordPos;
	}

	public ArrayList<Commande> getRecord() {
		return record;
	}

	/* affichage main */
	public EtatSimulation getState() {
		return state;
	}

	public Voiture getVoiture() {
		return v;
	}

	public ArrayList<Double> getRecordVit() {
		return recordVit;
	}
	
	public void setStrategy(Strategy s){
		this.s=s;
	}

}
