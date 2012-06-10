package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.voiture.Voiture;

public class PointeurObserver implements ObserverImage, ObserverSwing {

	private Voiture voiture;
	private Color color = Color.black;
	private Color colorDerape = Color.red;

	public PointeurObserver(Voiture voiture) {
		this.voiture = voiture;
	}

	private int getX() {
		return (int) voiture.getPosition().getX();
	}

	private int getY() {
		return (int) voiture.getPosition().getY();
	}

	private Color getColor() {
		if (voiture.getDerapage())
			return colorDerape;
		return color;
	}

	public void print(BufferedImage im) {
		im.setRGB(getY(), getX(), getColor().getRGB());
	}

	public void print(Graphics g) {
		int Height = 45;
		int Width = 15;
		int x = getX();
		int y = getY();
		double A = VTools.angle(new Vecteur(0, 1), voiture.getDirection());
		// int ULx, ULy, URx, URy;
		int BLx, BLy, BRx, BRy;
	
		g.setColor(getColor());

		// ULx = (int) (x + (Width / 2) * Math.cos(A) - (Height / 2) *
		// Math.sin(A));
		// ULy = (int) (y + (Height / 2) * Math.cos(A) + (Width / 2) *
		// Math.sin(A));
		// URx = (int) (x - (Width / 2) * Math.cos(A) - (Height / 2) *
		// Math.sin(A));
		// URy = (int) (y + (Height / 2) * Math.cos(A) - (Width / 2) *
		// Math.sin(A));
		BLx = (int) (x + (Width / 2) * Math.cos(A) + (Height / 2) * Math.sin(A));
		BLy = (int) (y - (Height / 2) * Math.cos(A) + (Width / 2) * Math.sin(A));
		BRx = (int) (x - (Width / 2) * Math.cos(A) + (Height / 2) * Math.sin(A));
		BRy = (int) (y - (Height / 2) * Math.cos(A) - (Width / 2) * Math.sin(A));

		g.drawLine(y, x, BRy, BRx);
		g.drawLine(BRy, BRx, BLy, BLx);
		g.drawLine(BLy, BLx, y, x);
	}
}
