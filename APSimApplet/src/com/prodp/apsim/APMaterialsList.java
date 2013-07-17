package com.prodp.apsim;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Contains many utility methods for finding specific {@link APMaterial}s from
 * their attributes. Also contains a method for listing their names.
 * 
 */

public class APMaterialsList {

	private static APMaterial[] matIDLookup;
	private static HashMap<Integer, Integer> denIDLookup = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Float> floIDLookup = new HashMap<Integer, Float>();
	private static HashMap<Integer, Boolean> liqIDLookup = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> buoIDLookup = new HashMap<Integer, Boolean>();

	static {
		ArrayList<APMaterial> matIDLookupList = new ArrayList<APMaterial>();

		for (APMaterial a : APMaterial.values()) {
			matIDLookupList.add(a);
			denIDLookup.put((int) a.getID(), a.getDensity());
			floIDLookup.put((int) a.getID(), a.getFlowChance());
			liqIDLookup.put((int) a.getID(), a.getIsLiquid());
			buoIDLookup.put((int) a.getID(), a.getIsBuoyant());
		}

		matIDLookup = new APMaterial[matIDLookupList.size()];

		for (int i = 0; i < matIDLookupList.size(); i++)
			matIDLookup[i] = matIDLookupList.get(i);
	}

	/**
	 * 
	 * Gets the material density via ID. See {@link APMaterial} for more info.
	 * 
	 * @param ID
	 *            the ID
	 * @return the density
	 */

	public static final int getMaterialDensityByID(short ID) {
		Integer density = denIDLookup.get((int) ID);

		if (!density.equals(null))
			return density;

		return -1;
	}

	/**
	 * 
	 * Gets the material flow chance via ID. See {@link APMaterial} for more
	 * info.
	 * 
	 * @param ID
	 *            the ID
	 * @return the chance
	 */

	public static final float getMaterialFlowChanceByID(short ID) {
		Float chance = floIDLookup.get((int) ID);

		if (!chance.equals(null))
			return chance;

		return -1;
	}

	/**
	 * 
	 * Gets the {@link APMaterial} associated with the ID.
	 * 
	 * @param ID
	 *            the ID
	 * @return the APMaterial
	 */

	public static final APMaterial getMaterialByID(short ID) {
		return matIDLookup[ID];
	}

	/**
	 * 
	 * Gets if the material is a liquid. See {@link APMaterial} for more info.
	 * 
	 * @param i
	 *            the ID
	 * @return the flag
	 */

	public static final boolean isLiquid(final short i) {
		Boolean is = liqIDLookup.get((int) i);

		if (is == null)
			return false;

		return is;
	}

	/**
	 * 
	 * Gets if the material is buoyant. See {@link APMaterial} for more info.
	 * 
	 * @param i
	 *            the ID
	 * @return the flag
	 */

	public static final boolean isBuoyant(final short i) {
		Boolean is = buoIDLookup.get((int) i);

		if (is == null)
			return false;

		return is;
	}

	/**
	 * 
	 * Gets the list of all the material names.
	 * 
	 * @return the list
	 */

	public static final String[] getMaterialList() {
		ArrayList<String> names = new ArrayList<String>();
		String[] retnames = new String[APMaterial.values().length - 1];

		for (APMaterial a : APMaterial.values())
			if (!a.equals(APMaterial.NULL))
				names.add(a.getName());

		names.toArray(retnames);
		return retnames;
	}
}
