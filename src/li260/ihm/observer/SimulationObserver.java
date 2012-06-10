package li260.ihm.observer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import li260.simulation.Simulation;
import li260.voiture.Voiture;

public class SimulationObserver implements ObserverSwing {

	private Simulation s;
	private Voiture v;

	public SimulationObserver(Simulation s, Voiture v) {
		this.s = s;
		this.v = v;
	}

	@Override
	public void print(Graphics g) {
		g.setColor(Color.black);
		Font font10 = new Font("SansSerif", Font.BOLD, 10);
		g.setFont(font10);
		DecimalFormat f = new DecimalFormat("#0.000");
		String str = "Vitesse : " + f.format(v.getVitesse()) + "\nDirection : "
				+ v.getDirection().toStringDecimal() + "\nPosition : "
				+ v.getPosition().toStringDecimal() + "\nCoups : "
				+ s.getRecord().size();
		drawString(g, str, 5, g.getClipBounds().height
				- g.getFontMetrics().getHeight() * 4 - 5);
	}

	// handling newline with Graphics2D.drawString
	// http://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

}
