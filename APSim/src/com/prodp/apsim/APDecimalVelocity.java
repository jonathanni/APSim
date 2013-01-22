package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 8-26-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * A decimal representation of velocity; the velocities along the three axes are
 * not limited to the coordinate grid.
 * 
 */

public class APDecimalVelocity {

	/**
	 * 
	 * Creates a new floating point precision 3D vector of velocity.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */

	public APDecimalVelocity(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Default constructor, sets everything to 0.
	 */

	public APDecimalVelocity() {
		this(0, 0, 0);
	}

	/**
	 * X-axis value of the velocity.
	 */

	public float x;

	/**
	 * Y-axis value of the velocity.
	 */

	public float y;

	/**
	 * Z-axis value of the velocity.
	 */

	public float z;

	public String toString() {
		return "{" + x + ", " + y + ", " + z + "}";
	}

	/**
	 * 
	 * Performs scalar multiplication on the vector.
	 * 
	 * @param f
	 *            a scalar
	 * @return the velocity times a scalar
	 */

	public APDecimalVelocity multiply(float f) {
		return new APDecimalVelocity(this.x * f, this.y * f, this.z * f);
	}

}
