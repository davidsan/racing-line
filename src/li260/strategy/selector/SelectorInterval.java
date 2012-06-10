package li260.strategy.selector;

import li260.simulation.Simulation;

public class SelectorInterval implements Selector {

	private int min;
	private int max;
	private Simulation s;

	public SelectorInterval(Simulation s, int min, int max) {
		super();
		this.s = s;
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean isSelected() {
		int size = s.getRecord().size();
		if (size < max && size >= min) {
			return true;
		}
		return false;
	}

}
