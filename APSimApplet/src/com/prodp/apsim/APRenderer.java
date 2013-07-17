package com.prodp.apsim;

import java.awt.GraphicsConfiguration;
import javax.swing.JComponent;
import anaglyphcanvas3d.AnaglyphCanvas3D;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * A cover for the canvas; allows more functions.
 * 
 */

public class APRenderer extends AnaglyphCanvas3D {

	/**
	 * The Serial Version UID.
	 */
	private static final long serialVersionUID = 9041402814129852918L;
	private boolean isAntialiased;

	/**
	 * 
	 * Creates a new renderer; identical to the superconstructor.
	 * 
	 * @param graphicsConfiguration
	 *            the graphics configuration for the canvas
	 * @param parent
	 *            the housing component
	 */

	public APRenderer(GraphicsConfiguration graphicsConfiguration,
			JComponent parent) {
		super(graphicsConfiguration, parent);
	}

	/**
	 * 
	 * Sets the scene antialiasing option of the canvas.
	 * 
	 * @param a
	 *            the flag
	 */

	public void setSceneAntialiasingEnable(boolean a) {
		isAntialiased = a;
		getView().setSceneAntialiasingEnable(a);
	}

	/**
	 * 
	 * Gets if the scene is antialiased.
	 * 
	 * @return the flag
	 */

	public boolean getSceneAntialiasingEnable() {
		return isAntialiased;
	}

}
