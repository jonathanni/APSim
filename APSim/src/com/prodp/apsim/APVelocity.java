package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

public class APVelocity {

	public byte x;

	public byte y;

	public byte z;

	public APVelocity(APDecimalVelocity a) {
		this((byte) Math.signum(Math.round(a.x)), (byte) Math.signum(Math
				.round(a.y)), (byte) Math.signum(Math.round(a.z)));
	}

	public APVelocity(byte a, byte b, byte c) {
		x = a;
		y = b;
		z = c;
	}
}
