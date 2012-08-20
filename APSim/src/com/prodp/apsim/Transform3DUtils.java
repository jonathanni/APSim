package com.prodp.apsim;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4d;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector4f;

/**
 * 
 * @author Productive Productions <apsimulator@yahoo.com>
 * @version 1.6
 * @since 11-10-2011
 * 
 */

public class Transform3DUtils {

	/**
	 * Returns a {@link Vector3f} that was converted from a {@link Vector3d}.
	 * 
	 * There might be small double conversion inaccuracies when converting the
	 * Vector3d.
	 * 
	 * @param v
	 *            a Vector3d that needs to be converted.
	 * @return A converted Vector3f.
	 */

	public static final Vector3f castVector3dToVector3f(Vector3d v) {
		return new Vector3f((float) v.x, (float) v.y, (float) v.z);
	}

	/**
	 * Returns a {@link Vector3d} that was converted from a {@link Vector3f}.
	 * 
	 * @param v
	 *            a Vector3d that needs to be converted.
	 * @return A converted Vector3d.
	 */

	public static final Vector3d castVector3fToVector3d(Vector3f v) {
		return new Vector3d(v.x, v.y, v.z);
	}

	/**
	 * Returns a {@link Vector3f} that was converted from a {@link Point3f}.
	 * 
	 * @param a
	 *            a Point3f that needs to be converted.
	 * @return A converted Vector3f.
	 */

	public static final Vector3f convertPoint3ftoVector3f(Point3f a) {
		return new Vector3f(a.x, a.y, a.z);
	}

	/**
	 * Returns a {@link Vector3d} that was converted from a {@link Point3d}.
	 * 
	 * @param a
	 *            a Point3d that needs to be converted.
	 * @return A converted Vector3d.
	 */

	public static final Vector3d convertPoint3dToVector3d(Point3d a) {
		return new Vector3d(a.x, a.y, a.z);
	}

	/**
	 * Returns a {@link Point3f} that was converted from a {@link Vector3f}.
	 * 
	 * To convert a Vector3d to a Point3f, use
	 * {@link #castVector3dToVector3f(Vector3d)} then use this function.
	 * 
	 * @param a
	 *            a Vector3f that needs to be converted.
	 * @return A converted Point3f.
	 */

	public static final Point3f convertVector3fToPoint3f(Vector3f a) {
		return new Point3f(a.x, a.y, a.z);
	}

	/**
	 * Returns a {@link Point3d} that was converted from a {@link Vector3d}.
	 * 
	 * To convert a Vector3f to a Point3d, use
	 * {@link #castVector3fToVector3d(Vector3f)} then use this function.
	 * 
	 * @param a
	 *            a Vector3f that needs to be converted.
	 * @return A converted Point3f.
	 */

	public static final Point3d convertVector3dToPoint3d(Vector3d a) {
		return new Point3d(a.x, a.y, a.z);
	}

	/**
	 * 
	 * Returns a {@link Vector3f} processed through Euler rotations avoiding
	 * Gimbal Lock.
	 * 
	 * Do NOT get rotation on the Y axis and rotation on the X axis mixed up.
	 * Rotation on the X axis means the Y value goes up and down from the Origin
	 * (0,0,0) Rotation on the Y axis means the new X value traveling along the
	 * X axis goes left and right from the Origin (0,0,0)
	 * 
	 * @param x
	 *            a float denoting the rotation on the Y axis.
	 * @param y
	 *            a float denoting the rotation on the X axis.
	 * @param r
	 *            a float denoting the radius of the rotation.
	 * @return a Vector3f giving a position of the rotation.
	 */

	public static final Vector3f rotateEulerVector3f(float x, float y, float r) {

		final float rad = (float) Math.PI / 180;

		final float XS = (float) Math.sin(x * rad);
		final float XC = (float) Math.cos(x * rad);
		final float XM = 1 - XC;
		final float YS = (float) Math.sin(y * rad);
		final float YC = (float) Math.cos(y * rad);
		final float YM = (float) (1 - Math.cos(x * rad));

		final Matrix3f X = new Matrix3f(XM + XC, 0, 0, 0, XC, -XS, 0, XS, XC);

		final Matrix3f Y = new Matrix3f(YC, 0, YS, 0, YM + XC, 0, -YS, 0, YC);

		Y.mul(X);

		return Transform3DUtils.mulMatrix3fByVector3f(Y, new Vector3f(0, 0, r));
	}

	/**
	 * 
	 * Returns a {@link Vector3d} processed through Euler rotations avoiding
	 * Gimbal Lock.
	 * 
	 * Do NOT get rotation on the Y axis and rotation on the X axis mixed up.
	 * Rotation on the X axis means the Y value goes up and down from the Origin
	 * (0,0,0) Rotation on the Y axis means the new X value traveling along the
	 * X axis goes left and right from the Origin (0,0,0)
	 * 
	 * @param x
	 *            a double denoting the rotation on the Y axis.
	 * @param y
	 *            a double denoting the rotation on the X axis.
	 * @param r
	 *            a double denoting the radius of the rotation.
	 * @return a Vector3d giving a position of the rotation.
	 */

	public static final Vector3d rotateEulerVector3d(double x, double y,
			double r) {

		final double rad = Math.PI / 180;

		final double XS = Math.sin(x * rad);
		final double XC = Math.cos(x * rad);
		final double XM = 1 - XC;
		final double YS = Math.sin(y * rad);
		final double YC = Math.cos(y * rad);
		final double YM = 1 - Math.cos(x * rad);

		final Matrix3d X = new Matrix3d(XM + XC, 0, 0, 0, XC, -XS, 0, XS, XC);

		final Matrix3d Y = new Matrix3d(YC, 0, YS, 0, YM + XC, 0, -YS, 0, YC);

		Y.mul(X);

		return Transform3DUtils.mulMatrix3dByVector3d(Y, new Vector3d(0, 0, r));
	}

	/**
	 * 
	 * Multiplies a {@link Matrix4d} by a {@link Vector4d} and returns a
	 * Vector4d as the result.
	 * 
	 * This is one of the simple matrix multiplication methods.
	 * 
	 * @param m
	 *            the Matrix4d.
	 * @param v
	 *            the Vector4d.
	 * @return a Vector4d representing the end product of the multiplication.
	 */

	public static final Vector4d mulMatrix4dByVector4d(Matrix4d m, Vector4d v) {

		final double v1 = v.x;
		final double v2 = v.y;
		final double v3 = v.z;
		final double v4 = v.w;

		final Vector4d newv = new Vector4d(v1 * m.m00 + v2 * m.m01 + v3 * m.m02
				+ v4 * m.m03,
				v1 * m.m10 + v2 * m.m11 + v3 * m.m12 + v4 * m.m13, v1 * m.m20
						+ v2 * m.m21 + v3 * m.m22 + v4 * m.m13, v1 * m.m30 + v2
						* m.m31 + v3 * m.m32 + v4 * m.m33);

		return newv;
	}

	/**
	 * 
	 * Multiplies a {@link Matrix4f} by a {@link Vector4f} and returns a
	 * Vector4f as the result.
	 * 
	 * This is one of the simple matrix multiplication methods.
	 * 
	 * @param m
	 *            the Matrix4f.
	 * @param v
	 *            the Vector4f.
	 * @return a Vector4f representing the end product of the multiplication.
	 */

	public static final Vector4f mulMatrix4fByVector4f(Matrix4f m, Vector4f v) {

		final float v1 = v.x;
		final float v2 = v.y;
		final float v3 = v.z;
		final float v4 = v.w;

		final Vector4f newv = new Vector4f(v1 * m.m00 + v2 * m.m01 + v3 * m.m02
				+ v4 * m.m03,
				v1 * m.m10 + v2 * m.m11 + v3 * m.m12 + v4 * m.m13, v1 * m.m20
						+ v2 * m.m21 + v3 * m.m22 + v4 * m.m23, v1 * m.m30 + v2
						* m.m31 + v3 * m.m32 + v4 * m.m33);

		return newv;
	}

	/**
	 * 
	 * Multiplies a {@link Matrix3d} by a {@link Vector3d} and returns a
	 * Vector4d as the result.
	 * 
	 * This is one of the simple matrix multiplication methods.
	 * 
	 * @param m
	 *            the Matrix4d.
	 * @param v
	 *            the Vector4d.
	 * @return a Vector4d representing the end product of the multiplication.
	 */

	public static final Vector3d mulMatrix3dByVector3d(Matrix3d m, Vector3d v) {

		final double v1 = v.x;
		final double v2 = v.y;
		final double v3 = v.z;

		final Vector3d newv = new Vector3d(
				v1 * m.m00 + v2 * m.m01 + v3 * m.m02, v1 * m.m10 + v2 * m.m11
						+ v3 * m.m12, v1 * m.m20 + v2 * m.m21 + v3 * m.m22);

		return newv;
	}

	/**
	 * 
	 * Multiplies a {@link Matrix3f} by a {@link Vector3f} and returns a
	 * Vector3f as the result.
	 * 
	 * This is one of the simple matrix multiplication methods.
	 * 
	 * @param m
	 *            the Matrix3f.
	 * @param v
	 *            the Vector3f.
	 * @return a Vector3f representing the end product of the multiplication.
	 */

	public static final Vector3f mulMatrix3fByVector3f(Matrix3f m, Vector3f v) {

		final float v1 = v.x;
		final float v2 = v.y;
		final float v3 = v.z;

		final Vector3f newv = new Vector3f(
				v1 * m.m00 + v2 * m.m01 + v3 * m.m02, v1 * m.m10 + v2 * m.m11
						+ v3 * m.m12, v1 * m.m20 + v2 * m.m21 + v3 * m.m22);

		return newv;
	}

	/**
	 * 
	 * Gives a {@link String} listing all the {@link Transform3D} Transform
	 * Types.
	 * 
	 * @return a String containing the Transform Types.
	 */

	public static final String getAllTransformTypes() {
		final String s = Transform3D.AFFINE + ", " + Transform3D.CONGRUENT
				+ ", " + Transform3D.IDENTITY + ", "
				+ Transform3D.NEGATIVE_DETERMINANT + ", "
				+ Transform3D.ORTHOGONAL + ", " + Transform3D.RIGID + ", "
				+ Transform3D.SCALE + ", " + Transform3D.TRANSLATION + ", "
				+ Transform3D.ZERO;
		return s;
	}

	/**
	 * 
	 * Takes a {@link Transform3D} and returns the Transform Type.
	 * 
	 * @param t
	 *            the Transform3D to be measured.
	 * @return a String containing the Transform Type of t. If the Transform
	 *         Type of t does not match with any of the Transform Types, returns
	 *         "NONE"
	 */

	public static final String print3DType(Transform3D t) {
		String type;

		switch (t.getType()) {
		case Transform3D.AFFINE:
			type = "AFFINE";
			break;
		case Transform3D.CONGRUENT:
			type = "CONGRUENT";
			break;
		case Transform3D.IDENTITY:
			type = "IDENTITY";
			break;
		case Transform3D.NEGATIVE_DETERMINANT:
			type = "NEG_DETERMINANT";
			break;
		case Transform3D.ORTHOGONAL:
			type = "ORTHO";
			break;
		case Transform3D.RIGID:
			type = "RIGID";
			break;
		case Transform3D.SCALE:
			type = "SCALE";
			break;
		case Transform3D.TRANSLATION:
			type = "TRANSLATION";
			break;
		case Transform3D.ZERO:
			type = "ZERO";
			break;
		default:
			type = "NONE";
			break;
		}
		return type;
	}

	/**
	 * 
	 * Gets the X Transform Value from a Transform3D.
	 * 
	 * @param t
	 *            the Transform3D that is used to take the Vector X value.
	 * @return a float that represents the Transform3D X value.
	 */

	public static final float getTransformX(Transform3D t) {

		Vector3f filler = new Vector3f();
		t.get(filler);

		return filler.x;
	}

	/**
	 * 
	 * Gets the Y Transform Value from a Transform3D.
	 * 
	 * @param t
	 *            the Transform3D that is used to take the Vector Y value.
	 * @return a float that represents the Transform3D Y value.
	 */

	public static final float getTransformY(Transform3D t) {

		Vector3f filler = new Vector3f();
		t.get(filler);

		return filler.y;
	}

	/**
	 * 
	 * Gets the Z Transform Value from a Transform3D.
	 * 
	 * @param t
	 *            the Transform3D that is used to take the Vector Z value.
	 * @return a float that represents the Transform3D Z value.
	 */

	public static final float getTransformZ(Transform3D t) {

		Vector3f filler = new Vector3f();
		t.get(filler);

		return filler.z;
	}

	/**
	 * 
	 * Similar to {@link Vector3f}.x.
	 * 
	 * @param f
	 *            a Vector3f that is used to take the Vector X value.
	 * @return a float that represents the Vector X value.
	 */

	public static final float getVector3fX(Vector3f f) {
		return f.x;
	}

	/**
	 * 
	 * Similar to {@link Vector3f}.y.
	 * 
	 * @param f
	 *            a Vector3f that is used to take the Vector Y value.
	 * @return a float that represents the Vector Y value.
	 */

	public static final float getVector3fY(Vector3f f) {
		return f.y;
	}

	/**
	 * 
	 * Similar to {@link Vector3f}.z.
	 * 
	 * @param f
	 *            a Vector3f that is used to take the Vector Z value.
	 * @return a float that represents the Vector Z value.
	 */

	public static final float getVector3fZ(Vector3f f) {
		return f.z;
	}

	/**
	 * 
	 * Similar to {@link Vector3d}.x.
	 * 
	 * @param f
	 *            a Vector3f that is used to take the Vector X value.
	 * @return a float that represents the Vector X value.
	 */

	public static final double getVector3dX(Vector3d f) {
		return f.x;
	}

	/**
	 * 
	 * Similar to {@link Vector3d}.y.
	 * 
	 * @param f
	 *            a Vector3f that is used to take the Vector Y value.
	 * @return a float that represents the Vector Y value.
	 */

	public static final double getVector3dY(Vector3d f) {
		return f.y;
	}

	/**
	 * 
	 * Similar to {@link Vector3d}.z.
	 * 
	 * @param f
	 *            a Vector3f that is used to take the Vector Z value.
	 * @return a float that represents the Vector Z value.
	 */

	public static final double getVector3dZ(Vector3d f) {
		return f.z;
	}

	/**
	 * 
	 * Gets a {@link Vector3f} from a {@link Transform3D}.
	 * 
	 * @param t
	 *            the Transform3D that is used to get the Vector3f.
	 * @return a Vector3f that is derived from t.
	 */

	public static final Vector3f getTransformVector3f(Transform3D t) {

		Vector3f filler = new Vector3f();
		t.get(filler);

		return filler;
	}

	/**
	 * 
	 * Gets a {@link Vector3d} from a {@link Transform3D}.
	 * 
	 * @param t
	 *            the Transform3D that is used to get the Vector3d.
	 * @return a Vector3d that is derived from t.
	 */

	public static final Vector3d getTransformVector3d(Transform3D t) {

		Vector3d filler = new Vector3d();
		t.get(filler);

		return filler;
	}

	/**
	 * 
	 * Adds two {@link Vector3f}s from two {@link Transform3D} and gives the
	 * sum.
	 * 
	 * @param t1
	 *            a Transform3D that is used to get a adding value.
	 * @param t2
	 *            a Transform3D that is used to get a adding value.
	 * @return the sum of the derived Vector3fs.
	 */

	public static final Vector3f addTransformVectors3f(Transform3D t1,
			Transform3D t2) {

		return new Vector3f(getTransformX(t1) + getTransformX(t2),
				getTransformY(t1) + getTransformY(t2), getTransformZ(t1)
						+ getTransformZ(t2));

	}

	/**
	 * 
	 * Adds two {@link Vector3d}s from two {@link Transform3D} and gives the
	 * sum.
	 * 
	 * @param t1
	 *            a Transform3D that is used to get a adding value.
	 * @param t2
	 *            a Transform3D that is used to get a adding value.
	 * @return the sum of the derived Vector3ds.
	 */

	public static final Vector3d addTransformVectors3d(Transform3D t1,
			Transform3D t2) {

		return new Vector3d(getTransformX(t1) + getTransformX(t2),
				getTransformY(t1) + getTransformY(t2), getTransformZ(t1)
						+ getTransformZ(t2));

	}

	/**
	 * 
	 * Adds two {@link Vector3f}s. Useful because it is a return value method.
	 * 
	 * @param t1
	 *            A Vector3f.
	 * @param t2
	 *            A Vector3f.
	 * @return The sum of the two Vector3fs as a Vector3f.
	 */

	public static final Vector3f addVector3f(Vector3f t1, Vector3f t2) {
		return new Vector3f(getVector3fX(t1) + getVector3fX(t2),
				getVector3fY(t1) + getVector3fY(t2), getVector3fZ(t1)
						+ getVector3fZ(t2));
	}

	/**
	 * 
	 * Adds two {@link Tuple3f}s. Useful because it is a return value method.
	 * 
	 * @param t1
	 *            A Tuple3f.
	 * @param t2
	 *            A Tuple3f.
	 * @return The sum of the two Tuple3fs as a Vector3f.
	 */

	public static final Tuple3f addVector3f(Tuple3f t1, Tuple3f t2) {
		return new Vector3f(t1.x + t2.x, t1.y + t2.y, t1.z + t2.z);
	}

	/**
	 * 
	 * Adds two {@link Vector3d}s. Useful because it is a return value method.
	 * 
	 * @param t1
	 *            A Vector3d.
	 * @param t2
	 *            A Vector3d.
	 * @return The sum of the two Vector3ds as a Vector3d.
	 */

	public static final Vector3d addVector3d(Vector3d t1, Vector3d t2) {
		return new Vector3d(getVector3dX(t1) + getVector3dX(t2),
				getVector3dY(t1) + getVector3dY(t2), getVector3dZ(t1)
						+ getVector3dZ(t2));

	}

	/**
	 * 
	 * Adds two {@link Tuple3d}s. Useful because it is a return value method.
	 * 
	 * @param t1
	 *            A Tuple3d.
	 * @param t2
	 *            A Tuple3d.
	 * @return The sum of the two Tuple3fs as a Vector3d.
	 */

	public static final Tuple3d addVector3d(Tuple3f t1, Tuple3f t2) {
		return new Vector3d(t1.x + t2.x, t1.y + t2.y, t1.z + t2.z);
	}

	/**
	 * 
	 * Adds two {@link Tuple3d}s. Useful because it is a return value method.
	 * 
	 * @param t1
	 *            A Tuple3d.
	 * @param t2
	 *            A Tuple3d.
	 * @return The sum of the two Tuple3fs as a Point3d.
	 */

	public static final Point3d addPoint3d(Tuple3d t1, Tuple3d t2) {
		return new Point3d(t1.x + t2.x, t1.y + t2.y, t1.z + t2.z);
	}

	/**
	 * 
	 * Adds two {@link Tuple3f}s. Useful because it is a return value method.
	 * 
	 * @param t1
	 *            A Tuple3d.
	 * @param t2
	 *            A Tuple3d.
	 * @return The sum of the two Tuple3fs as a Point3d.
	 */

	public static final Point3f addPoint3f(Tuple3f t1, Tuple3f t2) {
		return new Point3f(t1.x + t2.x, t1.y + t2.y, t1.z + t2.z);
	}

	/**
	 * 
	 * Converts a {@link Vector3d} containing degree measurements into one that
	 * has radian measurements.
	 * 
	 * @param t
	 *            a Vector3d that needs to be converted.
	 * @return a converted Vector3d containing radian measurements.
	 */

	public static final Vector3d getVectorDegreesDoubleEuler(Vector3d t) {

		final double alpha = t.x * (180 / Math.PI);
		final double beta = t.y * (180 / Math.PI);
		final double gamma = t.z * (180 / Math.PI);

		return new Vector3d(alpha, beta, gamma);

	}

	/**
	 * 
	 * Converts a {@link Vector3f} containing degree measurements into one that
	 * has radian measurements.
	 * 
	 * @param t
	 *            a Vector3f that needs to be converted.
	 * @return a converted Vector3f containing radian measurements.
	 */

	public static final Vector3f getVectorDegreesFloatEuler(Vector3f t) {

		final float alpha = (float) (t.x * (180 / Math.PI));
		final float beta = (float) (t.y * (180 / Math.PI));
		final float gamma = (float) (t.z * (180 / Math.PI));

		return new Vector3f(alpha, beta, gamma);

	}

	/**
	 * 
	 * Rounds {@link Vector3d} in to the nearest {@link Double} round using
	 * {@link #roundDouble(double, double)}.
	 * 
	 * @param in
	 *            a Vector3d that needs to be rounded.
	 * @param round
	 *            a double that is the reference number for rounding.
	 * @return a rounded Vector3d.
	 */

	public static final Vector3d roundVector3d(Vector3d in, double round) {
		return new Vector3d(roundDouble(in.x, round), roundDouble(in.y, round),
				roundDouble(in.z, round));
	}

	/**
	 * 
	 * Rounds {@link Vector3f} in to the nearest {@link Float} round using
	 * {@link #roundFloat(float, float)}
	 * 
	 * @param in
	 *            a Vector3f that needs to be rounded.
	 * @param round
	 *            a double that is the reference number for rounding.
	 * @return a rounded Vector3f.
	 */

	public static final Vector3f roundVector3f(Vector3f in, float round) {
		return new Vector3f(roundFloat(in.x, round), roundFloat(in.y, round),
				roundFloat(in.z, round));
	}

	/**
	 * 
	 * Rounds the {@link Double} in to the nearest {@link Double} to.
	 * 
	 * @param in
	 *            the Double to be rounded.
	 * @param to
	 *            the reference Double for rounding.
	 * @return a rounded Double.
	 */

	public static final double roundDouble(double in, double to) {
		return to * Math.round(in / to);
	}

	/**
	 * 
	 * Rounds the {@link Float} in to the nearest {@link Float} to.
	 * 
	 * @param in
	 *            the Float to be rounded.
	 * @param to
	 *            the reference Float for rounding.
	 * @return a rounded Float.
	 */

	public static final float roundFloat(float in, float to) {
		return to * Math.round(in / to);
	}

	/**
	 * 
	 * Gets the cross product of a {@link Vector3f} and a Vector3f and returns
	 * it.
	 * 
	 * @param a
	 *            a Vector3f to be put into a cross product operation.
	 * @param b
	 *            a Vector3f to be put into a cross product operation.
	 * @return the result of the cross product.
	 */

	public static final Vector3f getCrossProduct(Vector3f a, Vector3f b) {
		final Vector3f end = new Vector3f();
		end.cross(a, b);
		return end;
	}

	/**
	 * 
	 * Gets the cross product of a{@link Vector3d} and a Vector3d and returns
	 * it.
	 * 
	 * @param a
	 *            a Vector3d to be put into a cross product operation.
	 * @param b
	 *            a Vector3d to be put into a cross product operation.
	 * @return the result of the cross product.
	 */

	public static final Vector3d getCrossProduct(Vector3d a, Vector3d b) {
		final Vector3d end = new Vector3d();
		end.cross(a, b);
		return end;
	}

}
