package com.prodp.apsim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Base class for classes that read and write from a file.
 * 
 */

public abstract class APProcessableItem {

	/**
	 * Stream for reading in.
	 */

	public static ObjectInputStream in;

	/**
	 * Stream for writing out.
	 */

	public static ObjectOutputStream out;
	private String path;

	/**
	 * Default constructor. Do not use.
	 * 
	 * @category unused
	 */

	public APProcessableItem() {
	}

	/**
	 * 
	 * Main constructor. Links to a specific path of a file, and creates the
	 * necessary input and output streams.
	 * 
	 * @param s
	 *            the path to the file
	 */

	public APProcessableItem(String s) {
		path = s;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path));
			in = new ObjectInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Sets the path of the file.
	 * 
	 * @param s
	 *            the path of the file
	 */

	public final void setPath(String s) {
		path = s;
	}

	/**
	 * 
	 * Gets the path of the file.
	 * 
	 * @return the path of the file
	 */

	public final String getPath() {
		return path;
	}

	/**
	 * 
	 * Writes to the file. The process is specified elsewhere.
	 * 
	 * @throws IOException
	 */

	public abstract void write() throws IOException;

	/**
	 * 
	 * Reads from the file. The process is specified elsewhere, and the data is
	 * not returned from the function; rather, it is put somewhere else while
	 * executing the function.
	 * 
	 * @throws IOException
	 */

	public abstract void read() throws IOException;

}
