package com.prodp.apsim.plugins;

import javax.swing.Icon;

/**
 * 
 * This plugin is triggered on a click on the toolbar on the top of the screen.
 * 
 * The icon and text represented by this utility is added to the toolbar.
 * 
 * The text, tag, and ID of this plugin must be initialized to work properly.
 * 
 * @version 0.0
 * @author Jonathan
 * 
 */
public interface UtilityPlugin extends Plugin {

	/**
	 * ID of this plugin.
	 */
	public int ID = -1;

	/**
	 * Icon. Default icon is blank (null).
	 */
	public Icon icon = null;

	/**
	 * Text for the entry.
	 */
	public String text = "[def-text]";

	/**
	 * This text shows what menu this plugin is under.
	 * 
	 * For example, "Edit" puts this plugin under the Edit menu.
	 * 
	 */

	public String tag = "[def-tag]";
}
