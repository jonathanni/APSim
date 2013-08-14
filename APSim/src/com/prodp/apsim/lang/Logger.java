package com.prodp.apsim.lang;

import com.prodp.apsim.APMain;

public class Logger {
	public static final int INFO = 0, WARN = 1, ERRR = 2;

	public void log(int type, String msg) {
		switch (type) {
		case INFO:

			APMain.debug("[I] " + msg);

			break;
		case WARN:

			APMain.error("[W] " + msg);

			break;
		case ERRR:

			APMain.error("[E] " + msg);

			break;
		default:
			return;
		}
	}
}
