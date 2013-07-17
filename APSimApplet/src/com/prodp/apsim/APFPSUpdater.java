package com.prodp.apsim;

import java.util.TimerTask;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Automatically updates the FPS counter in the top right hand corner of the
 * GUI.
 * 
 */

public class APFPSUpdater extends TimerTask {

	@Override
	public void run() {

		StringBuilder builder = new StringBuilder();

		builder.append(APFinalData.getVersion()).append(" ")
				.append(String.format(String.format("%%0%dd", 5), APMain.fps))
				.append(" fps, ")
				.append(APFinalData.LIMIT - APProcessHandler.getBlockNumber())
				.append(" blocks left");

		APFinalData.fps.setText(builder.toString());

	}

}
