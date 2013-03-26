package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-1-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Interface for classes that control the networking systems of APSim.
 * 
 */

public interface APConnectionUsable {

	/**
	 * 
	 * Inputs data, processes it, then sends data.
	 * 
	 * @throws Exception
	 */

	public void update() throws Exception;

	/**
	 * 
	 * Startup of the networking controller.
	 * 
	 * @throws Exception
	 */

	public void init() throws Exception;

	/**
	 * 
	 * Shutdown of the networking controller.
	 * 
	 * @throws Exception
	 */

	public void exit() throws Exception;
}
