package li260.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import li260.strategy.Strategy;

public class StrategyTools {
	public static void saveStrategy(Strategy strat, String filename) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(filename));
			os.writeObject(strat);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Strategy loadStrategy(String filename) throws IOException {
		Strategy strat = null;
		try {
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream oos = new ObjectInputStream(fin);
			strat = (Strategy) oos.readObject();
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("file " + filename
					+ " not found...Returning null.");
		} catch (IOException e) {
			System.out.println("file " + filename
					+ " corrupted...Returning null.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return strat;
	}
}