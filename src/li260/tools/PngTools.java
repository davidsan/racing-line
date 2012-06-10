package li260.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PngTools {

	public static Image loadPng(String filename) {
		BufferedImage im = null;
		try {
			im = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return im;

	}
}
