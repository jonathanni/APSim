package com.prodp.apsim.plugins;

/**
 * 
 * This plugin, once triggered, runs as a job until stopped.
 * 
 * May be implemented with other plugin interfaces, or may be initialized on
 * start.
 * 
 * @version 0.0
 * @author Jonathan
 * 
 */

public interface JobPlugin extends Plugin {
	public boolean initOnStart = false;

	/**
	 * Stop the job and remove it from the job stack.
	 */
	public void stop();

	/**
	 * Pause the job.
	 */
	public void pause();

	/**
	 * Resume the job.
	 */
	public void resume();
}
