package li260.ihm;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;
import li260.ihm.events.UpdateEventListener;
import li260.ihm.observer.ObserverImage;

public class IHMImage implements UpdateEventListener {
	private ArrayList<ObserverImage> liste;
	private BufferedImage im;

	public IHMImage(Circuit track) { // INITIALISATION
		liste = new ArrayList<ObserverImage>();
		im = ToolsTerrain.imageFromCircuit(track);
	}

	public void manageUpdate() { // MISE A JOUR
		for (ObserverImage o : liste)
			o.print(im);
	}

	public BufferedImage getImage() {
		return im;
	}

	public void add(ObserverImage obj) {
		liste.add(obj);
	}
}
