package com.prodp.apsim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Root class of some APSim classes. Provides utility functions for debugging.
 * 
 */

public class APObject {

	private static PrintWriter out;

	static {
		try {
			File f = new File("stdout.txt");
			f.delete();
			out = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(f)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Prints the info sent in via array (varargs) and prints it to the file as
	 * well.
	 * 
	 * @param v
	 *            the data
	 */

	public static final void debug(Object... v) {
		for (Object i : v) {
			System.out.println(i);
			out.println(i);
		}
	}

	/**
	 * 
	 * Prints in red the info sent in via array (varargs) and prints it to the
	 * file as well.
	 * 
	 * @param v
	 *            the error data
	 */

	public static final void error(Object... v) {
		for (Object i : v) {
			System.err.println(i);
			out.println(i);
		}
	}
}
