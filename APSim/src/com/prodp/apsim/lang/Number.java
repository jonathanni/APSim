package com.prodp.apsim.lang;

public class Number extends Variable {
	private double value;

	public Number(double value) {
		setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public static Class<?> getUnderlyingType() {
		return Double.class;
	}

}
