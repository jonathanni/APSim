package com.prodp.apsim.lang;

import java.util.ArrayList;
import java.util.HashMap;

public class Container extends Object {
	private HashMap<java.lang.String, Object> variables = new HashMap<java.lang.String, Object>();
	private final ArrayList<Object> children = new ArrayList<Object>();

	public HashMap<java.lang.String, Object> getVariables() {
		return variables;
	}

	public void setVariables(HashMap<java.lang.String, Object> variables) {
		this.variables = variables;
	}

	public void setVariableValue(java.lang.String name, Object value) {

		getVariables().put(name, value);

		if (getChildren().size() == 0)
			return;

		// Recursively set value for children

		for (Object i : getChildren())
			if (i instanceof Container)
				((Container) i).setVariableValue(name, value);
	}

	public ArrayList<Object> getChildren() {
		return children;
	}

	public boolean addChild(Object a) {
		if (!a.equals(this)) // Prevent crashing
			children.add(a);
		else
			return false;
		return true;
	}
}
