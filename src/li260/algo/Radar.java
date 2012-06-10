package li260.algo;

import java.io.Serializable;

import li260.circuit.Circuit;
import li260.voiture.Voiture;

public interface Radar extends Serializable{
	public double[] scores(); // score de chaque branche
	public double[] distancesInPixels(); // pour l'observer
	public int getBestIndex(); // meilleur indice
	public double[] thetas(); // angles de chaque faisceau
	public void init(Voiture v, Circuit c);	
}
