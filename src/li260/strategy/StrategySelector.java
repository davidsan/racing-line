package li260.strategy;

import li260.strategy.selector.Selector;

public interface StrategySelector extends Strategy {
	public void add(Strategy str, Selector select);
}