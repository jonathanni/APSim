package com.prodp.apsim;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * The sky sphere of the world, being a starfield imposed onto a sphere.
 * 
 */

public class APSky extends Sphere {

	/**
	 * 
	 * Constructor; creates a new sky sphere based on a {@link APRandImage}.
	 * 
	 */

	public APSky() {
		super(1, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS_Y_UP
				| Sphere.GENERATE_TEXTURE_COORDS
				| Sphere.GENERATE_NORMALS_INWARD, new Appearance());

		setTexture(new TextureLoader(new APRandImage(200, 200,
				BufferedImage.TYPE_3BYTE_BGR, Color.BLACK, Color.WHITE))
				.getTexture());
	}

	private void setTexture(Texture tex) {

		Transform3D scale = new Transform3D();
		scale.setScale(30);

		Appearance app = new Appearance();

		TextureAttributes ta = new TextureAttributes();
		ta.setTextureMode(TextureAttributes.MODULATE);
		ta.setPerspectiveCorrectionMode(TextureAttributes.NICEST);
		ta.setTextureTransform(scale);

		PolygonAttributes patt = new PolygonAttributes();
		patt.setCullFace(PolygonAttributes.CULL_NONE);

		app.setPolygonAttributes(patt);
		app.setTextureAttributes(ta);

		tex.setEnable(true);

		app.setTexture(tex);

		getShape().setAppearance(app);
	}
}
