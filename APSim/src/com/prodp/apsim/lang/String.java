package com.prodp.apsim.lang;

public class String extends Variable {
	private java.lang.String value;

	public String(java.lang.String value) {
		setValue(value);
	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public static Class<?> getUnderlyingType() {
		return java.lang.String.class;
	}
}
