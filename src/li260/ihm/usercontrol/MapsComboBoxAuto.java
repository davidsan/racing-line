package li260.ihm.usercontrol;

import javax.swing.JComboBox;

import li260.ihm.controller.Controleur;

public class MapsComboBoxAuto extends JComboBox {

	private static final long serialVersionUID = 1L;

	public MapsComboBoxAuto(String[] files, Controleur controller) {
		super(files);
		addItemListener(controller);
		setFocusable(false);
		setSelectedIndex(-1);
		setFont(getFont().deriveFont((float) 14));

	}
}
