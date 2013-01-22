/*
 * 
 * 
 * 
 * 
 * 
 * Hello everyone, I will be showing about 20 minutes of programming time,
 * a speed-coding video.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

package com.prodp.apsim;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Screen3D;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Main starting point for APSim. First calls APProcessHandler to initiate GUI
 * building and game preparation, then launches a thread to run the game, while
 * controlling specific key operations and sleeping operations. Also contains
 * many utilities such as screenshotting.
 * 
 */

public final class APMain extends APObject implements Runnable {

	static {

		// Detect Mac OS X operating system
		if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {

			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name",
					"APSimulator");

			System.setProperty("com.apple.mrj.application.growbox.intrudes",
					"false");

			System.setProperty("com.apple.macos.useScreenMenuBar", "true");
			System.setProperty("com.apple.macos.smallTabs", "true");

			System.setProperty("apple.laf.smallTabs", "true");
			System.setProperty("apple.laf.useScreenMenuBar", "true");

			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Main starting point for APSim. Exceptions are piled onto this point.
	 * 
	 * @param args
	 * @throws AWTException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 * @throws CloneNotSupportedException
	 */

	public static void main(final String[] args) throws AWTException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			CloneNotSupportedException {

		new JFileChooser(); // Make sure the JFileChooser doesn't throw an
							// exception

		new APProcessHandler().init();

	}

	/**
	 * 
	 * Sleeps for a specific duration.
	 * 
	 * @param nanoDuration
	 *            the duration in nanoseconds
	 * @throws InterruptedException
	 */

	public static void sleepNanos(final long nanoDuration)
			throws InterruptedException {
		final long end = System.nanoTime() + nanoDuration;

		long timeLeft = nanoDuration;
		do {
			if (timeLeft > APFinalData.SLEEP_PRECISION)
				Thread.sleep(1);
			else

				Thread.yield();
			timeLeft = end - System.nanoTime();

			if (Thread.interrupted())
				throw new InterruptedException();

		} while (timeLeft > 0);

	}

	static long startNano, timeElapsed, fps;

	/**
	 * 
	 * Debug counter used in many debug purposes.
	 * 
	 */

	public static long dCounter = 0;

	private void doMovement() {
		if (APProcessHandler.keys[0])
			APProcessHandler.moveRelativeLeft(APFinalData.SPEED);
		if (APProcessHandler.keys[1])
			APProcessHandler.moveRelativeRight(APFinalData.SPEED);
		if (APProcessHandler.keys[2])
			APProcessHandler.moveRelativeFwd(APFinalData.SPEED);
		if (APProcessHandler.keys[3])
			APProcessHandler.moveRelativeBack(APFinalData.SPEED);
		if (APProcessHandler.keys[5])
			APProcessHandler.moveRelativeUp(APFinalData.SPEED);
		if (APProcessHandler.keys[6])
			APProcessHandler.moveRelativeDown(APFinalData.SPEED);
	}

	private void checkScreenshot() {
		if (APProcessHandler.keys[7] && !APProcessHandler.prevaction[1]) {
			if (!new File("_screenshots").exists())
				new File("_screenshots").mkdir();
			try {
				ImageIO.write(
						takeScreenshot(APProcessHandler.getCanvas(), 2),
						"png",
						new File("_screenshots/"
								+ (System.currentTimeMillis() / 1000L) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			APProcessHandler.prevaction[1] = true;
		}
	}

	private void doBrushChange() {
		if (APProcessHandler.keys[9] && !APProcessHandler.prevaction[3]) {
			APProcessHandler.incBrushSize();
			APProcessHandler.prevaction[3] = true;
		} else if (APProcessHandler.keys[10] && !APProcessHandler.prevaction[4]) {
			APProcessHandler.decBrushSize();
			APProcessHandler.prevaction[4] = true;
		}
	}

	private void doExport() {
		if (APProcessHandler.keys[8] && !APProcessHandler.prevaction[2]) {
			try {
				APProcessHandler.exportModel();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			APProcessHandler.prevaction[2] = true;
		}
	}

	private void doExportSequence(APProcess process) {
		if (APProcessHandler.keys[12] && !APProcessHandler.prevaction[6]) {
			if (!process.isRecording)
				APProcessHandler.startRecording();
			else
				APProcessHandler.stopRecording();

			APProcessHandler.prevaction[6] = true;
		}
	}

	private void doFreeze() {
		if (APProcessHandler.keys[11] && !APProcessHandler.prevaction[5]) {
			APProcessHandler.toggleFrozen();
			APProcessHandler.prevaction[5] = true;
		}
	}

	private void doBoxHide() {
		if (APProcessHandler.keys[13] && !APProcessHandler.prevaction[7]) {
			APProcessHandler.toggleBoxHide();
			APProcessHandler.prevaction[7] = true;
		}
	}

	private void doEscape(APProcess process) {
		if (APProcessHandler.keys[4] && !APProcessHandler.prevaction[0]) {
			process.isPaused = !APProcessHandler.APList.getCurrentProcess().isPaused;
			APProcessHandler.prevaction[0] = true;
		}
	}

	/**
	 * 
	 * Detect keypresses and respond to them.
	 * 
	 * @param process
	 */

	public void doKeyPresses(APProcess process) {
		// MOVE
		if (!process.busy)
			doMovement();

		// BRUSH SIZE
		doBrushChange();

		// CHECK TAKE SCREENSHOT
		checkScreenshot();

		// EXPORT TO .OBJ, .MTL
		doExport();

		// EXPORT .OBJ SEQUENCE
		doExportSequence(process);

		// FREEZE MODE
		doFreeze();

		// BOX HIDE
		doBoxHide();

		// ESC
		doEscape(process);
	}

	@Override
	public void run() {

		new Timer().schedule(new APFPSUpdater(), 0, 500);

		while (APProcessHandler.getIsRunning()) {

			APProcess process = APProcessHandler.APList.getCurrentProcess();

			startNano = System.nanoTime();

			if (!process.isPaused && !APProcessHandler.APList.isDead())

				if (!(process instanceof APServerProcess)) {

					APProcessHandler.performEnvironmentFunctions();
					APProcessHandler.updateArray();
					APProcessHandler.updatePosition();

				} else
					try {
						APProcessHandler.getServer().update();
					} catch (Exception e) {
						e.printStackTrace();
					}
			else
				APProcessHandler.drawPause((int) (APProcessHandler.getCanvas()
						.getWidth() / 2.3), APProcessHandler.getCanvas()
						.getHeight() / 2);

			process.isRobot = true;

			if (!process.isPaused)
				APProcessHandler.center.mouseMove(APFinalData.mainFrame.getX()
						+ APProcessHandler.getCanvas().getWidth() / 2,
						APFinalData.mainFrame.getY()
								+ APProcessHandler.getCanvas().getHeight() / 2
								+ APFinalData.processSwitch.getHeight()
								+ APFinalData.top.getHeight());

			process.isRobot = false;

			// Detect keys
			doKeyPresses(process);

			// IMPORTANT: This is Sensor
			if (APProcessHandler.isLeftMouseDown)
				APProcessHandler.countUp();

			if (!APProcessHandler.getCanvas().hasFocus())
				APProcessHandler.getCanvas().requestFocusInWindow();

			timeElapsed = System.nanoTime() - startNano;
			fps = APFinalData.NANOS_IN_SECOND / timeElapsed;

			try {
				APMain.sleepNanos(APFinalData.SLEEPTIME * 1000000000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			// do all your debugging here

			Toolkit.getDefaultToolkit().sync();
		}

		APProcessHandler.destroy();
	}

	// Taken from http://www.java.net/node/647363

	/**
	 * 
	 * Takes a screenshot and returns the image.
	 * 
	 * @param canvas
	 *            the Canvas3D associated with the game
	 * @param scale
	 *            the integral scale of the image
	 * @return the screenshot
	 */

	public static BufferedImage takeScreenshot(Canvas3D canvas, int scale) {

		Screen3D on = canvas.getScreen3D();

		Canvas3D shot = new Canvas3D(canvas.getGraphicsConfiguration(), true);
		canvas.getView().stopView();
		canvas.getView().addCanvas3D(shot);
		canvas.getView().startView();

		Screen3D off = shot.getScreen3D();
		off.setSize(on.getSize());
		off.setPhysicalScreenHeight(on.getPhysicalScreenHeight());
		off.setPhysicalScreenWidth(on.getPhysicalScreenWidth());
		shot.setOffScreenLocation(canvas.getLocationOnScreen());

		BufferedImage bi = new BufferedImage(canvas.getWidth() * scale,
				canvas.getHeight() * scale, BufferedImage.TYPE_INT_ARGB);
		ImageComponent2D buffer = new ImageComponent2D(
				ImageComponent.FORMAT_RGB, bi);
		shot.setOffScreenBuffer(buffer);
		shot.renderOffScreenBuffer();
		shot.waitForOffScreenRendering();
		BufferedImage res = shot.getOffScreenBuffer().getImage();
		canvas.getView().removeCanvas3D(shot);

		return res;

	}
}
