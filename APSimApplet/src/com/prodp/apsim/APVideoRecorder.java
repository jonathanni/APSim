package com.prodp.apsim;

import java.io.File;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * 3D object/model video recorder, takes a snapshot of the scene, transforms it
 * into obj format, and saves it every {@value #MAX_TARDINESS} ms.
 * 
 */

public class APVideoRecorder extends TimerTask {

	private Timer starter = new Timer();
	private volatile boolean isRunning = false;

	private volatile int counter = 0;

	private File file;

	private static final int MAX_TARDINESS = 30;

	private static final int TARGET = 34;

	/**
	 * 
	 * Constructor; creates a video recorder from a destination file.
	 * 
	 * @param f the file
	 */
	
	public APVideoRecorder(File f) {
		setFile(f);
	}
	
	/**
	 * 
	 * Starts the task and starts recording.
	 * 
	 */

	public void start() {
		resume();
		starter.schedule(this, 0, TARGET);
	}
	
	/**
	 * 
	 * Stops the task and stops recording.
	 * 
	 */

	public void stop() {
		pause();
		starter.cancel();

		counter = 0;
		setFile(null);
	}
	
	/**
	 * 
	 * Pauses recording.
	 * 
	 */

	public void pause() {
		isRunning = false;
	}
	
	/**
	 * 
	 * Resumes recording.
	 * 
	 */

	public void resume() {
		isRunning = true;
	}

	@Override
	public void run() {

		if (System.currentTimeMillis() - scheduledExecutionTime() >= MAX_TARDINESS)
			return;

		if (isRunning) {
			APProcess process = APProcessHandler.APList.getCurrentProcess();
			APOBJWriter wout = new APOBJWriter(new File(getFile()
					.getAbsolutePath()
					+ "_"
					+ String.format(String.format("%%0%dd", 4), counter)
					+ ".obj"), process);

			wout.write();
			wout.close();

			counter++;
		}
	}
	
	/**
	 * 
	 * Gets the file associated with the video recorder.
	 * 
	 * @return the file
	 */

	public File getFile() {
		return file;
	}

	/**
	 * 
	 * Sets the file for the video recorder.
	 * 
	 * @param file the file
	 */
	
	public void setFile(File file) {
		this.file = file;
	}

}
