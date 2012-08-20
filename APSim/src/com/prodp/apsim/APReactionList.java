package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-6-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Keeps reactions in a list, and allows for quick retrieving and reading.
 * 
 */

public class APReactionList {

	// private HashMap<APPair, APMaterial> product = new HashMap<APPair,
	// APMaterial>();
	// private HashMap<APPair, Float> chance = new HashMap<APPair, Float>();
	// private HashMap<APPair, Integer> action = new HashMap<APPair, Integer>();

	/**
	 * The list of APReactions; merely an identity map for lookup.
	 * 
	 * The way it works is it uses the first material's id for the first
	 * dimension, and the second material's id for the second. The slot
	 * resulting from this lookup contains null or the APReaction.
	 */

	public APReaction[][] gridLookup = new APReaction[APMaterial.values().length][APMaterial
			.values().length];

	/**
	 * 
	 * Adds a new reaction by putting it into a HashMap.
	 * 
	 * @param a
	 *            the reaction
	 */

	public final void addReaction(APReaction a) {
		gridLookup[a.getPair().id1][a.getPair().id2] = a;
	}

	/**
	 * 
	 * Gets a reaction by matching it from an entry in the HashMap.
	 * 
	 * @param a
	 *            the pair of materials
	 * @return the reaction matching it
	 */

	public final APReaction getReaction(final APPair a) {
		return gridLookup[a.id1][a.id2];
	}

	/**
	 * 
	 * Checks if the list has the pair of elements.
	 * 
	 * @param p
	 *            the pair of materials
	 * @return the flag
	 */

	public final boolean has(final APPair p) {
		return getReaction(p) != null;
	}
}
