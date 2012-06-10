package li260.mains;

import li260.circuit.ToolsTerrain;

public class ConvertToPng {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Usage : java -classpath bin li260.mains.ConvertToPng file.png");
			return;
		}
		ToolsTerrain.previsualisation(args[0]);
		System.out.println("Converting " + args[0] + " to PNG done.");
	}
}
