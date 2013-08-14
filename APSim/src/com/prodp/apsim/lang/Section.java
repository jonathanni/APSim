package com.prodp.apsim.lang;

import java.util.HashMap;

public abstract class Section extends Container {
	private java.lang.String name;

	public Section(java.lang.String name) {
		setName(name);
		setParent(null);
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}
}
