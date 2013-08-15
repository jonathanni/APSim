package com.prodp.apsim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.vecmath.Color4b;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Writes the coordinate data in the APSim cubes to a .OBJ/.MTL file for
 * external rendering.
 * 
 */

public class APOBJWriter {
	private FileWriter w, m;
	private File mf;
	private APProcess p;

	/**
	 * 
	 * Main constructor. Creates a writer based on a file and the process
	 * containing the APSim data.
	 * 
	 * @param f
	 *            the file
	 * @param process
	 *            the {@link APProcess} containing the data.
	 */

	public APOBJWriter(File f, APProcess process) {
		try {

			this.p = process;
			this.mf = new File(StringUtils
					.stripEnd(f.getAbsolutePath(), ".obj").concat(".mtl"));

			w = new FileWriter(f);
			m = new FileWriter(mf);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Writes coordinate and color info to the file.
	 * 
	 * @see <a
	 *      href="http://www.fileformat.info/format/wavefrontobj/egff.htm">Wavefront
	 *      OBJ file format</a>
	 * 
	 */

	private HashMap<Color4b, ArrayList<Integer>> colorMap = new HashMap<Color4b, ArrayList<Integer>>();
	private HashMap<Color4b, Float[]> textureMap = new HashMap<Color4b, Float[]>();

	public void write() {
		
		try {
			ImageIO.write(ImageIO.read(APMain.class
					.getResource("texture/textures.png")), "png",
					new File(mf.getParent() + File.separator + "tex.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		getUniqueMap();
		int i = 0, faceoffset = 0;

		try {
			w.write("mtllib " + mf.getName() + "\n");

			for (Entry<Color4b, ArrayList<Integer>> e : colorMap.entrySet()) {
				w.write("o block_" + i + "\n");
				w.write("g blocks_" + i + "\n");
				w.write("usemtl blocks_" + i + "\n");

				for (int j : e.getValue()) {
					for (int k = 0; k < 24; k++)
						w.write("v "
								+ Transform3DUtils.roundFloat(
										p.coords[(j * 24 * 3) + (k * 3)],
										0.0001f)
								+ " "
								+ Transform3DUtils.roundFloat(
										p.coords[(j * 24 * 3) + (k * 3) + 1],
										0.0001f)
								+ " "
								+ Transform3DUtils.roundFloat(
										p.coords[(j * 24 * 3) + (k * 3) + 2],
										0.0001f) + "\n");
				}

				for (int j = 0; j < e.getValue().size(); j++) {
					for (int k = 0; k < 6; k++)
						// Start from 1
						w.write("f " + (j * 6 * 4 + k * 4 + 1 + faceoffset)
								+ " " + (j * 6 * 4 + k * 4 + 2 + faceoffset)
								+ " " + (j * 6 * 4 + k * 4 + 3 + faceoffset)
								+ " " + (j * 6 * 4 + k * 4 + 4 + faceoffset)
								+ "\n");
				}

				faceoffset += e.getValue().size() * 24;

				i++;
			}

			i = 0;

			for (Entry<Color4b, ArrayList<Integer>> e : colorMap.entrySet()) {
				Color4b color = e.getKey();
				double r = ((int) color.x & 0xff) / 255.0, g = ((int) color.y & 0xff) / 255.0, b = ((int) color.z & 0xff) / 255.0, t = ((int) color.w & 0xff) / 255.0;

				m.write("newmtl blocks_" + i + "\n");
				m.write("Ka " + r / 2 + " " + g / 2 + " " + b / 2 + "\n");
				m.write("Kd " + r + " " + g + " " + b + "\n");
				m.write("Ks 1.0000 1.0000 1.0000 1.0000\n");
				m.write("Tr " + t + "\n");

				Float[] texc = textureMap.get(color);

				m.write("map_Ka -o " + texc[0] + " " + texc[1] + " -s "
						+ (texc[4] - texc[0]) + " " + (texc[5] - texc[1])
						+ " tex.png\n");
				m.write("map_Kd -o " + texc[0] + " " + texc[1] + " -s "
						+ (texc[4] - texc[0]) + " " + (texc[5] - texc[1])
						+ " tex.png\n");

				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Get the colors of the blocks; rather than having more entries of colors
	 * to indices, the color is mapped to an ArrayList of integers.
	 * 
	 * @return the {@link Color4b} to {@link ArrayList} {@link HashMap}
	 */

	private void getUniqueMap() {

		for (int i = 0; i < APFinalData.LIMIT; i++) {
			Color4b color = new Color4b(p.colors[i * 24 * 4],
					p.colors[i * 24 * 4 + 1], p.colors[i * 24 * 4 + 2],
					p.colors[i * 24 * 4 + 3]);

			if (p.status[i] != 0) {
				if (!colorMap.containsKey(color)) {
					colorMap.put(color, new ArrayList<Integer>());

					float[] tex = new float[24 * 2];

					System.arraycopy(p.texturecoords, i * 24 * 2, tex, 0,
							24 * 2);

					textureMap.put(color, ArrayUtils.toObject(tex));
				}
				colorMap.get(color).add(i);
			}
		}
	}

	/**
	 * 
	 * Flushes and closes the streams (due to the buffered nature of the
	 * streams).
	 * 
	 */

	public void close() {
		try {
			w.flush();
			w.close();
			m.flush();
			m.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
