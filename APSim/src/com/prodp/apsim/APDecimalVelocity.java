package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 8-26-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * A decimal representation of velocity; the velocities along the three axes
 * are not limited to the coordinate grid.
 * 
 */

public class APDecimalVelocity {

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
	
	public String toString(){
		return "{" + x + ", " + y + ", " + z + "}";
	}

}
