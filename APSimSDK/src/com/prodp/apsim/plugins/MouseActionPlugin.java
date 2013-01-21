package com.prodp.apsim.plugins;

import java.awt.event.MouseEvent;

/**
 * 
 * This plugin is triggered on some mouse events (clicks).
 * 
 * @version 0.0
 * @author Jonathan
 * 
 */

public interface MouseActionPlugin extends Plugin {
	// Default button: may be changed

	/**
	 * The button that triggers the event.
	 */
	public int button = MouseEvent.BUTTON1;
}
