package com.prodp.apsim;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Updates the log of the server every once in a while.
 *
 */

public class APServerThreadWatcher implements Runnable {

	private APServer server;

	/**
	 * 
	 * Constructor; creates a new watcher from the server provided.
	 * 
	 * @param apServer the server
	 */
	
	public APServerThreadWatcher(APServer apServer) {
		setServer(apServer);
	}

	@Override
	public void run() {
		new Timer().schedule(new Update(), 60000);

		try {
			while (APFinalData.serverFrame.isVisible()) {
				server.update();

				APMain.sleepNanos(100000);
			}

			server.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Gets the server associated with the watcher.
	 * 
	 * @return the server
	 */

	public APServer getServer() {
		return server;
	}
	
	/**
	 * 
	 * Sets the server for the watcher.
	 * 
	 * @param server the server
	 */

	public void setServer(APServer server) {
		this.server = server;
	}

}

class Update extends TimerTask {

	@Override
	public void run() {
		APServer.log("KEEP_ALIVE: [" + System.currentTimeMillis() / 1000 + "]");
	}
}
