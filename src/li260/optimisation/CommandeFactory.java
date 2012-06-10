package li260.optimisation;

import li260.voiture.Commande;

public class CommandeFactory {
	public static Commande[] generateAllComm(double[] thetas, double vitesse) {
		Commande[] allCom = new Commande[thetas.length];
		for (int i = 0; i < allCom.length; i++) {
			allCom[i] = generateOneComm(thetas[i], vitesse);
		}
		return allCom;
	}

	public static Commande generateOneComm(double theta, double vitesse) {
		Commande com = null;
		if (Math.abs(theta) > Math.PI / 2) {
			com = new Commande(-1., Math.signum(theta) * 1);
		} else {
			if (Math.abs(theta) > Math.PI / 4) {
				com = new Commande(-1., Math.signum(theta) * Math.cos(theta));
			} else {
				if (Math.abs(theta) > Math.PI / 6) {
					com = new Commande(-1., Math.signum(theta)
							* Math.cos(theta));
				} else {
					if (Math.abs(theta) > Math.PI / 9) {
						com = new Commande(-1., Math.signum(theta)
								* Math.cos(theta));
					} else {
						if (Math.abs(theta) > Math.PI / 12) {
							com = new Commande(-1, Math.signum(theta)
									* Math.cos(theta));
						} else {
							com = new Commande(vitesse, Math.signum(theta)
									* Math.cos(theta));
						}
					}
				}
			}
		}
		return com;
	}

	public static Commande[] generateAllCommCustom(double[] thetas, double v1,
			double v2, double v3, double v4, double v5) {
		Commande[] allCom = new Commande[thetas.length];
		for (int i = 0; i < allCom.length; i++) {
			if (Math.abs(thetas[i]) > Math.PI / 4) {
				allCom[i] = new Commande(v1, Math.signum(thetas[i])
						* Math.cos(thetas[i]));
			} else {
				if (Math.abs(thetas[i]) > Math.PI / 6) {
					allCom[i] = new Commande(v2, Math.signum(thetas[i])
							* Math.cos(thetas[i]));
				} else {
					if (Math.abs(thetas[i]) > Math.PI / 9) {
						allCom[i] = new Commande(v3, Math.signum(thetas[i])
								* Math.cos(thetas[i]));
					} else {
						if (Math.abs(thetas[i]) > Math.PI / 20) {
							allCom[i] = new Commande(v4, Math.signum(thetas[i])
									* Math.cos(thetas[i]));
						} else {
							allCom[i] = new Commande(v5, Math.signum(thetas[i])
									* Math.cos(thetas[i]));
						}
					}
				}
			}
		}
		return allCom;
	}

	public static double[] generateThetas(int size, double angleVision) {
		double[] thetas = new double[size];
		double delta = (angleVision - Math.PI / 64) / size;
		thetas[0] = 0.;
		for (int i = 1; i < size; i++) {
			thetas[i] = -angleVision + i * delta;
			if (i % 2 == 0) {
				thetas[i] *= -1;
			}
		}
		// for (int i = 0; i < size/2; i++) {
		// thetas[i] = (-angleVision)+delta*i;
		// thetas[thetas.length-i-1]=(angleVision)-delta*i;
		// }
		return thetas;
	}
}
