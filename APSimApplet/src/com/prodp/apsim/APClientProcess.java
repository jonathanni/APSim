package com.prodp.apsim;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-1-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Identity class for telling APSim that the user is playing a multiplayer game.
 * 
 */

public class APClientProcess extends APProcess {

	private String IP;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	/**
	 * 
	 * Constructor. Creates a new instance of this class. The IP address of the
	 * server, the input & output streams, and the name of the process are used.
	 * 
	 * @param ip
	 *            the IP address of the server
	 * @param a
	 *            the stream to read input from
	 * @param b
	 *            the stream to send output data
	 * @param name
	 *            the name of the process
	 */

	public APClientProcess(String ip, InputStream a, OutputStream b, String name) {
		super(name);
		setIP(ip);
		try {
			in = new ObjectInputStream(a);
			out = new ObjectOutputStream(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Gets the input stream associated with this process.
	 * 
	 * @return the input stream
	 */

	public ObjectInputStream getInputStream() {
		return in;
	}

	/**
	 * 
	 * Gets the output stream associated with this process.
	 * 
	 * @return the output stream
	 */

	public ObjectOutputStream getOutputStream() {
		return out;
	}

	/**
	 * 
	 * Gets the IP address of the server.
	 * 
	 * @return the IP address
	 */

	public String getIP() {
		return IP;
	}

	/**
	 * 
	 * Sets the IP address of the server. This may be used in the future.
	 * 
	 * @param iP the IP address
	 */

	public void setIP(String iP) {
		IP = iP;
	}

}
