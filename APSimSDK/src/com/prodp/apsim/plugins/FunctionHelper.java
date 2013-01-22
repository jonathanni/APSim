package com.prodp.apsim.plugins;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 
 * This function is the "lifeline" to the APSim project.
 * 
 * In the SDK version, this class does not contain any real functions, more like
 * a shell.
 * 
 * Uses reflection to accomplish invocation.
 * 
 * APMaterial String IDs in ascending order:
 * 
 * FIRE,WATER,LAVA,STONE,STEAM,WOOD,ICE,BALL
 * 
 * @version 0.1
 * @author Jonathan
 * 
 */

public class FunctionHelper {

	private static Class<?> lifeline;
	private static HashMap<String, Method> methods;
	private static String qualifiedName = " com.prodp.apsim.plugins.PluginLifeline.";

	static {
		try {
			lifeline = Class.forName("com.prodp.apsim.plugins.PluginLifeline");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Class not found! Are you trying to run the plugin as a standalone?");
		}

		methods = new HashMap<String, Method>();

		for (Method i : lifeline.getMethods())
			methods.put(i.toGenericString(), i);
	}

	private static Object invokeMethod(String genericName, Object... objs)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return methods.get(genericName).invoke(null, objs);
	}

	/**
	 * 
	 * Adds a new block to the scene.
	 * 
	 * @param process
	 *            an APProcess specifying the process.
	 * @param apmaterial
	 *            a String specifying the material to add.
	 * @param location
	 *            a Point3i specifying the location of the block.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void addBlock(Object process, String material, Object location)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		invokeMethod("public static void" + qualifiedName
				+ "addBlock(Object, Object, Object)", process, material,
				location);
	}

	/**
	 * 
	 * Removes a block from the scene.
	 * 
	 * @param process
	 *            an APProcess specifying the process.
	 * @param index
	 *            the index of the block.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */

	public static void removeBlock(Object process, int index)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		invokeMethod("public static void" + qualifiedName
				+ "removeBlock(Object, Integer)", process,
				Integer.valueOf(index));
	}

	/**
	 * Returns the current APProcess. The Object is safe to cast.
	 * 
	 * @return an APProcess.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Object getCurrentProcess() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		return invokeMethod("public static Object" + qualifiedName
				+ "getCurrentProcess()");
	}
}
