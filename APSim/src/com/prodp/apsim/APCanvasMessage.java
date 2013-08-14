package com.prodp.apsim;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 
 * @author Jonathan
 * @since 7/19/13
 * @version 0.0
 * 
 */

public class APCanvasMessage extends BufferedImage {

	private static final int PADDING = 5, ARC = 5;

	/**
	 * 
	 * Creates a new canvas message that should be displayed on the main
	 * Canvas3D. Automatically wraps the message within horizontal bounds.
	 * 
	 * See {@link java.awt.image.BufferedImage#BufferedImage(int, int, int)}
	 * 
	 * @param width
	 *            the message width
	 * @param height
	 *            the message height
	 * @param type
	 *            the type of BufferedImage
	 * @param msg
	 *            the message to be delivered
	 * @param msgcolor
	 *            the message color
	 * @param bgcolor
	 *            the background color
	 */

	public APCanvasMessage(int width, int height, int type, String msg,
			Color msgcolor, Color bgcolor) {
		super(width, height, type);

		Graphics2D g = (Graphics2D) getGraphics();

		ArrayList<String> dispStrings = new ArrayList<String>();
		char[] msgchar = msg.toCharArray();

		FontMetrics fontmet = g.getFontMetrics();

		for (int i = 0; i < msg.length(); i++) {
			StringBuilder temp = new StringBuilder();
			temp.append(msgchar[i]);

			if (fontmet.stringWidth(temp.toString()) >= width
					|| i == msg.length() - 1) {
				dispStrings.add(temp.toString());
				temp = new StringBuilder();
			}
		}

		g.setColor(bgcolor);

		g.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);

		g.setColor(msgcolor);

		int y = 0;
		for (int i = 0; i < dispStrings.size(); i++) {
			int fontheight = getStringBounds(g, dispStrings.get(i)).height;
			g.drawString(dispStrings.get(i), 0, y);
			y += fontheight + PADDING;
		}

		g.dispose();

	}

	private final Rectangle getStringBounds(Graphics2D g2, String str) {
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
		return gv.getPixelBounds(frc, 0, 0);
	}
}
