package li260.strategy;

import java.io.Serializable;

import li260.circuit.Circuit;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public interface Strategy extends Serializable{
	public Commande getCommande();
	public void init(Voiture v, Circuit c);	
}
