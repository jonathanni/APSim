package com.prodp.apsim;

import java.util.ArrayList;

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
	 * resulting from this lookup contains null or the APReaction's ID (slot
	 * value in the reaction list).
	 */

	public int[][] gridLookup = new int[APMaterial.values().length][APMaterial
			.values().length];

	private APReaction[] reactionList;
	private ArrayList<APReaction> reactionArrayList = new ArrayList<APReaction>();

	private boolean isSetUp = false;

	/**
	 * 
	 * Adds a new reaction by putting it into a HashMap.
	 * 
	 * @param a
	 *            the reaction
	 */

	public final void addReaction(APReaction a) {
		reactionArrayList.add(a);
	}

	private final void setUp() {

		for (int i = 0; i < gridLookup.length; i++)
			for (int j = 0; j < gridLookup[i].length; j++)
				gridLookup[i][j] = -1;

		isSetUp = true;

		reactionList = new APReaction[reactionArrayList.size()];

		for (int i = 0; i < reactionArrayList.size(); i++)
			reactionList[i] = reactionArrayList.get(i);

		for (int i = 0; i < reactionList.length; i++)
			gridLookup[reactionList[i].getPair().id1][reactionList[i].getPair().id2] = i;
	}

	/**
	 * 
	 * Gets a reaction by matching it from an entry in the HashMap.
	 * 
	 * @param a1
	 *            the id of the first material
	 * @param a2
	 *            the id of the second material
	 * @return the reaction matching it
	 */

	public final APReaction getReaction(final int a1, final int a2) {

		if (!isSetUp)
			setUp();

		final int lookup = gridLookup[a1][a2];
		return lookup == -1 ? null : reactionList[lookup];
	}

	/**
	 * 
	 * Checks if the list has the pair of elements.
	 * 
	 * @param a
	 *            the first material's ID
	 * @param b
	 *            the second material's ID
	 * @return the flag
	 */

	public final boolean has(final int a, final int b) {
		return getReaction(a, b) != null;
	}
}
