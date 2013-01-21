package com.prodp.apsim.plugins;

import java.util.ArrayList;

/**
 * 
 * This plugin is triggered on certain keypresses.
 * 
 * The action keys must have at least 1 key under the list.
 * 
 * @version 0.0
 * @author Jonathan
 * 
 */

public interface KeyboardActionPlugin extends Plugin {

	/**
	 * The keys that trigger the action. May be multiple keys that are required.
	 */
	public ArrayList<Integer> actionKeys = new ArrayList<Integer>();
}
