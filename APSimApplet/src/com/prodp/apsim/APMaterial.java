package com.prodp.apsim;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Materials for APSim. This enum contains identities for many different
 * materials.
 * 
 * Buoyancy is defined as being a gas.
 * 
 * The color must be different for every face.
 * 
 * Constructor format:
 * 
 * MATERIAL(byte[24] color, boolean isBuoyant, boolean isLiquid, float
 * flowChance, short ID, int density, String name);
 * 
 * NOTE: 1-Gas 2-Liquid 3-Normal Solid 4-Dense Solid/Liquid
 * 
 */

public enum APMaterial {

	/**
	 * Null material. Used to signify nothing.
	 */

	NULL(new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
			(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
			(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
			(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
			(byte) 0 }, false, false, 0, (short) 0, 0, "Nothing"),

	/**
	 * Fire.
	 */

	FIRE(new byte[] { (byte) 187, (byte) 0, (byte) 0, (byte) 255, (byte) 127,
			(byte) 0, (byte) 0, (byte) 255, (byte) 127, (byte) 0, (byte) 0,
			(byte) 255, (byte) 187, (byte) 0, (byte) 0, (byte) 255, (byte) 63,
			(byte) 0, (byte) 0, (byte) 255, (byte) 255, (byte) 0, (byte) 0,
			(byte) 255 }, true, false, 0.1f, (short) 1, 1, "Fire"),

	/**
	 * Water.
	 */

	WATER(new byte[] { (byte) 0, (byte) 0, (byte) 187, (byte) 200, (byte) 0,
			(byte) 0, (byte) 127, (byte) 200, (byte) 0, (byte) 0, (byte) 127,
			(byte) 200, (byte) 0, (byte) 0, (byte) 187, (byte) 200, (byte) 0,
			(byte) 0, (byte) 63, (byte) 200, (byte) 0, (byte) 0, (byte) 255,
			(byte) 200 }, false, true, 1f, (short) 2, 2, "Water"),

	/**
	 * Lava.
	 */

	LAVA(new byte[] { (byte) 255, (byte) 115, (byte) 60, (byte) 255,
			(byte) 255, (byte) 55, (byte) 0, (byte) 255, (byte) 255, (byte) 55,
			(byte) 0, (byte) 255, (byte) 255, (byte) 115, (byte) 60,
			(byte) 255, (byte) 195, (byte) 0, (byte) 0, (byte) 255, (byte) 255,
			(byte) 175, (byte) 120, (byte) 255 }, false, true, 0.5f, (short) 3,
			4, "Lava"),

	/**
	 * Stone.
	 */

	STONE(new byte[] { (byte) 127, (byte) 127, (byte) 127, (byte) 255,
			(byte) 67, (byte) 67, (byte) 67, (byte) 255, (byte) 67, (byte) 67,
			(byte) 67, (byte) 255, (byte) 127, (byte) 127, (byte) 127,
			(byte) 255, (byte) 7, (byte) 7, (byte) 7, (byte) 255, (byte) 187,
			(byte) 187, (byte) 187, (byte) 255 }, false, false, 0, (short) 4,
			4, "Stone"),

	/**
	 * Steam.
	 */

	STEAM(new byte[] { (byte) 205, (byte) 205, (byte) 205, (byte) 30,
			(byte) 165, (byte) 165, (byte) 165, (byte) 30, (byte) 165,
			(byte) 165, (byte) 165, (byte) 30, (byte) 205, (byte) 205,
			(byte) 205, (byte) 30, (byte) 135, (byte) 135, (byte) 135,
			(byte) 30, (byte) 245, (byte) 245, (byte) 245, (byte) 30 }, true,
			false, 0.4f, (short) 5, 1, "Steam"),

	/**
	 * Wood.
	 */

	WOOD(new byte[] { (byte) 155, (byte) 100, (byte) 70, (byte) 255,
			(byte) 115, (byte) 64, (byte) 19, (byte) 255, (byte) 115,
			(byte) 64, (byte) 19, (byte) 255, (byte) 155, (byte) 100,
			(byte) 70, (byte) 255, (byte) 85, (byte) 40, (byte) 5, (byte) 255,
			(byte) 185, (byte) 150, (byte) 120, (byte) 255 }, false, false, 0,
			(short) 6, 3, "Wood"),

	/**
	 * Ice.
	 */

	ICE(new byte[] { (byte) 225, (byte) 225, (byte) 255, (byte) 55, (byte) 200,
			(byte) 200, (byte) 255, (byte) 55, (byte) 200, (byte) 200,
			(byte) 255, (byte) 55, (byte) 225, (byte) 225, (byte) 255,
			(byte) 55, (byte) 180, (byte) 180, (byte) 225, (byte) 55,
			(byte) 245, (byte) 245, (byte) 255, (byte) 55 }, false, true,
			0.01f, (short) 7, 3, "Ice"),

	/**
	 * Bouncy Ball.
	 */
	BALL(new byte[] { (byte) 255, (byte) 192, (byte) 203, (byte) 255,
			(byte) 208, (byte) 200, (byte) 240, (byte) 211, (byte) 200,
			(byte) 255, (byte) 212, (byte) 200, (byte) 166, (byte) 62,
			(byte) 81, (byte) 222, (byte) 180, (byte) 133, (byte) 191,
			(byte) 156, (byte) 152, (byte) 230, (byte) 187, (byte) 190 },
			false, false, 0f, (short) 8, 3, "Bouncy Ball");

	private byte[] color = new byte[96];
	private boolean isBuoyant, isLiquid;
	private float flowChance;
	private int density;
	private short ID;
	private String name;

	/**
	 * Necessary default construct for builders.
	 */

	APMaterial() {
	}

	/**
	 * 
	 * Main constructor. All the attributes are passed here directly.
	 * 
	 * @param colors
	 *            the face colors of the material
	 * @param isBuoyant
	 *            flag indicating buoyancy
	 * @param isLiquid
	 *            flag indicating if this is a fluid
	 * @param flowChance
	 *            if the material is a fluid, indicates the chance of the fluid
	 *            flowing
	 * @param ID
	 *            the id associated with the material
	 * @param density
	 *            the density of the material used in sinking and rising
	 * @param name
	 *            the name of the material displayed in the material chooser
	 */

	APMaterial(byte[] colors, boolean isBuoyant, boolean isLiquid,
			float flowChance, short ID, int density, String name) {

		if (colors.length != 24)
			throw new ArrayIndexOutOfBoundsException(
					"Color array not of length 24");

		int i = 0;

		for (; i < 16; i++)
			color[i] = colors[i % 4];
		for (; i < 32; i++)
			color[i] = colors[4 + (i % 4)];
		for (; i < 48; i++)
			color[i] = colors[8 + (i % 4)];
		for (; i < 64; i++)
			color[i] = colors[12 + (i % 4)];
		for (; i < 80; i++)
			color[i] = colors[16 + (i % 4)];
		for (; i < 96; i++)
			color[i] = colors[20 + (i % 4)];

		this.isBuoyant = isBuoyant;
		this.isLiquid = isLiquid;
		this.flowChance = flowChance;
		this.ID = ID;
		this.density = density;
		this.name = name;
	}

	/**
	 * 
	 * Builder function. Sets the colors of the material.
	 * 
	 * @param colors
	 *            the colors of the material
	 * @return a built APMaterial to be used with other builders
	 */

	APMaterial colors(byte[] colors) {
		int i = 0;

		for (; i < 16; i++)
			color[i] = colors[i % 4];
		for (; i < 32; i++)
			color[i] = colors[4 + (i % 4)];
		for (; i < 48; i++)
			color[i] = colors[8 + (i % 4)];
		for (; i < 64; i++)
			color[i] = colors[12 + (i % 4)];
		for (; i < 80; i++)
			color[i] = colors[16 + (i % 4)];
		for (; i < 96; i++)
			color[i] = colors[20 + (i % 4)];

		return this;
	}

	/**
	 * 
	 * Builder function. Sets the name of the material.
	 * 
	 * @param name
	 *            the name of the material
	 * @return a built APMaterial to be used with other builders.
	 */

	APMaterial name(String name) {
		setName(name);
		return this;
	}

	/**
	 * 
	 * Builder function. Sets the identity flags of the material.
	 * 
	 * @param isBuoyant
	 *            buoyancy flag of the material
	 * @param isLiquid
	 *            liquid flag of the material
	 * @param movesOB
	 *            unused
	 * @return a built APMaterial to be used with other builders.
	 */

	APMaterial flags(boolean isBuoyant, boolean isLiquid, boolean movesOB) {
		this.isBuoyant = isBuoyant;
		this.isLiquid = isLiquid;

		return this;
	}

	/**
	 * 
	 * Builder function. Sets the liquid flow chance for the material.
	 * 
	 * @param flowChance
	 *            the flow chance
	 * @return a built APMaterial to be used with other builders.
	 */

	APMaterial flow(float flowChance) {
		this.flowChance = flowChance;

		return this;
	}

	/**
	 * 
	 * Builder function. Sets the ID of the material.
	 * 
	 * @param ID
	 *            the ID
	 * @return a built APMaterial to be used with other builders.
	 */

	APMaterial ID(short ID) {
		this.ID = ID;

		return this;
	}

	/**
	 * 
	 * Builder function. Sets the density of the material.
	 * 
	 * @param density
	 *            the density
	 * @return a built APMaterial to be used with other builders.
	 */

	APMaterial density(int density) {
		this.density = density;

		return this;
	}

	/**
	 * 
	 * Gets the colors of the material.
	 * 
	 * @return the colors
	 */

	public byte[] getColors() {
		return color;
	}

	/**
	 * 
	 * Gets the flow chance of the material.
	 * 
	 * @return the flow chance
	 */

	public float getFlowChance() {
		return flowChance;
	}

	/**
	 * 
	 * Sets the flow chance of the material.
	 * 
	 * @param fc
	 *            the flow chance
	 */

	public void setFlowChance(float fc) {
		flowChance = fc;
	}

	/**
	 * 
	 * Gets the ID of the material.
	 * 
	 * @return the ID
	 */

	public short getID() {
		return ID;
	}

	/**
	 * 
	 * Sets if the material is buoyant.
	 * 
	 * @param a
	 *            the flag
	 */

	public void setIsBuoyant(boolean a) {
		isBuoyant = a;
	}

	/**
	 * 
	 * Sets if the material is a liquid.
	 * 
	 * @param a
	 *            the flag
	 */

	public void setIsLiquid(boolean a) {
		isLiquid = a;
	}

	/**
	 * 
	 * Gets if the material is liquid.
	 * 
	 * @return the flag
	 */

	public final boolean getIsLiquid() {
		return isLiquid;
	}

	/**
	 * 
	 * Gets if the material is buoyant.
	 * 
	 * @return the flag
	 */

	public final boolean getIsBuoyant() {
		return isBuoyant;
	}

	/**
	 * 
	 * Gets the density of the material.
	 * 
	 * @return the density.
	 */

	public int getDensity() {
		return density;
	}

	/**
	 * 
	 * Sets the density of the material.
	 * 
	 * @param density
	 *            the density
	 */

	public void setDensity(int density) {
		this.density = density;
	}

	/**
	 * 
	 * Gets the name of the material.
	 * 
	 * @return the name of the material
	 */

	public String getName() {
		return name;
	}

	/**
	 * 
	 * Sets the name of the material.
	 * 
	 * @param name
	 *            the new name of the material.
	 */

	public void setName(String name) {
		this.name = name;
	}
}
