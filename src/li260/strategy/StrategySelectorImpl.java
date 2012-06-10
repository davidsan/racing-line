package li260.strategy;

import java.util.ArrayList;

import li260.circuit.Circuit;
import li260.strategy.selector.Selector;
import li260.voiture.Commande;
import li260.voiture.Voiture;

public class StrategySelectorImpl implements StrategySelector {

	private static final long serialVersionUID = 1L;
	private ArrayList<Strategy> listeStrategy;
	private ArrayList<Selector> listeSelect;

	public StrategySelectorImpl() {
		listeStrategy = new ArrayList<Strategy>();
		listeSelect = new ArrayList<Selector>();
	}

	public void add(Strategy str, Selector select) {
		listeStrategy.add(str);
		listeSelect.add(select);
	}

	public Commande getCommande() {
		for (int i = 0; i < listeStrategy.size(); i++) {
			if (listeSelect.get(i).isSelected())
				return listeStrategy.get(i).getCommande();
		}
		return new Commande(1, 0);
	}

	public void init(Voiture v, Circuit c) {
	}
}