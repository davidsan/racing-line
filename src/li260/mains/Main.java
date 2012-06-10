package li260.mains;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import li260.ihm.controller.Controleur;
import li260.ihm.usercontrol.PanelInterface;

public class Main {

	public static void main(String[] args) {
		JFrame windows = new JFrame("Course de voiture");
		Controleur controle = new Controleur();
		PanelInterface ihm = new PanelInterface(controle);
		windows.setLayout(new BorderLayout());
		windows.setContentPane(ihm);
		windows.setResizable(false);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.pack();
		windows.setLocationRelativeTo(null);
		windows.setVisible(true);
	}
}
