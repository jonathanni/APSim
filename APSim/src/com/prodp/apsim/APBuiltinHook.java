package com.prodp.apsim;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.prodp.apsim.lang.*;
import com.prodp.apsim.lang.Boolean;
import com.prodp.apsim.lang.Number;
import com.prodp.apsim.lang.String;

public class APBuiltinHook {
	public static Number sin(Number a) {
		return new Number(Math.sin(Math.toRadians(a.getValue())));
	}

	public static Number cos(Number a) {
		return new Number(Math.cos(Math.toRadians(a.getValue())));
	}

	public static Number tan(Number a) {
		return new Number(Math.tan(Math.toRadians(a.getValue())));
	}

	public static Number asin(Number a) {
		return new Number(Math.toDegrees(Math.asin(a.getValue())));
	}

	public static Number acos(Number a) {
		return new Number(Math.toDegrees(Math.acos(a.getValue())));
	}

	public static Number atan(Number a) {
		return new Number(Math.toDegrees(Math.atan(a.getValue())));
	}

	public static Number dec(String a) {
		return new Number(Double.valueOf(a.getValue()));
	}

	public static Number dec(Boolean a) {
		return a.isValue() ? new Number(1) : new Number(0);
	}

	public static String str(Number a) {
		return new String(java.lang.String.valueOf(a.getValue()));
	}

	public static String str(Boolean a) {
		return new String(java.lang.String.valueOf(a.isValue()));
	}

	public static Boolean bool(Number a) {
		return a.getValue() == 0 ? new Boolean(false) : new Boolean(true);
	}

	public static Boolean bool(String a) {
		return a.getValue().equals("true") ? new Boolean(true) : new Boolean(
				false);
	}

	public static Number toDeg(Number a) {
		return new Number(Math.toDegrees(a.getValue()));
	}

	public static Number toRad(Number a) {
		return new Number(Math.toRadians(a.getValue()));
	}

	public static Number abs(Number a) {
		return new Number(Math.abs(a.getValue()));
	}

	public static Number min(Number a, Number b) {
		return new Number(Math.min(a.getValue(), b.getValue()));
	}

	public static Number max(Number a, Number b) {
		return new Number(Math.max(a.getValue(), b.getValue()));
	}

	public static String slice(String a, Number start, Number end) {

		final int s = (int) start.getValue();
		final int e = (int) end.getValue();

		return new String(a.getValue().substring(s, e));
	}

	public static String chr(Number a) {
		return new String(java.lang.String.valueOf((char) ((int) a.getValue())));
	}

	public static Number ord(String a) {
		return new Number((int) a.getValue().toCharArray()[0]);
	}

	public static Array<String> list(String a) {
		char[] split = a.getValue().toCharArray();
		java.lang.String[] ssplit = new java.lang.String[split.length];

		for (int i = 0; i < split.length; i++)
			ssplit[i] = java.lang.String.valueOf(split[i]);

		Array<String> arr = new Array<String>(ssplit,
				new int[] { ssplit.length });

		return arr;
	}

	public static Number len(String a) {
		return new Number(a.getValue().length());
	}

	public static Number len(Array<?> a) {
		return new Number(a.getLength());
	}

	public static String type(Variable a) {
		if (a instanceof Boolean)
			return new String("b_var");
		else if (a instanceof Number)
			return new String("n_var");
		return new String("s_var");
	}

	public static String type(Array<?> a) {
		if (a.getUnderlyingType().equals(Boolean.class))
			return new String("b_var_array");
		else if (a.getUnderlyingType().equals(Double.class))
			return new String("n_var_array");
		return new String("s_var_array");
	}

	public static Number round(Number a) {
		return new Number(Math.round(a.getValue()));
	}

	// main functions

	public static Boolean printHUDMessage(String a, Number b, Number c,
			Number d, String e) {

		APFinalData.msg.setText("<html><p style=\"color:rgb("
				+ (int) b.getValue() + "," + (int) c.getValue() + ","
				+ (int) d.getValue() + ");font-family:" + e.getValue() + "\">"
				+ a.getValue() + "</p></html>");

		return new Boolean(true);
	}

	public static Boolean printHUDMessage(String a, Number b, Number c, Number d) {
		return printHUDMessage(a, b, c, d, new String("Arial"));
	}

	public static Boolean printHUDMessage(String a) {
		return printHUDMessage(a, new Number(0), new Number(0), new Number(0));
	}

	public static Boolean dispPopupMessage(String a, String b, Number c,
			Number d, Number e) {

		JOptionPane.showMessageDialog(new JFrame(), b.getValue(), a.getValue(),
				JOptionPane.INFORMATION_MESSAGE);

		return new Boolean(true);
	}

	public static Boolean dispPopupMessage(String a, String b) {

		return dispPopupMessage(a, b, new Number(0), new Number(0), new Number(
				0));

	}

	public static Boolean logStdout(String a) {
		APMain.debug(a.getValue());

		return new Boolean(true);
	}
	
	

}
