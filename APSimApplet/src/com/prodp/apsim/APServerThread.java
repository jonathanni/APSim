package com.prodp.apsim;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * The thread that updates the server. Runs continuously and updates server
 * information automatically.
 * 
 */

public class APServerThread implements Runnable {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;

	/**
	 * 
	 * Gets the output stream for the thread.
	 * 
	 * @return the output stream
	 */
	
	public ObjectOutputStream getOutputStream() {
		return out;
	}

	/**
	 * 
	 * Constructor; creates a new thread and starts outputting to the log.
	 * 
	 * @param s
	 */
	
	public APServerThread(Socket s) {

		socket = s;

		try {

			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

		APServer.log("Found client "
				+ socket.getInetAddress().getCanonicalHostName());
	}

	@Override
	public void run() {
		boolean isRunning = true;
		while (isRunning) {

			try {

				APProcessHandler.updateServer(this);

				// Read stuff
				byte instruction = 0;
				instruction = in.readByte();

				switch (instruction) {
				case APFinalData.ENTER:
					// Don't care
					break;
				case APFinalData.EXIT:
					isRunning = false;
					break;
				case APFinalData.UPDATE_POSITION:
					// TODO make something to do with the position
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
