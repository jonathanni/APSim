package com.prodp.apsim;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-1-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * This class is used with {@link JFileChooser}. It filters the APSim save
 * formats, APSim format 1.0 (uncompressed) as .aps and APSim format 1.1 (GZIP)
 * as .apsg.
 * 
 */

public class APFileFilter extends FileFilter {
	private final String[] names = new String[] { "aps" };

	@Override
	public boolean accept(File file) {
		for (String extension : names)
			if (file.getName().toLowerCase().endsWith(extension)
					|| file.isDirectory())
				return true;

		return false;
	}

	/**
	 * 
	 * Gets the long description of each file type.
	 * 
	 * @param f
	 *            the file to match the extension with
	 * @return the long description of the file extension
	 */

	public String getTypeDescription(File f) {
		if (f.getName().endsWith(".aps"))
			return "APSim Save Files Format 1.2";

		return null;
	}

	/**
	 * 
	 * Gets the icon of the file (default is paper icon, replaces with APSim
	 * logo).
	 * 
	 * @param f
	 *            the file to be filtered
	 * @return the icon associated with the file extension
	 */

	public Icon getIcon(File f) {
		return new ImageIcon(APFinalData.apIconImage);
	}

	@Override
	public String getDescription() {
		return "APSim Save Files (*.aps)";
	}
}
