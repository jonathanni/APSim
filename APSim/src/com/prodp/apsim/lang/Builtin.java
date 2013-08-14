package com.prodp.apsim.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.prodp.apsim.APBuiltinHook;

public class Builtin extends Function {
	public static HashMap<java.lang.String, Function> functionLookup = new HashMap<java.lang.String, Function>();
	private Method realMethod;

	static {

	}

	public Builtin(java.lang.String name, Type... types)
			throws SecurityException, NoSuchMethodException {
		super(name, types);
		setRealMethod(APBuiltinHook.class.getMethod(getName(), getRealTypes()));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object call(Object... params) {

		Class<?> retType = getRealMethod().getReturnType();
		java.lang.Object ret = null;

		try {
			ret = getRealMethod().invoke(null, (java.lang.Object[]) params);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		if (retType.equals(java.lang.Void.TYPE))
			return new Void();

		retType = (Class<Object>) retType;

		return (Object) retType.cast(ret);
	}

	private Method getRealMethod() {
		return realMethod;
	}

	private void setRealMethod(Method realMethod) {
		this.realMethod = realMethod;
	}
}
