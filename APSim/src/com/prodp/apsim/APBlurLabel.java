package com.prodp.apsim;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 6-27-2012 (Javadoc Created)
 *
 */

/**
 * 
 * This class is a customization of the {@link JLabel}; it adds the necessary
 * "fancy" addends to the text, including the gradient and (before) the blur.
 * 
 */

public class APBlurLabel extends JLabel {

	/**
	 * Serial Version UID.
	 */
	
	private static final long serialVersionUID = 3788856193818619609L;
	private BufferedImage cache = null;

	/**
	 * 
	 * Creates a new APBlurLabel via title. This is identical to
	 * {@link JLabel #JLabel(String)}.
	 * 
	 * @param title
	 *            the title of the label (the text).
	 */

	public APBlurLabel(String title) {
		super(title);
	}

	@Override
	public void paintComponent(Graphics g) {

		if (cache == null) {
			BufferedImage buffer = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = buffer.createGraphics();
			g2.setFont(getFont());

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);

			super.paintComponent(g2);

			GradientPaint smoothtext = new GradientPaint(0, 0, new Color(1, 0,
					0, 1f), 0, getHeight(), new Color(0, 0, 1, 1f));

			g2.setPaint(smoothtext);
			g2.setComposite(AlphaComposite.SrcIn);
			g2.fillRect(0, 0, getWidth(), getHeight());

			cache = new BufferedImage(getWidth(), getHeight(), buffer.getType());
			Graphics2D copier = cache.createGraphics();
			copier.drawImage(buffer, 0, 0, null);

			copier.dispose();
			g2.dispose();
		}

		((Graphics2D) g).drawImage(cache, 0, 0, null);

		g.dispose();
	}
}
