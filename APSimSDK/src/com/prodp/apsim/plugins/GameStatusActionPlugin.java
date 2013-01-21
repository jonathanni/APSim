package com.prodp.apsim.plugins;

/**
 * 
 * This plugin is triggered on a status change in the game.
 * 
 * The trigger truth value must be initialized.
 * 
 * @version 0.0
 * @author Jonathan
 * 
 */
public interface GameStatusActionPlugin extends Plugin {
	/**
	 * Truth value that triggers the plugin. May be an expression.
	 */
	public boolean trigger = false;
}
