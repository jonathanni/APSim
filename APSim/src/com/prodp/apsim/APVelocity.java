package com.prodp.apsim;

/**
 * 
 * An integral vector that holds a velocity.
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

public class APVelocity {

	/**
	 * X component.
	 */
	public byte x;

	/**
	 * Y Component.
	 */
	public byte y;

	/**
	 * Z component.
	 */
	public byte z;

	/**
	 * 
	 * Create an integral copy of an existing decimal velocity.
	 * 
	 * @param a
	 *            the copied decimal velocity.
	 */

	public APVelocity(APDecimalVelocity a) {
		this((byte) Math.signum(Math.round(a.x)), (byte) Math.signum(Math
				.round(a.y)), (byte) Math.signum(Math.round(a.z)));
	}

	/**
	 * 
	 * Creates a new velocity based on three small bytes.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public APVelocity(byte a, byte b, byte c) {
		x = a;
		y = b;
		z = c;
	}

	@Override
	public int hashCode() {
		return ((((int)x << 8) + (int)y) << 8) + (int)z;
	}

	@Override
	public boolean equals(Object b) {
		return (b instanceof APVelocity) && ((APVelocity) b).x == x
				&& ((APVelocity) b).y == y && ((APVelocity) b).z == z;
	}
	
	@Override
	public String toString(){
		return "{" + x + ", " + y + ", " + z + "}";
	}
}
