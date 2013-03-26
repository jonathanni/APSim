package com.prodp.apsim;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 6-30-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * This class is a customization of the {@link APButton}; it contains many
 * graphics tricks to make it look professional.
 * 
 */

public class APButton extends JButton {

	/**
	 * Serial Version UID.
	 */
	
	private static final long serialVersionUID = 7212938566276838363L;

	LinearGradientPaint paint, down;

	private RadialGradientPaint glare, dglare;

	private final float[] pFrac = new float[] { 0, .31f, 1 };

	private final Color[] pColors = new Color[] { new Color(197, 222, 234),
			new Color(138, 187, 215), new Color(6, 109, 171) };

	private final float[] dFrac = new float[] { 0, .31f, 1 };

	private final Color[] dColors = new Color[] { new Color(6, 109, 171),
			new Color(138, 187, 215), new Color(197, 222, 234) };

	private final float[] dgFrac = new float[] { 0, .31f, 1 };

	private final Color[] dgColors = new Color[] { new Color(255, 255, 255, 0),
			new Color(255, 255, 255, 67), new Color(255, 255, 255, 167) };

	private final float[] gFrac = new float[] { 0, .31f, 1 };

	private final Color[] gColors = new Color[] {
			new Color(215, 215, 215, 207), new Color(215, 215, 215, 67),
			new Color(215, 215, 215, 0) };

	private static final int PADDING = 20;

	private boolean flag = false;

	private double reflectionFraction;

	private int reflectionHeight = 0;

	/**
	 * 
	 * Creates a new APButton, using Arial as the font (default) and the
	 * inputted string as text.
	 * 
	 * @param s
	 *            the title of the button
	 */

	public APButton(String s) {
		this(s, Font.getFont("Arial"));
	}

	/**
	 * 
	 * Creates a new APButton, using the inputted font and the inputted string.
	 * Uses 1, the default reflection ratio.
	 * 
	 * @param s
	 *            the title of the button
	 * @param f
	 *            the font of the button
	 */

	public APButton(String s, Font f) {
		this(s, f, 1);
	}

	/**
	 * 
	 * Primary constructor; uses the inputted string, font, and reflection
	 * ratio. The reflection ratio is a double specifying the ratio between the
	 * reflection height and the original button height.
	 * 
	 * In version 0.0 the reflection height is always 1. Please use
	 * {@link #APButton(String, Font)}.
	 * 
	 * @category unused
	 * 
	 * @param s
	 *            the title of the button
	 * @param f
	 *            the font of the button
	 * @param reflfrac
	 *            the ratio between the reflection height and the button height
	 */

	public APButton(String s, Font f, double reflfrac) {
		super(s);
		setFont(f);
		setContentAreaFilled(false);
		setReflectionFraction(reflfrac);
	}

	@Override
	public void paintComponent(Graphics g) {

		if (!flag) {
			Component root = SwingUtilities.getRoot(this);

			setReflectionHeight((int) (getSize().height * getReflectionFraction()));

			setSize(getSize().width, getSize().height
					+ (int) (getSize().height * getReflectionFraction())
					+ PADDING);

			setPreferredSize(getSize());
			setMinimumSize(getSize());
			setMaximumSize(getSize());

			((JFrame) root).pack();

			flag = true;
		}

		BufferedImage original = new BufferedImage(getWidth(), getHeight() / 2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = original.createGraphics();
		FontMetrics fm = getFontMetrics(getFont());

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);

		g2.setFont(getFont());

		paint = new LinearGradientPaint(0, 0, 0, getHeight() / 2, pFrac,
				pColors);
		glare = new RadialGradientPaint(8, 8, getWidth(), gFrac, gColors);
		dglare = new RadialGradientPaint(getWidth() - 8, 8, getWidth(), dgFrac,
				dgColors);
		down = new LinearGradientPaint(0, 0, 0, getHeight() / 2, dFrac, dColors);

		Point screenp = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(screenp, getParent());

		if (!new Rectangle(getBounds().x, getBounds().y, getBounds().width,
				getBounds().height / 2).contains(screenp)) {

			getModel().setArmed(false);
			getModel().setPressed(false);

		} else if (getModel().isPressed()) {

			getModel().setArmed(true);
			getModel().setPressed(true);

		}

		if (!getModel().isPressed()) {

			g2.setPaint(paint);
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() / 2 - 1, 10, 10);

			if (getModel().isRollover()) {
				g2.setPaint(glare);
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() / 2 - 1, 10,
						10);
			}

		} else {
			g2.setPaint(down);
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() / 2 - 1, 10, 10);
			g2.setPaint(dglare);
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() / 2 - 1, 10, 10);
		}

		g2.setPaint(Color.DARK_GRAY);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() / 2 - 1, 10, 10);

		GradientPaint smoothtext = new GradientPaint(0, 0, new Color(0.7f,
				0.7f, 0.7f, 1f), 0, fm.getHeight(), new Color(0, 0, 0, 1f));
		BufferedImage text = new BufferedImage(getWidth(), getHeight() / 2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g3 = text.createGraphics();

		g3.addRenderingHints(g2.getRenderingHints());

		g3.setFont(getFont());

		g3.setColor(Color.BLACK);
		g3.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
				fm.getHeight());

		g3.setPaint(smoothtext);
		g3.setComposite(AlphaComposite.SrcIn);
		g3.fillRect(0, 0, getWidth(), getHeight() / 2);

		g3.dispose();

		g2.setColor(Color.DARK_GRAY);
		g2.translate(3d, 3d);
		g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
				fm.getHeight());

		g2.translate(-3d, -3d);
		g2.drawImage(text, 0, 0, null);

		g2.drawImage(text, 0, 0, null);
		g2.dispose();

		BufferedImage intermediate = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D o = intermediate.createGraphics();
		GradientPaint mask = new GradientPaint(0, 0,
				new Color(1f, 1f, 1f, 0.5f), 0, getHeight() / 2, new Color(1f,
						1f, 1f, 0));

		o.addRenderingHints(g2.getRenderingHints());

		o.drawImage(original, 0, 0, null);

		AffineTransform originalTransform = o.getTransform();

		o.scale(1, -1);
		o.drawImage(original, 0, -getHeight() - PADDING, null);

		o.setTransform(originalTransform);
		o.translate(0, getHeight() / 2 + PADDING);

		o.setPaint(mask);
		o.setComposite(AlphaComposite.DstIn);

		o.fillRect(0, 0, getWidth(), getHeight() / 2);

		o.setTransform(originalTransform);

		o.dispose();

		g.drawImage(intermediate, 0, 0, null);

		g.dispose();

	}

	/**
	 * 
	 * Gets the ratio between the reflection height and the button height.
	 * 
	 * @return the ratio
	 */

	public double getReflectionFraction() {
		return reflectionFraction;
	}

	/**
	 * 
	 * Sets the ratio described in {@link #getReflectionFraction()}.
	 * 
	 * @param reflectionFraction
	 *            the ratio
	 */

	public void setReflectionFraction(double reflectionFraction) {
		this.reflectionFraction = reflectionFraction;
	}

	/**
	 * 
	 * Gets the reflection height. In version 0.0 this is equivalent to the
	 * button height.
	 * 
	 * @return the reflection height
	 */

	@SuppressWarnings("unused")
	
	private int getReflectionHeight() {
		return reflectionHeight;
	}
	
	/**
	 * 
	 * Sets the reflection height. In version 0.0 this does nothing.
	 * 
	 * @category unused
	 * 
	 * @param reflectionHeight the reflection height
	 */

	private void setReflectionHeight(int reflectionHeight) {
		this.reflectionHeight = reflectionHeight;
	}
}
