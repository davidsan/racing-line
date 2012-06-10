package li260.mains;

import li260.circuit.ToolsTerrain;

public class ConvertToTrk {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Usage : java -classpath bin li260.mains.ConvertToTrk file.png");
			return;
		}
		String filename = args[0];
		ToolsTerrain.convertToTrk(filename);
		System.out.println("Converting " + filename + " to TRK done.");
	}
}