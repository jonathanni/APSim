package com.prodp.apsim;

import java.util.Arrays;
import java.util.HashMap;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Container for APSim game data. All the necessary data per game is in this
 * class.
 * 
 */

public class APProcess {

	// flags for action listeners
	volatile boolean isPaused = true, busy = false, isRobot = true,
			isRecording = false;

	// current array count
	int aCount = 0;

	// process id
	private byte processID;

	// world
	APWorld save;

	// material
	private APMaterial currentMat = APFinalData.DEFAULT_MAT; // default

	// viewpoint rotation x and y, with mouse x and y angles
	private volatile double rotateX, rotateY;

	// Current Position
	private Point3d curPos = new Point3d(0, 2, 0);

	// Brush size
	private int brushSize = 0;

	// Is frozen
	private boolean isFrozen = false, isBoxHidden = false;

	// Video recorder
	private APVideoRecorder record;

	// MAIN BLOCK ARRAY
	// *************************************************************

	float[] windcoords = new float[APFinalData.PRESSURE_COUNT * 2 * 3 * 3];
	byte[] windcolors = new byte[APFinalData.PRESSURE_COUNT * 2 * 3 * 3];

	float[] coords = new float[APFinalData.LIMIT * 24 * 3];
	byte[] colors = new byte[APFinalData.LIMIT * 24 * 4];
	float[] texturecoords = new float[APFinalData.LIMIT * 24 * 2];
	short[] status = new short[APFinalData.LIMIT];

	// APVelocity[] velocity = new APVelocity[APFinalData.LIMIT];
	APDecimalVelocity[] dVelocity = new APDecimalVelocity[APFinalData.LIMIT];

	APPressurePoint[] pressures = new APPressurePoint[APFinalData.PRESSURE_COUNT];

	int[] realcoords = new int[APFinalData.LIMIT * 3];

	HashMap<Integer, Point3i> coordsort = new HashMap<Integer, Point3i>(
			APFinalData.LIMIT, 1f);
	HashMap<Point3i, Integer> reversecoordsort = new HashMap<Point3i, Integer>(
			APFinalData.LIMIT, 1f);

	HashMap<Point3i, Integer> reversepressuresort = new HashMap<Point3i, Integer>(
			128, 1f);

	// *************************************************************
	// MAIN BLOCK ARRAY

	/**
	 * 
	 * Gets the Euler X-Rotation (unit: pi).
	 * 
	 * Note: X rotation is around the Y-Axis.
	 * 
	 * @return the rotation in units of pi
	 */

	public double getRotateX() {
		return rotateX;
	}

	/**
	 * 
	 * Sets the Euler X-Rotation.
	 * 
	 * @param rotateX
	 *            the rotation
	 */

	public void setRotateX(double rotateX) {
		this.rotateX = rotateX;
	}

	/**
	 * 
	 * Gets the Euler Y-Rotation (unit: pi).
	 * 
	 * Note: Y rotation is around the X axis.
	 * 
	 * @return the rotation in units of pi
	 */

	public double getRotateY() {
		return rotateY;
	}

	/**
	 * 
	 * Sets the Euler Y-Rotation.
	 * 
	 * @param rotateY
	 *            the rotation
	 */

	public void setRotateY(double rotateY) {
		this.rotateY = rotateY;
	}

	/**
	 * 
	 * Gets the current position of the player; the player is represented by a
	 * point, not a rectangular area.
	 * 
	 * @return the position of the player
	 */

	public Point3d getCurPos() {
		return curPos;
	}

	/**
	 * 
	 * Sets the current position of the player; necessary when updating the
	 * position.
	 * 
	 * @param curPos
	 *            the new position of the player
	 */

	public void setCurPos(Point3d curPos) {
		this.curPos = curPos;
	}

	/**
	 * 
	 * Sets the current material (primary) used to add blocks to the scene.
	 * 
	 * @param a
	 *            the new material
	 */

	public void setMaterial(APMaterial a) {
		currentMat = a;
	}

	/**
	 * 
	 * Gets the current material used.
	 * 
	 * @return the material
	 */

	public APMaterial getMaterial() {
		return currentMat;
	}

	/**
	 * 
	 * Sets the process ID. Necessary in distinguishing between multiple
	 * processes.
	 * 
	 * @param i
	 *            the ID
	 */

	public void setProcessID(byte i) {
		processID = i;
	}

	/**
	 * 
	 * Gets the process ID.
	 * 
	 * @return the process ID
	 */

	public byte getProcessID() {
		return processID;
	}

	/**
	 * 
	 * Main constructor. Creates a new process, hence a new tab. Also links to a
	 * specific file path; it is "Untitled" if it is new, and specified after
	 * saving.
	 * 
	 * Sets up and fills the multiple arrays associated with the process.
	 * 
	 * @param worldPath
	 *            the path of the file, or "Untitled[0..n]"
	 */

	public APProcess(String worldPath) {
		save = new APWorld(worldPath);
		// FLRBaBoT

		Arrays.fill(windcolors, (byte) 0);
		Arrays.fill(windcoords, -1);
		Arrays.fill(texturecoords, 0);

		for (int j = 0; j < APFinalData.LIMIT; j++) {

			coordsort.put(j, null);
			reversecoordsort.put(null, j);

			dVelocity[j] = new APDecimalVelocity();

			realcoords[j * 3] = 0;
			realcoords[j * 3 + 1] = -j - 1;
			realcoords[j * 3 + 2] = 0;

			for (int i = 0; i < 24; i++) {

				coords[(j * 24 * 3) + (i * 3)] = 0;
				coords[(j * 24 * 3) + (i * 3) + 1] = -j - 1;
				coords[(j * 24 * 3) + (i * 3) + 2] = 0;
			}
		}
	}

	/**
	 * 
	 * Default constructor. Not used often.
	 * 
	 */

	public APProcess() {
		this(null);
	}

	/**
	 * 
	 * Sets the file path for saving.
	 * 
	 * @param n
	 *            the file path
	 */

	public void setWorldPath(String n) {
		save = new APWorld(n);
	}

	/**
	 * 
	 * Gets the save file path.
	 * 
	 * @return the file path
	 */

	public String getWorldPath() {
		return save.getPath();
	}

	/**
	 * 
	 * Gets the current brush radius.
	 * 
	 * @return the brush radius.
	 */

	public int getBrushSize() {
		return brushSize;
	}

	/**
	 * 
	 * Sets the brush radius. Used when changing the brush radius (E, C).
	 * 
	 * @param brushSize
	 *            the new brush radius
	 */

	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}

	/**
	 * 
	 * Gets if the game is frozen. Useful in taking screenshots.
	 * 
	 * NOTE: freezing cannot be used in multiplayer games.
	 * 
	 * @return the flag
	 */

	public boolean isFrozen() {
		return isFrozen;
	}

	/**
	 * 
	 * Sets if the game is frozen. Frozen means that the blocks are frozen, it
	 * does not mean the person can move around.
	 * 
	 * NOTE: freezing cannot be used in multiplayer games.
	 * 
	 * @param isFrozen
	 *            the frozen flag
	 */

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	/**
	 * 
	 * Gets the {@link APVideoRecorder} associated with the process.
	 * 
	 * @return the recorder
	 */

	public APVideoRecorder getRecord() {
		return record;
	}

	/**
	 * 
	 * Sets the {@link APVideoRecorder} for this process.
	 * 
	 * @param record
	 *            the new recorder
	 */

	public void setRecord(APVideoRecorder record) {
		this.record = record;
	}

	/**
	 * 
	 * Gets if the selection box is hidden. Useful in taking screenshots or
	 * recording video.
	 * 
	 * @return if the box is hidden
	 */

	public boolean isBoxHidden() {
		return isBoxHidden;
	}

	/**
	 * 
	 * Sets if the box is hidden.
	 * 
	 * @param isBoxHidden
	 *            the flag
	 */

	public void setBoxHidden(boolean isBoxHidden) {
		this.isBoxHidden = isBoxHidden;
	}
}
