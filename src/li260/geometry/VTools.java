package li260.geometry;

public class VTools {

	public static Vecteur addition(Vecteur a, Vecteur b) {
		return new Vecteur(a.getX() + b.getX(), a.getY() + b.getY());
	}

	public static Vecteur soustraction(Vecteur a, Vecteur b) {
		return new Vecteur(b.getX() - a.getX(), b.getY() - a.getY());
	}

	public static double prodScal(Vecteur a, Vecteur b) {
		return ((a.getX() * b.getX()) + (a.getY() * b.getY()));
	}

	public static Vecteur prodVect(Vecteur a, Vecteur b) {
		double nX, nY;
		nX = (a.getX() * b.getY()) - (a.getY() * b.getX());
		nY = (a.getY() * b.getX()) - (a.getX() * b.getY());
		return new Vecteur(nX, nY);
	}

	public static Vecteur prodDouble(Vecteur v, double d) {
		return new Vecteur(v.getX() * d, v.getY() * d);
	}

	public static Vecteur random() {
		double x, y;
		if ((x = Math.random()) < 0.5) {
			x *= -1;
		}
		if ((y = Math.random()) < 0.5) {
			y *= -1;
		}
		return new Vecteur(x, y);
	}

	public static double norme(Vecteur v) {
		return Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2));
	}

	public static Vecteur normalisation(Vecteur v) {
		double norme = norme(v);
		v.setX(v.getX() / norme);
		v.setY(v.getY() / norme);
		return v;
	}

	public static boolean isOrthogonaux(Vecteur a, Vecteur b) {
		return ((angle(a, b) == (Math.PI / 2)) || (angle(a, b) == -(Math.PI / 2)));
	}

	public static double composanteZ(Vecteur u, Vecteur v) {
		double a = u.getX() * v.getY() - u.getY() * v.getX();
		return Math.signum(a);
	}

	public static double angle(Vecteur a, Vecteur b) {
		double res = composanteZ(a, b)
				* (Math.acos(prodScal(a, b) / (norme(a) * norme(b))));
		if (Double.isNaN(res)) {
			return 0;
		}
		return res;
	}

	public static boolean seCroisent(Vecteur a, Vecteur b) {
		return angle(a, b) != 0 && angle(a, b) != Math.PI;
	}

	public static void rotation(Vecteur v, double angle) {
		double vX = v.getX();
		v.setX(vX * Math.cos(angle) - v.getY() * Math.sin(angle));
		v.setY(vX * Math.sin(angle) + v.getY() * Math.cos(angle));
	}

}
