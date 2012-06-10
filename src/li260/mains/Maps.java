package li260.mains;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import li260.ihm.controller.ControleurMaps;
import li260.ihm.usercontrol.PanelInterfaceMaps;

public class Maps {

	public static void main(String[] args) {
		JFrame windows = new JFrame("LI260 Maps");
		ControleurMaps controle = new ControleurMaps();
		PanelInterfaceMaps ihm = new PanelInterfaceMaps(controle);
		windows.setLayout(new BorderLayout());
		windows.setContentPane(ihm);
		windows.setResizable(false);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.pack();
		windows.setLocationRelativeTo(null);
		windows.setVisible(true);
	}
}
