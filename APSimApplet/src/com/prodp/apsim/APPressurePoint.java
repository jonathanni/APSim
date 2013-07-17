package com.prodp.apsim;

import javax.vecmath.Point3i;

/**
 * 
 * Location containing a value for pressure. Since every coordinate in 3D space
 * cannot have its own pressure value, as it would take too much space, there is
 * a list of important pressure points instead.
 * 
 * Each pressure point contains a location, pressure value, and persistence
 * value. The location is a Point3i and is inherited. The pressure value is a
 * float, and the persistence value is a float between 1f (exclusive) and 4f
 * (inclusive).
 * 
 * See {@link com.prodp.apsim.APArrayUtils#performPressure(APProcess, int)}
 * 
 * @author Jonathan
 * @version 0.0
 * @since 6-19-13
 * 
 */

public class APPressurePoint extends Point3i {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final float RATIO = -0.25f;

	private float value, persistence;

	/**
	 * 
	 * Creates a new instance of a pressure point. Default persistence 2.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param z
	 *            the z coordinate
	 * @param value
	 *            the pressure value
	 * @param persistence
	 *            the persistence value
	 */

	public APPressurePoint(int x, int y, int z, float value, float persistence) {
		super(x, y, z);
		setValue(value);
		if (persistence <= 1f || persistence > 4f)
			setPersistence(2);
		else
			setPersistence(persistence);
	}

	/**
	 * 
	 * Gets a float > 0 and <= 1.0f that denotes how forceful the pressure is
	 * away from the point with a given distance.
	 * 
	 * Uses a function (persistence)^(|dist|*R) assuming distance is positive.
	 * 
	 * @param dist
	 *            the distance to the point
	 * @return the multiplier
	 */
	public final float getMultiplier(float dist) {
		return (float) Math.pow(persistence, dist * RATIO);
	}

	/**
	 * 
	 * Gets the pressure value.
	 * 
	 * @return the value
	 */

	public float getValue() {
		return value;
	}

	/**
	 * 
	 * Sets the pressure value.
	 * 
	 * @param value
	 *            the value
	 */

	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * 
	 * Gets the persistence value.
	 * 
	 * @return the value
	 */
	public float getPersistence() {
		return persistence;
	}

	/**
	 * 
	 * Sets the persistence value.
	 * 
	 * @param persistence
	 *            the value
	 */

	public void setPersistence(float persistence) {
		this.persistence = persistence;
	}

}
