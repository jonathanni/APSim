package com.prodp.apsim;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 6-30-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Represents the client-side part of a multiplayer game.
 * 
 * This class contains the start and end routines for the client side of an
 * APSim multiplayer. Starting requires prompting for the user's ip, which can
 * be "localhost" or of the form [xx]x.[xx]x.[xx]x.[xx]x. Updating just reads
 * the coordinate and status arrays, and ending tells the server to exit this
 * client, and it closes the streams.
 * 
 */

public class APClient implements APConnectionUsable {

	private APClientProcess process;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket client;

	@Override
	public void update() throws Exception {

		if (in.readByte() == APFinalData.REQ_CLIENT_UPDATE)
			process.status = (short[]) in.readObject();

		out.writeByte(APFinalData.UPDATE_POSITION);
	}

	@Override
	public void init() throws Exception {
		String IP = JOptionPane.showInputDialog("Please input the server IP: ");

		if (IP == null)
			return;

		if (!(Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]").matcher(IP)
				.matches() || IP.trim().contentEquals("localhost")))
			throw new Exception("IP format incorrect");
		// continue
		client = new Socket(IP, 24356);

		APProcessHandler.APList.addProcess(new APClientProcess(IP, client
				.getInputStream(), client.getOutputStream(), "Client"));
		process = (APClientProcess) APProcessHandler.APList.getCurrentProcess();
		in = process.getInputStream();
		out = process.getOutputStream();

		if (in.readByte() == APFinalData.REQ_CLIENT_LOAD) {
			process.realcoords = (int[]) in.readObject();
			process.coords = (float[]) in.readObject();
			process.colors = (byte[]) in.readObject();
			process.status = (short[]) in.readObject();
			process.velocity = (APVelocity[]) in.readObject();
		}

		out.writeByte(APFinalData.ENTER);
	}

	@Override
	public void exit() throws Exception {

		out.writeByte(APFinalData.EXIT);

		in.close();
		out.close();
		client.close();

	}

}
