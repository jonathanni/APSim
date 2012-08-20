package com.prodp.apsim;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * The server of APSim; includes all necessary processes for updating the
 * server.
 * 
 * It also includes the functions for updating the log and the server screen.
 * 
 */

public class APServer implements APConnectionUsable {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ServerSocket server;
	private final static Style style = APFinalData.serverDocument.addStyle(
			"Font", null);

	@Override
	public void update() throws Exception {
		log("Waiting for client...");
		log("");
		new Thread(new APServerThread(server.accept())).start();
	}
	
	/**
	 * 
	 * Logs a string plus a newline.
	 * 
	 * @param s the message
	 */

	public static void log(String s) {
		try {
			APFinalData.serverDocument.insertString(
					APFinalData.serverDocument.getLength(), s + "\n", style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws Exception {
		server = new ServerSocket(24356);

		APProcessHandler.APList.addProcess(new APServerProcess("Server"));

		APFinalData.mainFrame.setVisible(false);
		APFinalData.serverFrame.setVisible(true);
		APFinalData.serverFrame.setResizable(false);
		APFinalData.serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		StyleConstants.setFontFamily(style, "Courier New");
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_JUSTIFIED);

		log("APSim Server " + APFinalData.getVersion());
		log("****************************************************************************"); // 75
		APProcessHandler.APList.getCurrentProcess().isPaused = false;

		new Thread(new APServerThreadWatcher(this)).start();
	}

	@Override
	public void exit() throws Exception {

		APFinalData.serverFrame.setVisible(false);
		APFinalData.mainFrame.setVisible(true);

		in.close();
		out.close();
		server.close();

	}

}
