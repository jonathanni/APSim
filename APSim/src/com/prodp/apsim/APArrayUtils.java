package com.prodp.apsim;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;
import javax.vecmath.Vector3f;

// Utility class for internal array processes

/**
 * 
 * @author Jonathan
 * @version 0.2
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

	private static int[] indices = new int[27];
	private static final Flagger fl = new Flagger(APFinalData.LIMIT);
	private static float vr1x = 0, vr1y = 0, vr1z = 0, vr2x = 0, vr2y = 0,
			vr2z = 0;
	private static int error;

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

	/*****************************************************************************
	 * // MODIFIED for APSim // // This program is a 'remote' 3D-collision
	 * detector for two balls on linear // trajectories and returns, if
	 * applicable, the location of the collision // for // both balls as well as
	 * the new velocity vectors (assuming a partially // elastic // collision as
	 * defined by the restitution coefficient). // // All variables apart from
	 * 'error' are of Double Precision Floating Point // type. // // The
	 * Parameters are: // // R (restitution coefficient) between 0 and 1
	 * (1=perfectly elastic // collision) // m1 (mass of ball 1) // m2 (mass of
	 * ball 2) // r1 (radius of ball 1) // r2 (radius of ball 2) // x1
	 * (x-coordinate of ball 1) // y1 (y-coordinate of ball 1) // z1
	 * (z-coordinate of ball 1) // x2 (x-coordinate of ball 2) // y2
	 * (y-coordinate of ball 2) // z2 (z-coordinate of ball 2) // vx1 (velocity
	 * x-component of ball 1) // vy1 (velocity y-component of ball 1) // vz1
	 * (velocity z-component of ball 1) // vx2 (velocity x-component of ball 2)
	 * // vy2 (velocity y-component of ball 2) // vz2 (velocity z-component of
	 * ball 2) // error (int) (0: no error // 1: balls do not collide // 2:
	 * initial positions impossible (balls overlap)) // // Note that the
	 * parameters with an ampersand (&) are passed by reference, // i.e. the
	 * corresponding arguments in the calling program will be updated // (the
	 * positions and velocities however only if 'error'=0). // All variables
	 * should have the same data types in the calling program // and all should
	 * be initialized before calling the function. // // This program is free to
	 * use for everybody. However, you use it at your // own // risk and I do
	 * not accept any liability resulting from incorrect // behaviour. // I have
	 * tested the program for numerous cases and I could not see anything //
	 * wrong with it but I can not guarantee that it is bug-free under any //
	 * circumstances. // // I would appreciate if you could report any problems
	 * to me // (for contact details see
	 * http://www.plasmaphysics.org.uk/feedback.htm ). // // Thomas Smid
	 * February 2004 // December 2005 (a few minor changes to improve speed) //
	 * December 2009 (generalization to partially inelastic collisions) // July
	 * 2011 (fix for possible rounding errors)
	 ******************************************************************************/

	private static int collision3D(boolean bouncy, float m1, float m2,
			float r1, float r2, Point3f pf1, Point3f pf2, Vector3f vf1,
			Vector3f vf2) {

		float R = bouncy ? 1 : (float) Math.random();
		float x1 = pf1.x, y1 = pf1.y, z1 = pf1.z, x2 = pf2.x, y2 = pf2.y, z2 = pf2.z;
		float vx1 = vf1.x, vy1 = vf1.y, vz1 = vf1.z, vx2 = vf2.x, vy2 = vf2.y, vz2 = vf2.z;

		float pi, r12, m21, d, v, theta2, phi2, st, ct, sp, cp, vx1r, vy1r, vz1r;
		float fvz1r, thetav, phiv, dr, alpha, beta, sbeta, cbeta, t, a, dvz2;
		float vx2r, vy2r, vz2r, x21, y21, z21, vx21, vy21, vz21, vx_cm, vy_cm, vz_cm;

		// **** initialize some variables ****
		pi = (float) Math.acos(-1.0E0);
		r12 = r1 + r2;
		m21 = m2 / m1;
		x21 = x2 - x1;
		y21 = y2 - y1;
		z21 = z2 - z1;
		vx21 = vx2 - vx1;
		vy21 = vy2 - vy1;
		vz21 = vz2 - vz1;

		vx_cm = (m1 * vx1 + m2 * vx2) / (m1 + m2);
		vy_cm = (m1 * vy1 + m2 * vy2) / (m1 + m2);
		vz_cm = (m1 * vz1 + m2 * vz2) / (m1 + m2);

		// **** calculate relative distance and relative speed ***
		d = (float) Math.sqrt(x21 * x21 + y21 * y21 + z21 * z21);
		v = (float) Math.sqrt(vx21 * vx21 + vy21 * vy21 + vz21 * vz21);

		// **** return if distance between balls smaller than sum of radii ****
		if (d < r12)
			return 2;

		// **** return if relative speed = 0 ****
		if (v == 0)
			return 1;

		// **** shift coordinate system so that ball 1 is at the origin ***
		x2 = x21;
		y2 = y21;
		z2 = z21;

		// **** boost coordinate system so that ball 2 is resting ***
		vx1 = -vx21;
		vy1 = -vy21;
		vz1 = -vz21;

		// **** find the polar coordinates of the location of ball 2 ***
		theta2 = (float) Math.acos(z2 / d);
		phi2 = (x2 == 0 && y2 == 0 ? 0 : (float) Math.atan2(y2, x2));

		st = (float) Math.sin(theta2);
		ct = (float) Math.cos(theta2);
		sp = (float) Math.sin(phi2);
		cp = (float) Math.cos(phi2);

		// **** express the velocity vector of ball 1 in a rotated coordinate
		// system where ball 2 lies on the z-axis ******
		vx1r = ct * cp * vx1 + ct * sp * vy1 - st * vz1;
		vy1r = cp * vy1 - sp * vx1;
		vz1r = st * cp * vx1 + st * sp * vy1 + ct * vz1;
		fvz1r = vz1r / v;

		if (fvz1r > 1)
			fvz1r = 1;
		// fix for possible rounding errors
		else if (fvz1r < -1)
			fvz1r = -1;

		thetav = (float) Math.acos(fvz1r);

		phiv = (vx1r == 0 && vy1r == 0 ? 0 : (float) Math.atan2(vy1r, vx1r));

		// **** calculate the normalized impact parameter ***
		dr = (float) (d * Math.sin(thetav) / r12);

		// **** return old positions and velocities if balls do not collide ***
		if (thetav > pi / 2 || Math.abs(dr) > 1) {
			x2 = x2 + x1;
			y2 = y2 + y1;
			z2 = z2 + z1;
			vx1 = vx1 + vx2;
			vy1 = vy1 + vy2;
			vz1 = vz1 + vz2;
			return 1;
		}

		// **** calculate impact angles if balls do collide ***
		alpha = (float) Math.asin(-dr);
		beta = phiv;
		sbeta = (float) Math.sin(beta);
		cbeta = (float) Math.cos(beta);

		// **** calculate time to collision ***
		t = (float) ((d * Math.cos(thetav) - r12 * Math.sqrt(1 - dr * dr)) / v);

		// **** update positions and reverse the coordinate shift ***
		x2 = x2 + vx2 * t + x1;
		y2 = y2 + vy2 * t + y1;
		z2 = z2 + vz2 * t + z1;
		x1 = (vx1 + vx2) * t + x1;
		y1 = (vy1 + vy2) * t + y1;
		z1 = (vz1 + vz2) * t + z1;

		// *** update velocities ***

		a = (float) Math.tan(thetav + alpha);

		dvz2 = 2 * (vz1r + a * (cbeta * vx1r + sbeta * vy1r))
				/ ((1 + a * a) * (1 + m21));

		vz2r = dvz2;
		vx2r = a * cbeta * dvz2;
		vy2r = a * sbeta * dvz2;
		vz1r = vz1r - m21 * vz2r;
		vx1r = vx1r - m21 * vx2r;
		vy1r = vy1r - m21 * vy2r;

		// **** rotate the velocity vectors back and add the initial velocity
		// vector of ball 2 to retrieve the original coordinate system ****

		vx1 = ct * cp * vx1r - sp * vy1r + st * cp * vz1r + vx2;
		vy1 = ct * sp * vx1r + cp * vy1r + st * sp * vz1r + vy2;
		vz1 = ct * vz1r - st * vx1r + vz2;
		vx2 = ct * cp * vx2r - sp * vy2r + st * cp * vz2r + vx2;
		vy2 = ct * sp * vx2r + cp * vy2r + st * sp * vz2r + vy2;
		vz2 = ct * vz2r - st * vx2r + vz2;

		// *** velocity correction for inelastic collisions ***

		vr1x = (vx1 - vx_cm) * R + vx_cm;
		vr1y = (vy1 - vy_cm) * R + vy_cm;
		vr1z = (vz1 - vz_cm) * R + vz_cm;
		vr2x = (vx2 - vx_cm) * R + vx_cm;
		vr2y = (vy2 - vy_cm) * R + vy_cm;
		vr2z = (vz2 - vz_cm) * R + vz_cm;

		return 0;
	}

	private static final float jitter(boolean active) {
		return active ? (float) Math.random() - 0.5f : 0;
	}

	/**
	 * 
	 * Performs a collision between two blocks (only two blocks).
	 * 
	 * Takes the location of the block the current block is going towards
	 * 
	 * @param process
	 * @param index
	 */
	private static final void performCollisions(final APProcess process,
			final int index) {
		Point3i check = new Point3i((int) Math.signum(Math
				.round(process.dVelocity[index].x)), (int) Math.signum(Math
				.round(process.dVelocity[index].y)), (int) Math.signum(Math
				.round(process.dVelocity[index].z)));
		APMaterial currentStatMat = APMaterialsList
				.getMaterialByID(process.status[index]);
		APMaterial targetStatMat;

		// for (int i = (check.x == 0 ? -1 : check.x); i < (check.x == 0 ? 2
		// : check.x + 1); i++)
		// for (int j = (check.y == 0 ? -1 : check.y); j < (check.y == 0 ? 2
		// : check.y + 1); j++)
		// for (int k = (check.z == 0 ? -1 : check.z); k < (check.z == 0 ? 2
		// : check.z + 1); k++) {
		int index_checked = indices[(check.x + 1) * 9 + (check.y + 1) * 3
				+ (check.z + 1)];// (i + 1) * 9 + (k + 1) * 3 + (k + 1);

		// null index
		if (index_checked == -1)
			return;

		// set the target material
		targetStatMat = APMaterialsList
				.getMaterialByID(process.status[index_checked]);

		boolean jitter = false;

		// Spread All Liquids
		if (((currentStatMat.getIsLiquid() || currentStatMat.getIsBuoyant())
				&& (targetStatMat.getIsLiquid() || targetStatMat.getIsBuoyant()) && APFinalData.random
				.nextDouble() < currentStatMat.getFlowChance())
				|| currentStatMat.equals(APMaterial.BALL))
			jitter = true;

		Point3f jp = new Point3f(jitter(jitter), jitter(jitter), jitter(jitter));
		Point3f p1 = new Point3f((float) process.realcoords[index * 3],
				(float) process.realcoords[index * 3 + 1],
				(float) process.realcoords[index * 3 + 2]);
		p1.add(jp);

		error = collision3D(
				currentStatMat.equals(APMaterial.BALL),
				1,
				1,
				(float) Math.sqrt(Math.pow(jp.x, 2) + Math.pow(1 + jp.y, 2)
						+ Math.pow(jp.z, 2)) - 0.5f,
				0.5f,
				p1,
				new Point3f((float) process.realcoords[index_checked * 3],
						(float) process.realcoords[index_checked * 3 + 1],
						(float) process.realcoords[index_checked * 3 + 2]),
				new Vector3f(process.dVelocity[index].x,
						process.dVelocity[index].y, process.dVelocity[index].z),
				new Vector3f(process.dVelocity[index_checked].x,
						process.dVelocity[index_checked].y,
						process.dVelocity[index_checked].z));

		if (error == 0) {

			process.dVelocity[index].x = vr1x;
			process.dVelocity[index].y = vr1y;
			process.dVelocity[index].z = vr1z;

			process.dVelocity[index_checked].x = vr2x;
			process.dVelocity[index_checked].y = vr2y;
			process.dVelocity[index_checked].z = vr2z;
		}
	}

	/**
	 * 
	 * As of version 0.2, this function is replaced by
	 * {@link #performCollisions(APProcess, int)}.
	 * 
	 * @deprecated
	 */

	@SuppressWarnings("unused")
	private static final void performLiquidFlow(final APProcess process,
			final int index) {
		return;
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

				APReaction a = APProcessHandler.APRList.getReaction(
						process.status[i], process.status[j]);

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

		if (process.status[index] != 0
				&& Math.signum(process.dVelocity[index].y) == -1
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
				for (int l = -1; l <= 1; l++) {
					Point3i loccoord = new Point3i(coordinate.x + i,
							coordinate.y + k, coordinate.z + l);
					Integer putindex;

					putindex = process.reversecoordsort.get(loccoord);

					if (putindex != null)
						indices[(i + 1) * 9 + (k + 1) * 3 + (l + 1)] = putindex;
					else
						indices[(i + 1) * 9 + (k + 1) * 3 + (l + 1)] = -1;
				}

		indices[13] = -1; // center block is own block
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

		// Do All Collisions
		performCollisions(process, i);

		// Perform all the reactions and exchange the block's position based on
		// density (e.g. oil floats on top of water)
		performReactions(process, i);
		exchangeDensities(process, i);

		// Perform adhesion/cohesion on particles
		performCoAdhesion(process, i);

		// ^ ^ ^ Very Low Time

		// System.out.println(process.dVelocity[i]);

		// simple "Friction"

		float k = 0.5f * 1.225f * 0.49f;
		process.dVelocity[i].x -= (Math.abs(process.dVelocity[i].x) < 1 ? process.dVelocity[i].x
				: Math.signum(process.dVelocity[i].x))
				+ k
				* (process.dVelocity[i].x * Math.abs(process.dVelocity[i].x))
				/ 1000.0f;
		process.dVelocity[i].y -= k
				* (process.dVelocity[i].y * Math.abs(process.dVelocity[i].y))
				/ 1000.0f;
		// a = F/m, Fd=1/2pv^2CdA (Newton's second law, drag equation)
		process.dVelocity[i].z -= (Math.abs(process.dVelocity[i].z) < 1 ? process.dVelocity[i].z
				: Math.signum(process.dVelocity[i].z))
				+ k
				* (process.dVelocity[i].z * Math.abs(process.dVelocity[i].z))
				/ 1000.0f;

		// Round 2 setting (tick is 0.1 seconds, so must divide by 10)
		APVelocity addend = new APVelocity(process.dVelocity[i].multiply(0.1f));
		Point3i point = new Point3i(process.realcoords[i * 3] + addend.x,
				process.realcoords[i * 3 + 1] + addend.y,
				process.realcoords[i * 3 + 2] + addend.z);

		// Ground absorbs all force
		if (point.y < 0) {
			process.dVelocity[i].y = 0;

			return;
		}

		// Move (checked)
		if (!process.reversecoordsort.containsKey(point)) {

			process.realcoords[i * 3] += addend.x;
			process.realcoords[i * 3 + 1] += addend.y;
			process.realcoords[i * 3 + 2] += addend.z;

			process.coords[i * 24 * 3] += addend.x * APFinalData.BOXSIZE;
			process.coords[i * 24 * 3 + 1] += addend.y * APFinalData.BOXSIZE;
			process.coords[i * 24 * 3 + 2] += addend.z * APFinalData.BOXSIZE;

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
	 * Makes particles stick to particles of the same type AND particles of
	 * different type.
	 * 
	 * @param process
	 * @param i
	 */
	private static void performCoAdhesion(APProcess process, int i) {

		if (!(APMaterialsList.isBuoyant((short) i) || APMaterialsList
				.isLiquid((short) i)))
			return;

		float cohesionRate = 1f;
		int index = isAroundLiquid(process, 2, i);

		if (index != -1 && process.status[index] != 0) {

			float ratex = (process.realcoords[index * 3] - process.realcoords[i * 3])
					/ 2f * cohesionRate, ratey = (process.realcoords[index * 3 + 1] - process.realcoords[i * 3 + 1])
					/ 2f * cohesionRate, ratez = (process.realcoords[index * 3 + 2] - process.realcoords[i * 3 + 2])
					/ 2f * cohesionRate;

			process.dVelocity[i].x += ratex;
			process.dVelocity[i].y += ratey;
			process.dVelocity[i].z += ratez;

			process.dVelocity[index].x -= ratex;
			process.dVelocity[index].y -= ratey;
			process.dVelocity[index].z -= ratez;

			return;
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
