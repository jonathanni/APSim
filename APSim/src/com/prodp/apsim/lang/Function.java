package com.prodp.apsim.lang;

public class Function extends Container {

	private Type[] parameters;
	private Class<? extends Object>[] realTypes;
	private java.lang.String name;

	public Function(java.lang.String name, Type... parameters) {
		setName(name);
		setParameters(parameters);
	}

	public Type[] getParameters() {
		return parameters;
	}

	public void setParameters(Type[] parameters) {
		this.parameters = parameters;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	protected Class<? extends Object>[] getRealTypes() {
		return realTypes;
	}

	public Object call(Object... params) {

		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof Variable)
				this.getVariables().put(((Variable) params[i]).getName(),
						params[i]);
		}

		return null;
	}

}
