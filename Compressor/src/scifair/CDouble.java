package scifair;

public class CDouble {

	public int index, height;

	public CDouble(int index, int height) {
		this.index = index;
		this.height = height;
	}

	@Override
	public String toString() {
		return "{" + index + ", " + height + "}";
	}
}
