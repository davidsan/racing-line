package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.tools.PngTools;
import li260.voiture.Voiture;

public class VoitureObserverPNG implements ObserverSwing {

	private Voiture voiture;
	private Color color = Color.yellow;
	private Color colorDerape = Color.red;

	public VoitureObserverPNG(Voiture v) {
		this.voiture = v;
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

	@Override
	public void print(Graphics g) {
		g.setColor(getColor());
		BufferedImage car = (BufferedImage) PngTools.loadPng("./cars/car.png");
		double angle = VTools.angle(voiture.getDirection(), new Vecteur(0, 1));
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, (car.getWidth() / 2), (car.getHeight() / 2));
		AffineTransformOp op = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BICUBIC);
		car = op.filter(car, null);
		g.drawImage(car, getY() - 25, getX() - 25, null);

	}

}
