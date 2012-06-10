package li260.mains.test;

import li260.algo.Dijkstra;
import li260.circuit.Circuit;
import li260.circuit.factory.CircuitFactory;
import li260.tools.DijkstraTools;

public class BuildDijkstra {
	public static void main(String[] args) {

		String name = "t260_safe";
		String filename = "track/" + name + ".trk";
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit track = cF.build();
		Dijkstra dijk = new Dijkstra(track);
		DijkstraTools.saveDijkstra(dijk, "tcoms/dijkstra/" + name + ".dijk");

	}
}