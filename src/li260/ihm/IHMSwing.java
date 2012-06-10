package li260.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import li260.ihm.events.UpdateEventListener;
import li260.ihm.observer.ObserverSwing;

public class IHMSwing extends JPanel implements UpdateEventListener {

	private static final long serialVersionUID = 1L;

	private ArrayList<ObserverSwing> liste;

	public IHMSwing() {
		super();
		liste = new ArrayList<ObserverSwing>();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1024, 768));
		setBackground(Color.DARK_GRAY);
		setVisible(true);
	}

	public void add(ObserverSwing obj) {
		liste.add(obj);
	}

	public void init() {
		liste = new ArrayList<ObserverSwing>();
	}

	@Override
	public void manageUpdate() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});

	}

	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for (ObserverSwing o : liste)
			o.print(g);

	}
}
