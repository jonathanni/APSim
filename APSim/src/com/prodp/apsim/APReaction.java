package com.prodp.apsim;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-3-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * A reaction between two elements is defined as an element <i>a</i> changing to
 * an element <i>c</i> due to contact with element <i>b</i>.
 * 
 * The reaction happens with chance; this is specified, as well as the action,
 * being either change to element <i>c</i> or removal.
 * 
 */

public final class APReaction {

	// IDS
	private APPair pair;
	private APMaterial product;
	private float chanceOfReaction;
	private int action;

	/**
	 * 
	 * Creates a new reaction.
	 * 
	 * The pair contains the two reactional elements; the first element reacts
	 * with the second element and changes.
	 * 
	 * @param pair
	 *            the pair of elements
	 * @param p
	 *            the product
	 * @param chance
	 *            the chance of reaction
	 * @param a
	 *            the final action see {@link APFinalData #CHANGE_BLOCK_FLAG}
	 *            and {@link APFinalData #REMOVE_BLOCK_FLAG}.
	 */

	public APReaction(final APPair pair, final APMaterial p,
			final float chance, final int a) {

		setPair(pair);

		setProduct(p);
		setChanceOfReaction(chance);
		setAction(a);
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof APReaction)
			return getPair().equals(((APReaction) b).getPair())
					&& getProduct().equals(((APReaction) b).getProduct());
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(pair).append(product)
				.append(chanceOfReaction).append(action).toHashCode();
	}

	/**
	 * 
	 * Gets the end product of the reaction.
	 * 
	 * @return the end product
	 */

	public final APMaterial getProduct() {
		return product;
	}

	/**
	 * 
	 * Sets the product of the reaction.
	 * 
	 * @param product
	 *            the end product
	 */

	public void setProduct(APMaterial product) {
		this.product = product;
	}

	/**
	 * 
	 * Gets the chance of the reaction. Between 0f and 1f.
	 * 
	 * @return the chance of reaction
	 */

	public final float getChanceOfReaction() {
		return chanceOfReaction;
	}

	/**
	 * 
	 * Sets the chance of the reaction
	 * 
	 * @param chanceOfReaction
	 *            the chance of reaction
	 */

	public void setChanceOfReaction(float chanceOfReaction) {
		this.chanceOfReaction = chanceOfReaction;
	}

	/**
	 * 
	 * Gets the action performed.
	 * 
	 * @return the action
	 */

	public final int getAction() {
		return action;
	}

	/**
	 * 
	 * Sets the action performed.
	 * 
	 * @param action
	 *            the action
	 */

	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * 
	 * Gets the pair of reactional elements.
	 * 
	 * @return the pair
	 */

	public final APPair getPair() {
		return pair;
	}

	/**
	 * 
	 * Sets the pair of reactional elements.
	 * 
	 * @param pair
	 *            the pair
	 */

	public void setPair(APPair pair) {
		this.pair = pair;
	}
}
