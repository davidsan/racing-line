package li260.geometry;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Vecteur implements Serializable {

	private static final long serialVersionUID = 1L;
	private double x;
	private double y;

	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vecteur(Vecteur v1, Vecteur v2) {
		this(v2.getX() - v1.getX(), v2.getY() - v1.getY());
	}

	public Vecteur(Vecteur v) {
		this(v.getX(), v.getY());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void autoAdd(Vecteur v) {
		x = (x + v.x);
		y = (y + v.y);
	}

	public void autoProdDouble(double d) {
		x = (x * d);
		y = (y * d);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public String toStringDecimal() {
		DecimalFormat f = new DecimalFormat("#0.0");
		return "(" + f.format(x) + ", " + f.format(y) + ")";
	}

	public Vecteur cloneAsVecteur() {
		return new Vecteur(x, y);
	}

	public void toUnitVec() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		double relativeError = 0.001;
		return (Math.abs(other.x - x) < relativeError && Math.abs(other.y - y) < relativeError);
	}

	public boolean equalsPoint(Vecteur other) {
		return equalsZone(other, 3.);
	}

	public boolean equalsZone(Vecteur other, double diameter) {
		Vecteur vect = new Vecteur(this, other);
		return VTools.norme(vect) < diameter;
	}

}
