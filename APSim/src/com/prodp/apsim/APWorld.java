package com.prodp.apsim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.vecmath.Point3d;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * The APSim world containing functions for writing the APSim world data.
 * 
 */

public class APWorld extends APProcessableItem {

	// Loader components
	private JPanel panel = new JPanel();
	private JFrame loader = new JFrame();
	private JProgressBar bar = new JProgressBar(SwingConstants.HORIZONTAL, 0,
			100);
	private JLabel barLabel = new JLabel("Progress: ");

	/**
	 * 
	 * Creates a new world based on the path.
	 * 
	 * @param p the path
	 */
	
	public APWorld(String p) {
		init();
		setPath(p);
	}
	
	/**
	 * 
	 * Creates a new untitled world.
	 * 
	 */

	public APWorld() {
		init();
	}

	private void init() {

		bar.setDoubleBuffered(true);
		bar.setSize(200, 50);

		panel.add(bar);
		panel.add(barLabel);

		loader.add(panel);
		loader.setTitle("Performing Operations...");
		loader.setIconImage(APFinalData.apIconImage);
		loader.setResizable(false);
		loader.pack();

	}

	@Override
	public void write() throws IOException {

		final APProcess process = APProcessHandler.APList.getCurrentProcess();

		try {
			out = new ObjectOutputStream(new GZIPOutputStream(
					new FileOutputStream(getPath())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		out.writeInt(APFinalData.FORMAT_VERSION);

		out.writeInt(APProcessHandler.getBrushSize());

		out.writeDouble(APProcessHandler.getCurrentPosition().x);
		out.writeDouble(APProcessHandler.getCurrentPosition().y);
		out.writeDouble(APProcessHandler.getCurrentPosition().z);

		out.writeDouble(process.getRotateX());
		out.writeDouble(process.getRotateY());

		out.writeShort(APProcessHandler.APList.getCurrentProcess()
				.getMaterial().getID());
		out.writeShort(APFinalData.LIMIT);

		out.writeBoolean(APProcessHandler.getCanvas().getView()
				.getSceneAntialiasingEnable());
		out.writeShort(APProcessHandler.getSensitivity());

		loader.setVisible(true);

		out.writeObject(process.coords);
		out.writeObject(process.colors);
		out.writeObject(process.status);
		out.writeObject(process.realcoords);

		for (int i = 0; i < APFinalData.LIMIT; i++) {

			out.writeFloat(process.dVelocity[i].x);
			out.writeFloat(process.dVelocity[i].y);
			out.writeFloat(process.dVelocity[i].z);

			bar.setValue((int) (i / APFinalData.LIMIT * 100));
		}

		loader.setVisible(false);

		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read() throws IOException, FileNotFoundException {

		final APProcess process = APProcessHandler.APList.getCurrentProcess();

		in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(
				getPath())));

		APProcessHandler.setBrushSize(in.readInt());

		APProcessHandler.setCurrentPosition(new Point3d(in.readDouble(), in
				.readDouble(), in.readDouble()));

		process.setRotateX(in.readDouble());
		process.setRotateY(in.readDouble());

		APProcessHandler.APList.getCurrentProcess().setMaterial(
				APMaterialsList.getMaterialByID(in.readShort()));
		final short LOCALLIMIT = in.readShort();

		APProcessHandler.getCanvas().getView()
				.setSceneAntialiasingEnable(in.readBoolean());
		APProcessHandler.setSensitivity(in.readShort());

		loader.setVisible(true);

		try {
			process.coords = (float[]) in.readObject();
			process.colors = (byte[]) in.readObject();
			process.status = (short[]) in.readObject();
			process.realcoords = (int[]) in.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < LOCALLIMIT; i++) {

			process.dVelocity[i].x = in.readFloat();
			process.dVelocity[i].y = in.readFloat();
			process.dVelocity[i].z = in.readFloat();

			bar.setValue((int) (i / APFinalData.LIMIT * 100));
		}

		loader.setVisible(false);

		in.close();
	}

}
