package com.prodp.apsim;

import javax.vecmath.Point3i;

// Utility class for internal array processes

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 6-10-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * This class contains many important array utilities and methods that are
 * necessary for processing (game internals). The {@link Flagger} associated
 * with this class is to make sure that the method
 * {@link #doReqCheckLoopActions(APProcess, int)} does not call more than
 * {@link APFinalData #LIMIT}. The indices array is used to label what blocks
 * are around the current block. The indices of the block (around the block) are
 * put into this array (the index of the block means the position of the block
 * in the coordinate array and the HashMaps associated with it). See
 * {@link APProcess} for more info.
 * 
 */

public class APArrayUtils extends APObject {

	private static int[] indices = new int[26];
	private static final Flagger fl = new Flagger(APFinalData.LIMIT);

	/**
	 * 
	 * Returns the Flagger associated. See {@link Flagger} for more information
	 * about flaggers.
	 * 
	 * @return fl a {@link Flagger}
	 */

	public static final Flagger getFlagger() {
		return fl;
	}

	/**
	 * 
	 * Finds the first occurence of an empty space in the status array.
	 * Emptiness is defined as having an occurence of {@link APMaterial}.NULL,
	 * which is represented by the integer 0.
	 * 
	 * @param a
	 *            the status array to be checked containing the material ids of
	 *            each block.
	 * 
	 * @return i the index of the empty space, or -1 if there is no empty space.
	 */

	// Finds any status in the status array that is APMaterial.NULL
	public final static int findEmptySpace(short[] a) {
		for (int i = 0; i < APFinalData.LIMIT; i++)
			if (a[i] == 0)
				return i;
		return -1;
	}

	/**
	 * 
	 * Sets the other 23 vertices of the cube to their respective coordinates.
	 * 
	 * Since a cube has 24 vertices, and there is an x, y, z tuple for every
	 * vertex, this method copies a float array containing {x, y, z, x + XUNIT,
	 * y, z, x + XYUNIT, y + XYUNIT, z, ...} into 72 places such that XUNIT is a
	 * tuple containing (UNIT, 0, 0), XYUNIT is (UNIT, UNIT, 0), and so on with
	 * YUNIT, ZUNIT, XZUNIT, YZUNIT, and XYZUNIT. UNIT is an arbitrary float. To
	 * put it visually:
	 * 
	 * <pre>
	 *          h_____________g
	 *         /|            /|
	 *        e_|___________f |
	 *        | |           | |
	 *        | |           | |
	 *        | |           | |
	 *        | d___________|_c
	 *        |/            |/
	 *        a_____________b
	 * 
	 * </pre>
	 * 
	 * With a cube of this dimension, the point represented by "a" in this
	 * diagram represents the point (x, y, z). b is (x + XUNIT, y, z) c is (x +
	 * XZUNIT, y, z + XZUNIT) ... e is (x, y + YUNIT, z).
	 * 
	 * Note to developer: (X)(Y)(Z)UNIT is represented by BOX(X)(Y)(Z).
	 * 
	 * @param block
	 *            the array containing <b>all</b> the cube coordinates
	 * @param index
	 *            the index of the current box focused on out of all the boxes
	 */

	// Maps the other 23 coordinates to a cube block (necessary)s
	public final static void setCoordBlocks(float[] block, final int index) {

		final float x = block[index], y = block[index + 1], z = block[index + 2];

		System.arraycopy(new float[] { x, y, z, APFinalData.BOXX.x + x, y, z,
				APFinalData.BOXXY.x + x, APFinalData.BOXXY.y + y, z, x,
				APFinalData.BOXY.y + y, z, x, y, APFinalData.BOXZ.z + z, x, y,
				z, x, APFinalData.BOXY.y + y, z, x, APFinalData.BOXYZ.y + y,
				APFinalData.BOXYZ.z + z, APFinalData.BOXXZ.x + x, y,
				APFinalData.BOXXZ.z + z, APFinalData.BOXX.x + x, y, z,
				APFinalData.BOXXY.x + x, APFinalData.BOXXY.y + y, z,
				APFinalData.BOXXYZ.x + x, APFinalData.BOXXYZ.y + y,
				APFinalData.BOXXYZ.z + z, APFinalData.BOXZ.x + x, y,
				APFinalData.BOXZ.z + z, APFinalData.BOXXZ.x + x, y,
				APFinalData.BOXXZ.z + z, APFinalData.BOXXYZ.x + x,
				APFinalData.BOXXYZ.y + y, APFinalData.BOXXYZ.z + z, x,
				APFinalData.BOXYZ.y + y, APFinalData.BOXYZ.z + z, x, y, z,
				APFinalData.BOXX.x + x, y, z, APFinalData.BOXXZ.x + x, y,
				APFinalData.BOXXZ.z + z, x, y, APFinalData.BOXZ.z + z, x,
				APFinalData.BOXY.y + y, z, APFinalData.BOXXY.x + x,
				APFinalData.BOXXY.y + y, z, APFinalData.BOXXYZ.x + x,
				APFinalData.BOXXYZ.y + y, APFinalData.BOXXYZ.z + z, x,
				APFinalData.BOXYZ.y + y, APFinalData.BOXYZ.z + z }, 0, block,
				index, 72);
	}

	/**
	 * 
	 * Sets the other 23 vertices of the cube to their respective colors.
	 * 
	 * Since a cube has 24 vertices, and there is an r, g, b, a tuple for every
	 * vertex, this method copies a byte array containing the 96 bytes
	 * containing the red, green, blue, and alpha values of the
	 * {@link APMaterial} colors.
	 * 
	 * @param color
	 *            the array containing all the block colors
	 * @param a
	 *            the {@link APMaterial} containing the color of the block
	 * @param index
	 *            the index of the current box focused on out of all the boxes
	 */

	// Maps the other 23 colors to a cube block
	public static final void setColorBlocks(byte[] color, final APMaterial a,
			final int index) {
		System.arraycopy(a.getColors(), 0, color, index, 96);
	}

	/**
	 * 
	 * Checks if there is liquid (defined in {@link APMaterial}) around the
	 * block in a certain radius.
	 * 
	 * This is necessary, separate from the
	 * {@link #computeAroundIndices(APProcess, int)} process since that method
	 * only computes indices around the block with a radius of 1.
	 * 
	 * @param process
	 *            the current {@link APProcess} (screen)
	 * @param distance
	 *            the radius of which to check around in
	 * @param index
	 *            the index of the current box focused on out of all the boxes
	 * @return the index of the first occurence of a liquid or -1 if none
	 */

	// Check if a block is around a liquid
	public static final int isAroundLiquid(final APProcess process,
			final int distance, int index) {
		// default index is -1
		for (int i = -distance; i <= distance; i++)
			for (int j = -distance; j <= distance; j++)
				for (int k = -distance; k <= distance; k++) {
					if (!(i == 0 && j == 0 && k == 0)) {
						Point3i search = new Point3i(
								process.realcoords[index * 3] + i,
								process.realcoords[index * 3 + 1] + j,
								process.realcoords[index * 3 + 2] + k);
						if (process.reversecoordsort.containsKey(search))
							return process.reversecoordsort.get(search);
					}
				}
		return -1;
	}

	/**
	 * 
	 * Performs liquid flow of a block if it is a liquid.
	 * 
	 * More specifically, it looks around for another block, a "sticky" block to
	 * stick to, and when it finds this partner by moving towards it, it stops
	 * moving. All the processes that modify the block's position are
	 * concatenated to the block's velocity so that they can be checked for
	 * collisions later.
	 * 
	 * The algorithm:
	 * 
	 * Step 1:
	 * 
	 * Looks around for sticky indices (other liquid blocks), then moves towards
	 * them if possible.
	 * 
	 * Step 2:
	 * 
	 * Assuming the block has moved from this velocity change in Step 1, it
	 * checks if the block's new coordinate (with the velocity added) is
	 * 
	 * @param process
	 *            the current {@link APProcess} (screen)
	 * @param index
	 *            the index of the current box focused on out of all the boxes
	 */

	private static final void performLiquidFlow(final APProcess process,
			final int index) {
		int stickyindex = -1;

		if ((stickyindex = isAroundLiquid(process, 2, index)) != -1
				&& process.realcoords[index * 3 + 1] == 0) {

			process.velocity[index].x = (byte) (Math
					.signum(process.realcoords[stickyindex * 3]
							- process.realcoords[index * 3]));
			process.velocity[index].y = (byte) (Math
					.signum(process.realcoords[stickyindex * 3 + 1]
							- process.realcoords[index * 3 + 1]));
			process.velocity[index].z = (byte) (Math
					.signum(process.realcoords[stickyindex * 3 + 2]
							- process.realcoords[index * 3 + 2]));

		} else {

			Point3i check = new Point3i(process.realcoords[index * 3]
					+ process.velocity[index].x,
					process.realcoords[index * 3 + 1]
							+ process.velocity[index].y,
					process.realcoords[index * 3 + 2]
							+ process.velocity[index].z);

			for (int b : indices) {
				if (!process.reversecoordsort.containsKey(check)) {

					if (b != -1) {
						process.velocity[index].x += process.velocity[b].x;
						process.velocity[index].y += process.velocity[b].y;
						process.velocity[index].z += process.velocity[b].z;
					}

				} else {

					process.velocity[index].x = (byte) (APFinalData.random
							.nextInt(3) - 1);
					process.velocity[index].y = (byte) (APFinalData.random
							.nextInt(3) - 1);
					process.velocity[index].z = (byte) (APFinalData.random
							.nextInt(3) - 1);

				}
			}

			if (process.velocity[index].x > 0)
				process.velocity[index].x = 1;
			else if (process.velocity[index].x < 0)
				process.velocity[index].x = -1;

			if (process.velocity[index].y > 0)
				process.velocity[index].y = 0;
			// else if(process.velocity[index].y < 0)
			// process.velocity[index].y = -1;

			if (process.velocity[index].z > 0)
				process.velocity[index].z = 1;
			else if (process.velocity[index].z < 0)
				process.velocity[index].z = -1;
		}
	}

	/**
	 * 
	 * Uses the {@link APReaction} list from the {@link APReactionList}
	 * associated with the {@link APProcessHandler}, and processes the reactions
	 * on the specified block, if any.
	 * 
	 * It takes the current {@link APMaterial} of the specific block by
	 * converting it to an APMaterial from a short via
	 * {@link com.prodp.apsim.APMaterialsList #getMaterialByID(short)}. It loops
	 * through the blocks around the current block, and if it isn't null (-1),
	 * it tries to search for the {@link APPair} of the material of the current
	 * block, and the material of the block around it. If this is found in the
	 * reactions list, it runs a check to see if a random float is less than the
	 * chance of the reaction. If it passes these tests, it gets the action of
	 * the reaction, an int, and when passed through a switch block, it can:
	 * 
	 * Change the block to another block,
	 * 
	 * or
	 * 
	 * Remove the block.
	 * 
	 * @param process
	 *            the current {@link APProcess} (screen)
	 * @param i
	 *            the index of the current box focused on out of all the boxes
	 */

	// TODO combine!

	public static final void performReactions(final APProcess process,
			final int i) {

		for (int j : indices) {
			if (j != -1) {
				APPair search = new APPair(process.status[i], process.status[j]);

				APReaction a = APProcessHandler.APRList.getReaction(search);

				if (a != null
						&& APFinalData.random.nextFloat() < a
								.getChanceOfReaction()) {

					int action = a.getAction();

					if (action == APFinalData.CHANGE_BLOCK_FLAG)
						APProcessHandler
								.changeBlock(process, i, a.getProduct());
					else
						APProcessHandler.removeBlock(process, i);

					break;

				}
			}
		}
	}

	/**
	 * 
	 * Makes some blocks sink if they are heavier than the block below them.
	 * 
	 * NOTE: 16, in the list of indices is the top center block. The process
	 * checks the block above the current block to see if it is lighter or
	 * heavier, similar to what was discribed above. The number 16, substituted
	 * for the definitely harder method of checking the top block's index, has
	 * speed benefits.
	 * 
	 * @param process
	 *            the current {@link APProcess} (screen)
	 * @param index
	 *            the index of the current box focused on out of all the boxes
	 */

	// TODO Make this faster!!!
	public static void exchangeDensities(final APProcess process, int index) {
		int tempind = 0;

		if (process.status[index] != 0 && process.velocity[index].y == -1
				&& (tempind = indices[16]) != -1 // 16 is top center
				&& APMaterialsList
						.getMaterialDensityByID(process.status[index]) < APMaterialsList
						.getMaterialDensityByID(process.status[tempind]))
			APProcessHandler.switchBlock(process, index, tempind);
	}

	/**
	 * 
	 * Computes the blocks around the current block.
	 * 
	 * Since it is O(n^3), it is quite costly, but it only iterates over 27-1 =
	 * 26 indices. Even so, the 3 nested loops cause problems. This process
	 * takes each coordinate around the block and searches in the coordinate
	 * array to see if it exists.
	 * 
	 * @param process
	 *            the current {@link APProcess} (screen)
	 * @param index
	 *            the index of the current box focused on out of all the boxes
	 */

	public static void computeAroundIndices(APProcess process, int index) {

		int j = 0;
		// Arrays.fill(indices, -1); // costly

		Point3i coordinate = new Point3i(process.realcoords[index * 3],
				process.realcoords[index * 3 + 1],
				process.realcoords[index * 3 + 2]);

		/*
		 * 
		 * -1 -> 1 -1 -> 1 -1 -> 1
		 * 
		 * 26, don't count center
		 * 
		 * Brute force search!! (Not good)
		 */

		for (int i = -1; i <= 1; i++)
			for (int k = -1; k <= 1; k++)
				for (int l = -1; l <= 1; l++)
					if (!(i == 0 && k == 0 && l == 0)) {
						Point3i loccoord = new Point3i(coordinate.x + i,
								coordinate.y + k, coordinate.z + l);
						Integer putindex;

						putindex = process.reversecoordsort.get(loccoord);

						if (putindex != null)
							indices[j] = putindex;
						else
							indices[j] = -1;

						j++;
					}
	}

	/**
	 * 
	 * Container method for all the processing requiring checking the status of
	 * the block via loop actions.
	 * 
	 * Does some preliminary work with some other functions and then tries to
	 * move the block to the specified position dictated by the velocity. The
	 * velocities are CHECKED before the move, so any Newtonic forces associated
	 * with Newton's 3rd Law of Motion are already covered in the previous
	 * functions.
	 * 
	 * @param process
	 *            the current {@link APProcess} (screen)
	 * @param i
	 *            the index of the current box focused on out of all the boxes
	 */

	public static void doReqCheckLoopActions(final APProcess process, int i) {

		final APMaterial currentStatMat = APMaterialsList
				.getMaterialByID(process.status[i]);

		// Spread All Liquids
		if ((currentStatMat.getIsLiquid() || currentStatMat.getIsBuoyant())
				&& APFinalData.random.nextDouble() < currentStatMat
						.getFlowChance()) {

			performLiquidFlow(process, i);

		}

		// Perform all the reactions and exchange the block's position based on
		// density (e.g. oil floats on top of water)
		performReactions(process, i);
		exchangeDensities(process, i);

		APVelocity addend = new APVelocity(process.velocity[i]);

		// The velocities are CHECKED in the liquid flow algorithm

		Point3i point = new Point3i(process.realcoords[i * 3] + addend.x,
				process.realcoords[i * 3 + 1] + addend.y,
				process.realcoords[i * 3 + 2] + addend.z);

		if (process.isFrozen())
			addend = new APVelocity();

		// Move (checked)
		if (point.y > -1 && !process.reversecoordsort.containsKey(point)) {

			process.realcoords[i * 3] += addend.x;
			process.realcoords[i * 3 + 1] += addend.y;
			process.realcoords[i * 3 + 2] += addend.z;

			process.coords[i * 24 * 3] += addend.x * APFinalData.BOXSIZE;
			process.coords[i * 24 * 3 + 1] += addend.y * APFinalData.BOXSIZE;
			process.coords[i * 24 * 3 + 2] += addend.z * APFinalData.BOXSIZE;

			// Point3i coordinate = new Point3i(process.realcoords[i * 3],
			// process.realcoords[i * 3 + 1],
			// process.realcoords[i * 3 + 2]);

			process.reversecoordsort.remove(process.coordsort.get(i));
			Integer rec = process.reversecoordsort.put(point, i);
			process.coordsort.put(i, point);

			fl.set(i, true);

			if (rec != null && rec != i && !fl.get(rec))
				// conditional recursive call
				computeAroundIndices(process, rec);
		}

	}

	/**
	 * 
	 * Clears the {@link Flagger} associated with the utility.
	 * 
	 */

	public static void clearFlagger() {
		fl.clear();
	}
}
