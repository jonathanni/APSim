package com.prodp.apsim;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-7-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * The 3D scene; includes utility functions to make scene preparation easier.
 * 
 */

public class APSceneGraph extends SimpleUniverse {

	private BranchGroup group = new BranchGroup();

	/**
	 * 
	 * Constructor; creates a new scene based on the canvas.
	 * 
	 * @param c
	 */

	public APSceneGraph(Canvas3D c) {
		super(c);
		c.getView().setFrontClipDistance(0.05);
	}

	/**
	 * 
	 * Adds multiple children instead of one node.
	 * 
	 * @param n
	 *            the nodes
	 */

	public void addChild(Node... n) {
		for (Node a : n)
			group.addChild(a);
	}

	/**
	 * 
	 * Adds the group containing the objects.
	 * 
	 */

	public void addBranchGraph() {
		addBranchGraph(group);
	}

	/**
	 * 
	 * Prepares the scene by allowing the viewpoint to be set.
	 * 
	 * @param initcoord
	 *            the starting location
	 */

	public void prepare(Transform3D initcoord) {
		getViewingPlatform().getViewPlatformTransform().setTransform(initcoord);
		getViewingPlatform().setNominalViewingTransform();
	}

	/**
	 * 
	 * Gets the group containing the objects.
	 * 
	 * @return the group
	 */

	public BranchGroup getBranchGraph() {
		return group;
	}

	/**
	 * 
	 * Gets the node equal to the input node. Equivalent to a
	 * <code>contains(Node)</code> function.
	 * 
	 * @param n
	 *            the node
	 * @return the node equivalent
	 */

	public Node getChild(Node n) {
		for (Node next = (Node) group.getAllChildren().nextElement(); group
				.getAllChildren().hasMoreElements();)
			if (next.equals(n))
				return next;
		return null;
	}
}
