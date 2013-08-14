package com.prodp.apsim.lang;

public class Array<T extends Variable> {

	private int[] dimensions;
	private Class<?> underlyingType;
	private T[] values;

	@SuppressWarnings("unchecked")
	public Array(java.lang.Object[] initialvalues, int[] dimensions) {
		int mul = 1;

		for (int i : dimensions)
			mul *= i;

		Variable[] arr = null;

		if (values instanceof Boolean[]) {
			arr = new Boolean[mul];
			for (int i = 0; i < initialvalues.length; i++)
				arr[i] = new Boolean(((java.lang.Boolean) initialvalues[i]));
		} else if (values instanceof Number[]) {
			arr = new Number[mul];
			for (int i = 0; i < initialvalues.length; i++)
				arr[i] = new Number(((java.lang.Double) initialvalues[i]));
		} else if (values instanceof String[]) {
			arr = new String[mul];
			for (int i = 0; i < initialvalues.length; i++)
				arr[i] = new String(((java.lang.String) initialvalues[i]));
		}

		setValues((T[]) arr);
		setDimensions(dimensions);
	}

	public Variable getValue(int... indices) {

		if (indices.length != getDimensions().length)
			return null;

		int add = 1;

		for (int i = 0; i < indices.length; i++)
			add += indices[i] * getDimensions()[i];

		return values[add];
	}

	@SuppressWarnings("unchecked")
	public boolean setValue(Variable var, int... indices) {

		if (indices.length != getDimensions().length)
			return false;

		int add = 1;

		for (int i = 0; i < indices.length; i++)
			add += indices[i] * getDimensions()[i];

		values[add] = (T) var;

		return true;
	}

	private T[] getValues() {
		return values;
	}

	private void setValues(T[] values) {
		this.values = values;
	}

	public int[] getDimensions() {
		return dimensions;
	}

	public void setDimensions(int[] dimensions) {
		this.dimensions = dimensions;
	}

	public Class<?> getUnderlyingType() {
		return underlyingType;
	}

	private void setUnderLyingType(Class<?> type) {
		underlyingType = type;
	}

	public int getLength() {
		return getValues().length;
	}
}
