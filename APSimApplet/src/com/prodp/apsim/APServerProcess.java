package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Identity class; distinguishes servers away from other processes.
 * 
 */

public class APServerProcess extends APProcess {

	/**
	 * 
	 * Creates a new process based on the path of the world.
	 * 
	 * @param name the world path
	 */

	public APServerProcess(String name) {
		setWorldPath(name);
	}

}
