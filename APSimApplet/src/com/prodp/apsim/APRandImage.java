package com.prodp.apsim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-3-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Generates a random image; the image consists of white dots plotted randomly
 * over a black background. Intended to imitate stars.
 * 
 */

public class APRandImage extends BufferedImage {

	private static final double SPREAD = 0.005;

	/**
	 * 
	 * Creates a new random image through plotting light points on a dark
	 * background.
	 * 
	 * @param arg0
	 *            see {@link BufferedImage #BufferedImage(int, int, int)}
	 * @param arg1
	 *            see {@link BufferedImage #BufferedImage(int, int, int)}
	 * @param arg2
	 *            see {@link BufferedImage #BufferedImage(int, int, int)}
	 * @param bgcolor
	 *            the background color
	 * @param speckles
	 *            the noise colors
	 */

	public APRandImage(int arg0, int arg1, int arg2, Color bgcolor,
			Color... speckles) {
		super(arg0, arg1, arg2);

		Graphics2D g = createGraphics();

		g.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(bgcolor);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (Color speckle : speckles) {
			g.setColor(speckle);
			for (int i = 0; i < Math.round(SPREAD * (getWidth() * getHeight())); i++)
				g.drawRect((int) Math.round(Math.random() * getWidth()),
						(int) Math.round(Math.random() * getHeight()), 1, 1);
		}
		g.dispose();
	}

}
