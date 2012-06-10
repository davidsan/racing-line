package li260.ihm.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import li260.geometry.Vecteur;
import li260.ihm.events.UpdateEventListener;

public class MousePositionListener extends MouseAdapter {

	private ArrayList<Vecteur> listePoints;
	private UpdateEventListener ihm;

	public MousePositionListener(ArrayList<Vecteur> listePoints,
			UpdateEventListener ihm) {
		super();
		this.listePoints = listePoints;
		this.ihm = ihm;
	}

	public void mouseReleased(MouseEvent event) {
		listePoints.add(new Vecteur(event.getY(), event.getX()));
		ihm.manageUpdate();
		// System.out.println("POS : Clic detected @ " + event.getX() + " : "
		// + event.getY());
	}

	public ArrayList<Vecteur> getListePoints() {
		return listePoints;
	}

}
