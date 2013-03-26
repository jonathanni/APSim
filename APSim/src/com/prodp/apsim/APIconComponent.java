package com.prodp.apsim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Icon for the tabs on the {@link JTabbedPane}.
 * 
 */

public class APIconComponent extends JPanel {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 4814306468315683200L;
	private Image image;

	/**
	 * 
	 * Creates a new icon for the tabs. It is treated like a separate component.
	 * 
	 * @param image the image to use (pref. APSim logo)
	 * @param width the width of the image
	 * @param height the height of the image
	 */

	public APIconComponent(Image image, int width, int height) {
		setSize(width, height);
		setImage(image);
		add(new Box.Filler(new Dimension(width, height), new Dimension(width,
				height), new Dimension(width, height)));
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(getImage(), 0, 0, getSize().width, getSize().height, null);

		g2.dispose();
	}
	
	/**
	 * 
	 * Gets the icon image.
	 * 
	 * @return the icon image
	 */

	public Image getImage() {
		return image;
	}
	
	/**
	 * 
	 * Sets the icon image.
	 * 
	 * @param image the icon
	 */

	public void setImage(Image image) {
		this.image = image;
	}
}
