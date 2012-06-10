package li260.ihm.observer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import li260.circuit.Circuit;
import li260.circuit.ToolsTerrain;

public class CircuitObserver implements ObserverSwing {

	private BufferedImage trackIm;

	public CircuitObserver(Circuit track) {
		super();
		trackIm = ToolsTerrain.imageFromCircuit(track);
	}

	@Override
	public void print(Graphics g) {
		g.drawImage(trackIm, 0, 0, null);
	}

}
