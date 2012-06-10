package li260.ihm.usercontrol;

import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;

import li260.ihm.controller.Controleur;

public class MapsComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;

	public MapsComboBox(Controleur controller) {

		setBorder(new EmptyBorder(new Insets(5, 10, 0, 0)));
		addItem("1_safe");
		addItem("2_safe");
		addItem("3_safe");
		addItem("4_safe");
		addItem("5_safe");
		addItem("6_safe");
		addItem("7_safe");
		addItem("8_safe");
		addItem("aufeu");
		addItem("bond");
		addItem("Een2");
		addItem("labymod");
		addItem("labyperso");
		addItem("perso");
		addItem("t2009");
		addItem("t260_safe");
		addItemListener(controller);

	}
}
