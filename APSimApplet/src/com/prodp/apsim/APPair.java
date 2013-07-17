package com.prodp.apsim;

import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * A specialized {@link Pair}, between two {@link APMaterial}s. Necessary for
 * searching in the {@link APReaction}s list.
 * 
 * As of version 0.1, the materials are now the IDs of the materials instead of
 * the APMaterials themselves. This is to insure greater speed.
 * 
 */

public final class APPair extends Pair<Integer, Integer> {

	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = -784122335949560595L;

	/**
	 * The ID of the first material.
	 */

	public int id1;

	/**
	 * The ID of the second material.
	 */

	public int id2;
	
	private int hashCode;

	/**
	 * 
	 * Creates a new pair of two APMaterials and caches the hashCode.
	 * 
	 * @param s
	 *            the first material
	 * @param t
	 *            the second material
	 */

	public APPair(short s, short t) {

		id1 = s;
		id2 = t;

		hashCode = id1 << 16 | id2;
	}

	@Override
	public boolean equals(Object b) {
		return (b instanceof APPair) && id1 == ((APPair) b).id1
				&& id2 == ((APPair) b).id2;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public Integer setValue(Integer value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer getLeft() {
		return id1;
	}

	@Override
	public Integer getRight() {
		return id2;
	}

	@Override
	public String toString() {
		return "(" + id1 + ", " + id2 + ")";
	}
}
