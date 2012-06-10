package li260.ihm.usercontrol;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import li260.ihm.controller.Controleur;

public class MenuStratJCheckBox extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<JCheckBox> listStrategy;

	public MenuStratJCheckBox(Controleur controller) {
		super();

		listStrategy = new ArrayList<JCheckBox>();

		setBorder(new EmptyBorder(new Insets(5, 10, 0, 0)));
		setLayout(new GridLayout(3, 2));
		JCheckBox radar = new JCheckBox("Radar");
		JCheckBox dijkstra = new JCheckBox("Dijkstra");
		JCheckBox clone = new JCheckBox("Clone");
		JCheckBox endline = new JCheckBox("Arrivée");
		JCheckBox points = new JCheckBox("Points");
		JCheckBox pedestrian = new JCheckBox("Piéton");

		dijkstra.setSelected(true);

		listStrategy.add(pedestrian);
		listStrategy.add(points);
		listStrategy.add(endline);
		listStrategy.add(clone);
		listStrategy.add(dijkstra);
		listStrategy.add(radar);

		for (JCheckBox strat : listStrategy) {
			add(strat);
		}
	}

	public ArrayList<JCheckBox> getListStrategy() {
		return listStrategy;
	}

}
