package li260.circuit;

public interface CircuitModifiable extends Circuit {
	public void setTerrain(int i, int j, Terrain t);

	public void majDijkstra();
}
