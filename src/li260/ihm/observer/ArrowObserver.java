package li260.ihm.observer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import li260.geometry.VTools;
import li260.geometry.Vecteur;
import li260.tools.PngTools;
import li260.voiture.Voiture;

public class ArrowObserver implements ObserverSwing {

	private Voiture voiture;
	private Color color = Color.yellow;
	private Color colorDerape = Color.red;

	public ArrowObserver(Voiture v) {
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
		double angle = VTools.angle(voiture.getDirection(), new Vecteur(1, 0));
		BufferedImage img = null;
		angle += Math.PI;
		if (angle < 1 * Math.PI / 8) {
			img = (BufferedImage) PngTools.loadPng("./cars/arrow/en4.png");
		} else {
			if (angle < 2 * Math.PI / 8) {
				img = (BufferedImage) PngTools.loadPng("./cars/arrow/en3.png");
			} else {
				if (angle < 3 * Math.PI / 8) {
					img = (BufferedImage) PngTools
							.loadPng("./cars/arrow/en2.png");
				} else {
					if (angle < 4 * Math.PI / 8) {
						img = (BufferedImage) PngTools
								.loadPng("./cars/arrow/en1.png");
					} else {
						if (angle < 5 * Math.PI / 8) {
							img = (BufferedImage) PngTools
									.loadPng("./cars/arrow/se4.png");
						} else {
							if (angle < 6 * Math.PI / 8) {
								img = (BufferedImage) PngTools
										.loadPng("./cars/arrow/se3.png");
							} else {
								if (angle < 7 * Math.PI / 8) {
									img = (BufferedImage) PngTools
											.loadPng("./cars/arrow/se2.png");
								} else {
									if (angle < 8 * Math.PI / 8) {
										img = (BufferedImage) PngTools
												.loadPng("./cars/arrow/se1.png");
									} else {
										if (angle < 9 * Math.PI / 8) {
											img = (BufferedImage) PngTools
													.loadPng("./cars/arrow/ws4.png");
										} else {
											if (angle < 10 * Math.PI / 8) {
												img = (BufferedImage) PngTools
														.loadPng("./cars/arrow/ws3.png");
											} else {
												if (angle < 11 * Math.PI / 8) {
													img = (BufferedImage) PngTools
															.loadPng("./cars/arrow/ws2.png");
												} else {
													if (angle < 12 * Math.PI / 8) {
														img = (BufferedImage) PngTools
																.loadPng("./cars/arrow/ws1.png");
													} else {
														if (angle < 13 * Math.PI / 8) {
															img = (BufferedImage) PngTools
																	.loadPng("./cars/arrow/nw4.png");
														} else {
															if (angle < 14 * Math.PI / 8) {
																img = (BufferedImage) PngTools
																		.loadPng("./cars/arrow/nw3.png");
															} else {
																if (angle < 15 * Math.PI / 8) {
																	img = (BufferedImage) PngTools
																			.loadPng("./cars/arrow/nw2.png");
																} else {
																	img = (BufferedImage) PngTools
																			.loadPng("./cars/arrow/nw1.png");
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		g.drawImage(img, getY() - 25, getX() - 30, null);

	}

}
