package li260.ihm.observer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;

public class MapsObserver implements ObserverSwing {

	private BufferedImage trackIm;

	public MapsObserver(Circuit track) {
		super();
		trackIm = ToolsTerrain.imageFromMaps(track);
	}

	@Override
	public void print(Graphics g) {
		g.drawImage(trackIm, 0, 0, null);
	}

}
