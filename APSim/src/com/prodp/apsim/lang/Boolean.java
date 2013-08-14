package com.prodp.apsim.lang;

public class Boolean extends Variable {
	private boolean value;

	public Boolean(boolean value) {
		setValue(value);
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public static Class<?> getUnderlyingType() {
		return Boolean.class;
	}

}
