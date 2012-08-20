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
 * @author Jonathan
 * 
 */

public class APVelocity {

	/**
	 * 
	 * X velocity.
	 * 
	 */

	public byte x;

	/**
	 * 
	 * Y velocity.
	 * 
	 */

	public byte y;

	/**
	 * 
	 * Z velocity.
	 * 
	 */

	public byte z;

	/**
	 * 
	 * Default constructor; unused.
	 * 
	 */

	public APVelocity() {
	};

	/**
	 * 
	 * Copy constructor; creates a velocity out of another.
	 * 
	 * @param copy the other velocity
	 */

	public APVelocity(APVelocity copy) {
		x = copy.x;
		y = copy.y;
		z = copy.z;
	}

	@Override
	public String toString() {
		return "APVelocity {" + x + ", " + y + ", " + z + "}";
	}

	@Override
	public int hashCode() {
		return x ^ y ^ z;
	}

	@Override
	public boolean equals(Object b) {
		return (b instanceof APVelocity) && ((APVelocity) b).equals(this);
	}

	private boolean equals(APVelocity b) {
		return b.x == x && b.y == y && b.z == z;
	}
}
