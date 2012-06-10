package li260.ihm.usercontrol;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import li260.ihm.controller.Controleur;

public class MenuObsJCheckBox extends JPanel {

	private static final long serialVersionUID = 1L;

	public ArrayList<JCheckBox> listCamera;

	public MenuObsJCheckBox(Controleur controller) {
		super();
		listCamera = new ArrayList<JCheckBox>();

		setBorder(new EmptyBorder(new Insets(5, 10, 0, 0)));
		setLayout(new GridLayout(3, 2));
		JCheckBox path = new JCheckBox("Trajectoire");
		JCheckBox routegm = new JCheckBox("Itinéraire");
		JCheckBox radar = new JCheckBox("Radar");
		JCheckBox car = new JCheckBox("Voiture");
		JCheckBox pointer = new JCheckBox("Pointeur");
		JCheckBox arrow = new JCheckBox("Flèche");

		listCamera.add(path);
		listCamera.add(routegm);
		listCamera.add(radar);
		listCamera.add(car);
		listCamera.add(pointer);
		listCamera.add(arrow);
		
		routegm.setSelected(true);
		car.setSelected(true);

		for (JCheckBox obs : listCamera) {
			/* add to MenuObsJCheckBox panel */
			add(obs);
		}
	}

	public ArrayList<JCheckBox> getListCamera() {
		return listCamera;
	}

}
