package com.prodp.apsim;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * This class is supposed to hash the whole runtime jar to see if it is
 * different than the website's MD5 hash. It checks to see if there needs to be
 * an update to the latest version.
 * 
 */

public class APHasher {

	/**
	 * 
	 * Converts a byte array to a hex string.
	 * 
	 * @param bytes the bytes used
	 * @return a hex string
	 */
	
	private static String toHex(byte[] bytes) {
		StringBuffer hexString = new StringBuffer();

		for (byte i : bytes)
			hexString.append(Integer.toString(0xFF & i, 16));

		return hexString.toString();
	}
	
	/**
	 * 
	 * Hashes the runtime jar and returns the hex hash.
	 * 
	 * @return the hex string hash
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */

	public static final String hash() throws IOException,
			NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		File in = new File("RunAP.jar");
		FileInputStream is = new FileInputStream(in);
		byte[] buf = new byte[1024];
		int numRead = 0;

		do {
			numRead = is.read(buf);

			if (numRead > 0)
				md5.update(buf, 0, numRead);

		} while (numRead != -1);

		is.close();
		return toHex(md5.digest(buf));
	}
}
