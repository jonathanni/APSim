package com.prodp.apsim;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * This class is used with {@link JFileChooser}. It filters out the file types
 * associated with Wavefront OBJ.
 * 
 * @see <a href="http://wikipedia.org/wiki/Wavefront_.obj_file">OBJ information</a>
 * 
 */

public class APOBJFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(".obj");
	}

	@Override
	public String getDescription() {
		return "Wavefront OBJ (*.mtl, *.obj)";
	}

}
