package li260.circuit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import li260.circuit.factory.CircuitFactory;
import li260.circuit.factory.CircuitFactoryImage;

public class ToolsTerrain {

	public static boolean isRunnable(Terrain t) {
		if (t == Terrain.Herbe || t == Terrain.Eau || t == Terrain.Obstacle)
			return false;
		return true;
	}

	public static Terrain terrainFromChar(char c) {
		switch (c) {
		case 'b':
			return Terrain.Eau;
		case '.':
			return Terrain.Route;
		case 'w':
			return Terrain.BandeBlanche;
		case 'r':
			return Terrain.BandeRouge;
		case '*':
			return Terrain.StartPoint;
		case '!':
			return Terrain.EndLine;
		default:
			return Terrain.Herbe;
		}
	}

	public static int colorFromTerrain(Terrain t) {
		switch (t) {
		case Route:
			return Color.gray.getRGB();
		case Herbe:
			return Color.green.getRGB();
		case Eau:
			return Color.blue.getRGB();
		case Obstacle:
			return Color.black.getRGB();
		case BandeBlanche:
			return Color.white.getRGB();
		case BandeRouge:
			return Color.red.getRGB();
		case StartPoint:
			return Color.magenta.getRGB();
		case EndLine:
			return Color.cyan.getRGB();
		default:
			return (Color.orange.getRGB());
		}
	}

	public static int colorFromMaps(Terrain t) {
		switch (t) {
		case Route:
			return new Color(0xf4f3f0).getRGB();
		case Herbe:
			return new Color(0xc9dfaf).getRGB();
		case Eau:
			return new Color(0xa5bfdd).getRGB();
		case Obstacle:
			return new Color(0x248a8a).getRGB();
		case BandeBlanche:
			return new Color(0xffffff).getRGB();
		case BandeRouge:
			return new Color(0xfe857b).getRGB();
		case StartPoint:
			return new Color(0x645848).getRGB();
		case EndLine:
			return new Color(0xffdc34).getRGB();
		default:
			return new Color(0xfaac38).getRGB();
		}
	}
	
	public static Terrain terrainFromColor(int colr) {
		if (colr == Color.gray.getRGB())
			return Terrain.Route;
		if (colr == Color.blue.getRGB())
			return Terrain.Eau;
		if (colr == Color.black.getRGB())
			return Terrain.Obstacle;
		if (colr == Color.white.getRGB())
			return Terrain.BandeBlanche;
		if (colr == Color.red.getRGB())
			return Terrain.BandeRouge;
		if (colr == Color.magenta.getRGB())
			return Terrain.StartPoint;
		if (colr == Color.cyan.getRGB())
			return Terrain.EndLine;
		return Terrain.Herbe;
	}

	public static BufferedImage imageFromCircuit(Circuit c) {
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < c.getHeight(); i++) {
			for (int j = 0; j < c.getWidth(); j++) {
				im.setRGB(j, i, colorFromTerrain(c.getTerrain(i, j)));
			}
		}
		return im;
	}

	public static BufferedImage imageFromMaps(Circuit c) {
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < c.getHeight(); i++) {
			for (int j = 0; j < c.getWidth(); j++) {
				im.setRGB(j, i, colorFromMaps(c.getTerrain(i, j)));
			}
		}
		return im;
	}

	public static void previsualisation(String filename) {
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit c = cF.build();
		BufferedImage im = imageFromCircuit(c);
		try {
			ImageIO.write(im, "png", new File(filename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static char charFromTerrain(Terrain t) {
		switch (t) {
		case Route:
			return '.';
		case Herbe:
			return 'g';
		case Eau:
			return 'b';
		case Obstacle:
			return '%';
		case BandeBlanche:
			return 'w';
		case BandeRouge:
			return 'r';
		case StartPoint:
			return '*';
		case EndLine:
			return '!';
		default:
			return '.'; /* herbe */
		}
	}

	public static void convertToTrk(String filename) {
		CircuitFactoryImage cF = new CircuitFactoryImage(filename);
		Circuit c = cF.build();
		try {
			FileOutputStream output = new FileOutputStream(filename
					+ ".convert.trk");
			PrintStream p = new PrintStream(output);
			int h = c.getHeight();
			int w = c.getWidth();
			p.println(w);
			p.println(h);
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					p.print(charFromTerrain(c.getTerrain(i, j)));
				}
				p.println();
			}
			output.close();
		} catch (IOException e) {
			System.err.println("Can't open file " + filename + ".convert.trk"
					+ " for writting... Saving aborted");
		}
	}

	public static void preProcess(CircuitModifiable track,
			CircuitModifiable trackRes) {
		int i, j;
		int nbPixelsHerbe;
		for (i = 1; i < track.getHeight() - 1; i++) {
			for (j = 1; j < track.getWidth() - 1; j++) {
				nbPixelsHerbe = 0;
				for (int di = -1; di <= 1; di++) {
					for (int dj = -1; dj <= 1; dj++) {
						if (!ToolsTerrain.isRunnable(track.getTerrain(i + di, j
								+ dj))) {
							nbPixelsHerbe++;
						}

					}
				}
				if (nbPixelsHerbe > 2) {
					for (int di = -1; di <= 1; di++) {
						for (int dj = -1; dj <= 1; dj++) {
							trackRes.setTerrain(i + di, j + dj, Terrain.Herbe);
						}
					}
				} else {
					trackRes.setTerrain(i, j, track.getTerrain(i, j));
				}
			}
		}
	}

	public static void preProcessRectangle(CircuitModifiable track,
			CircuitModifiable trackRes, int x, int y, int l, int h) {
		for (int i = 0; i < track.getHeight(); i++) {
			for (int j = 0; j < track.getWidth(); j++) {
				trackRes.setTerrain(i, j, track.getTerrain(i, j));
			}
		}
		for (int i = x; i < x + l; i++) {
			for (int j = y; j < y + h; j++) {
				trackRes.setTerrain(i, j, Terrain.Eau);
			}
		}
	}

	public static void preProcessLonely(CircuitModifiable track,
			CircuitModifiable trackRes) {
		int i, j;
		int nbPixelsArrivee;
		for (i = 1; i < track.getHeight() - 1; i++) {
			for (j = 1; j < track.getWidth() - 1; j++) {
				nbPixelsArrivee = 0;
				if (track.getTerrain(i, j) != Terrain.EndLine) {
					trackRes.setTerrain(i, j, track.getTerrain(i, j));
					continue;
				}
				for (int di = -1; di <= 1; di++) {
					for (int dj = -1; dj <= 1; dj++) {
						if (track.getTerrain(i + di, j + dj) == Terrain.EndLine) {
							nbPixelsArrivee++;
						}
					}
				}
				if (nbPixelsArrivee < 3) {
					for (int di = -1; di <= 1; di++) {
						for (int dj = -1; dj <= 1; dj++) {
							trackRes.setTerrain(i + di, j + dj, Terrain.Herbe);
						}
					}
				} else {
					trackRes.setTerrain(i, j, track.getTerrain(i, j));
				}
			}
		}
	}

}
