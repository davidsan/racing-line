package li260.strategy.selector;

public class SelectorIterate implements Selector {

	private int i;
	private int iMax;

	public SelectorIterate(int iMax) {
		super();
		this.i = 0;
		this.iMax = iMax;
	}

	@Override
	public boolean isSelected() {
		return i++ < iMax;
	}
}
