package com.prodp.apsim.lang;

public class FunctionCall extends Object {
	private Function boundFunction;

	public FunctionCall(Function func) {
		setBoundFunction(func);
	}

	public Function getBoundFunction() {
		return boundFunction;
	}

	public void setBoundFunction(Function boundFunction) {
		this.boundFunction = boundFunction;
	}
}
