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
 * Allows the programmer to set certain parts of a boolean array on or off; this
 * is a cover for a boolean primitive array.
 * 
 */

public class Flagger {
	private boolean[] fl;

	/**
	 * 
	 * Creates a new flagger with a set capacity.
	 * 
	 * @param capacity the capacity of the flagger
	 */
	
	public Flagger(final int capacity) {
		fl = new boolean[capacity];

		for (int i = 0; i < fl.length; i++)
			fl[i] = false;
	}
	
	/**
	 * 
	 * Gets the boolean value at the specified index.
	 * 
	 * @param index the index of the flag
	 * @return the flag
	 */

	public final boolean get(final int index) {
		return fl[index];
	}
	
	/**
	 * 
	 * Sets the boolean value at the index specified.
	 * 
	 * @param index the index of the flag
	 * @param b the flag
	 */

	public final void set(final int index, final boolean b) {
		fl[index] = b;
	}
	
	/**
	 * 
	 * Toggles the boolean value at the specified index.
	 * 
	 * Inverts the boolean value at the index.
	 * 
	 * @param index the index of the flag
	 */

	public final void toggle(final int index) {
		fl[index] = !fl[index];
	}
	
	/**
	 * 
	 * Clears the entire boolean array.
	 * 
	 */

	public final void clear() {
		for (int i = 0; i < fl.length; i++)
			fl[i] = false;
	}
}
