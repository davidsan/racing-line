package li260.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import li260.algo.Dijkstra;

public class DijkstraTools {
	public static void saveDijkstra(Dijkstra dijkstra, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			GZIPOutputStream gzos = new GZIPOutputStream(fos);
			ObjectOutputStream os = new ObjectOutputStream(gzos);
			os.writeObject(dijkstra.getDist());
			os.close();
			gzos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Dijkstra loadDijkstra(String filename) throws IOException {
		Dijkstra dijkstra = null;
		FileInputStream fis = new FileInputStream(filename);
		GZIPInputStream gzis = new GZIPInputStream(fis);
		ObjectInputStream os = new ObjectInputStream(gzis);
		try {
			dijkstra = new Dijkstra((double[][]) os.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dijkstra;
	}
}
