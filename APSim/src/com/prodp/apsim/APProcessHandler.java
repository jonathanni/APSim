//INFO:
/*
 * 
 *  This program cannot be redistributed under any other persons.
 * 
 * Uses class "com.centerkey.utils.BareBonesBrowserLaunch" a free utility package released
 * under public domain.
 * Also uses packages included in "jgoodies-common-1.2.1.jar" and "jgoodies-looks-2.4.2.jar" for setLookAndFeel.
 * Also uses AnaglyphCanvas3D, at http://sourceforge.net/projects/anaglyphcanvas3/.
 * Uses screenshot utility at http://www.java.net/node/647363.
 * Uses Java 3D library at http://java3d.java.net/
 * 
 */

//Bug 11611I (November 6, 2011 SOLVED)

package com.prodp.apsim;

//imports
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import anaglyphcanvas3d.AnaglyphMode;
import anaglyphcanvas3d.StereoMode;

import com.centerkey.utils.BareBonesBrowserLaunch;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBluer;
import com.sun.j3d.utils.image.TextureLoader;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-3-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Main controller for all APSim processes. Handles {@link APProcess}es and
 * switches between them. All necessary functions are called from here, or are
 * declared here.
 * 
 */

// This is the main class for everything: implements all the listeners
public class APProcessHandler extends APObject implements ActionListener,
		ChangeListener, MouseListener, MouseMotionListener, WindowListener,
		KeyListener {

	/*
	 * 
	 * The data types are indexed as follows:
	 * 
	 * -private -protected -default -public
	 */

	private static Vector3d OLD_SR_VECTOR;

	// world objects
	private static APRenderer c3d;
	// private SimpleUniverse world;
	private static APSceneGraph scene;
	// private final BranchGroup objects = new BranchGroup();

	// graphics
	private final GraphicsConfigTemplate3D graphics = new GraphicsConfigTemplate3D();
	private GraphicsConfiguration graphicsConfig;
	// main thread
	private volatile Thread threedanim;

	// shape array
	private volatile static QuadArray cMain;

	// wind array
	private volatile static LineArray windLoc;

	// light can only stay in this boundary
	private static BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0,
			0), 20);

	// shapes on scene graph
	private static final Shape3D ground = new Shape3D();
	private static final Shape3D aobjects = new Shape3D();
	private static final Shape3D lobjects = new Shape3D();
	private static final Shape3D selections = new Shape3D();

	// selection box
	private static QuadArray selection = new QuadArray(APFinalData.brushlocs
			.get(APFinalData.MAX_BRUSH_SIZE).size() * 24, QuadArray.COORDINATES
			| QuadArray.BY_REFERENCE);
	private static float[] selectionCoords = new float[APFinalData.brushlocs
			.get(APFinalData.MAX_BRUSH_SIZE).size() * 72];

	private static Transform3D mainRoutineFinalTransform = new Transform3D();

	// setting sensitivity
	private static int sensitivity = 90;

	// Temporary Variables
	// This temporary element is the default element. Should be water in the
	// choose dialog.
	private short tempelement = (short) (APFinalData.DEFAULT_MAT.getID() - 1);
	private boolean antiaOn;
	private AnaglyphMode amode = null;

	// Is running?
	private volatile static boolean isRunning;

	// Number of Untitled s
	private int untitled = 1;

	// Client/Server pair
	private static APClient client = new APClient();
	private static APServer server = new APServer();

	// The sky
	private static APSky sky = new APSky();
	private static TransformGroup skyTG = new TransformGroup();
	private static Transform3D skyTrans = new Transform3D();
	private static double skyRot = 0;

	// number of blocks on scene
	private static int blockNumber = 0;

	volatile static boolean isLeftMouseDown = false, isRightMouseDown = false; // do
																				// not
																				// remove
																				// this

	// key detection flags
	volatile static boolean[] keys = new boolean[15];
	volatile static boolean[] prevaction = new boolean[8];

	// mouse control
	static Robot center;

	// Graphics for pause draw and others
	Graphics2D g;

	/**
	 * A list of all the {@link APProcess}es active.
	 */

	// List of processes and reactions
	public static final APProcessList APList = new APProcessList();

	/**
	 * A static list of all the {@link APReaction}s.
	 */

	public static final APReactionList APRList = new APReactionList();

	/**
	 * Current tool
	 */

	private static int tool = APFinalData.TOOL_SPRAYVAC;

	// Main getters and setters

	/**
	 * 
	 * Moves the player left (relative to position).
	 * 
	 * @param d
	 *            unused
	 */

	public static void moveRelativeLeft(double d) {
		APProcess process = APList.getCurrentProcess();

		process.getCurPos().add(
				Transform3DUtils.rotateEulerVector3d(0, (APList
						.getCurrentProcess().getRotateX() + Math.PI)
						* APFinalData.DEGREE + 90, 0.05));
	}

	/**
	 * 
	 * Moves the player right (relative to position).
	 * 
	 * @param d
	 *            unused
	 */

	public static void moveRelativeRight(double d) {
		APProcess process = APList.getCurrentProcess();

		process.getCurPos().add(
				Transform3DUtils.rotateEulerVector3d(0, (APList
						.getCurrentProcess().getRotateX() + Math.PI)
						* APFinalData.DEGREE - 90, 0.05));
	}

	/**
	 * 
	 * Moves the player forwards (relative to position).
	 * 
	 * @param d
	 *            unused
	 */

	public static void moveRelativeFwd(double d) {
		APProcess process = APList.getCurrentProcess();

		process.getCurPos().add(
				Transform3DUtils.rotateEulerVector3d(0, (APList
						.getCurrentProcess().getRotateX() + Math.PI)
						* APFinalData.DEGREE, 0.05));
	}

	/**
	 * 
	 * Moves the player backwards (relative to position).
	 * 
	 * @param d
	 *            unused
	 */

	public static void moveRelativeBack(double d) {
		APProcess process = APList.getCurrentProcess();

		process.getCurPos().add(
				Transform3DUtils.rotateEulerVector3d(0, (APList
						.getCurrentProcess().getRotateX() + Math.PI)
						* APFinalData.DEGREE + 180, 0.05));
	}

	/**
	 * 
	 * Moves the player up.
	 * 
	 * @param d
	 *            unused
	 */

	public static void moveRelativeUp(double d) {
		APList.getCurrentProcess().getCurPos().add(new Vector3d(0, 0.05, 0));
	}

	/**
	 * 
	 * Moves the player down.
	 * 
	 * @param d
	 *            unused
	 */

	public static void moveRelativeDown(double d) {
		APProcess process = APList.getCurrentProcess();

		process.getCurPos().add(new Vector3d(0, -0.05, 0));
		if (process.getCurPos().y < 0.3) {
			process.getCurPos().y = 0.3;
		}
	}

	/**
	 * 
	 * Increments the brush size by one.
	 * 
	 * It detaches the interchangable {@link BranchGroup} from its parent and
	 * attaches the bigger brush {@link BranchGroup} to the visible parent
	 * {@link BranchGroup}.
	 * 
	 */

	public static void incBrushSize() {
		APProcess process = APList.getCurrentProcess();

		if (process.getBrushSize() != APFinalData.MAX_BRUSH_SIZE - 1) {
			process.setBrushSize(process.getBrushSize() + 1);

			// Force the brush to update
			OLD_SR_VECTOR = null;
		}
	}

	/**
	 * 
	 * Decrements the brush size by one.
	 * 
	 * It detaches the interchangable {@link BranchGroup} from its parent and
	 * attaches the smaller brush {@link BranchGroup} to the visible parent
	 * {@link BranchGroup}.
	 * 
	 */

	public static void decBrushSize() {
		APProcess process = APList.getCurrentProcess();

		if (process.getBrushSize() != 0) {
			process.setBrushSize(process.getBrushSize() - 1);

			// Force the brush to update
			OLD_SR_VECTOR = null;
		}
	}

	/**
	 * 
	 * Toggles if the scene is frozen.
	 * 
	 */

	public static void toggleFrozen() {
		APProcess process = APList.getCurrentProcess();

		process.setFrozen(!process.isFrozen());

	}

	/**
	 * 
	 * Toggles if the selection box is hidden.
	 * 
	 */

	public static void toggleBoxHide() {
		APProcess process = APList.getCurrentProcess();

		process.setBoxHidden(!process.isBoxHidden());

		if (process.isBoxHidden())
			Arrays.fill(selectionCoords, -1);
		else
			selectionCoords = APFinalData.spherecoords[process.getBrushSize()];
	}

	/**
	 * 
	 * Gets the cube face array of the controller.
	 * 
	 * @return the cube face array
	 */

	public static QuadArray getGeometry() {
		return cMain;
	}

	/**
	 * 
	 * Gets the client of the client-server networking system.
	 * 
	 * @return the client
	 */

	public static APClient getClient() {
		return client;
	}

	/**
	 * 
	 * Gets the server of the client-server networking system.
	 * 
	 * @return the server
	 */

	public static APServer getServer() {
		return server;
	}

	/**
	 * 
	 * Sets the sensitivity of the mouse.
	 * 
	 * @param sense
	 *            the sensitivity as a percent
	 */

	public static void setSensitivity(int sense) {
		sensitivity = sense;
	}

	/**
	 * 
	 * Gets the sensitivity of the mouse.
	 * 
	 * @return the sensitivity
	 */

	public static int getSensitivity() {
		return sensitivity;
	}

	/**
	 * 
	 * Sets the brush size to a new brush size.
	 * 
	 * If the new brush size is greater than the current brush size it increases
	 * the brush size until it is equal.
	 * 
	 * If the new brush size is less than the current brush size it decreases
	 * the brush size until it is equal.
	 * 
	 * Otherwise nothing happens.
	 * 
	 * @param s
	 *            the new brush size.
	 */

	public static void setBrushSize(int s) {
		APProcess p = APList.getCurrentProcess();

		if (s > p.getBrushSize()) {
			while (s != p.getBrushSize())
				incBrushSize();
			return;
		}

		if (s < p.getBrushSize()) {
			while (s != p.getBrushSize())
				decBrushSize();
			return;
		}
	}

	/**
	 * 
	 * Gets the current brush size.
	 * 
	 * @return the brush size
	 */

	public static int getBrushSize() {
		return APList.getCurrentProcess().getBrushSize();
	}

	/**
	 * 
	 * Sets the current position of the player.
	 * 
	 * @param p
	 *            the position
	 */

	public static void setCurrentPosition(Point3d p) {
		APList.getCurrentProcess().setCurPos(p);
	}

	/**
	 * 
	 * Gets the current position of the player.
	 * 
	 * @return the position
	 */

	public static Point3d getCurrentPosition() {
		return APList.getCurrentProcess().getCurPos();
	}

	/**
	 * 
	 * Gets the 3D canvas (screen).
	 * 
	 * @return the canvas
	 */

	public static APRenderer getCanvas() {
		return c3d;
	}

	/**
	 * 
	 * Gets if the program is running.
	 * 
	 * @return the flag
	 */

	public static boolean getIsRunning() {
		return isRunning;
	}

	/**
	 * 
	 * Gets how many blocks are on the scene.
	 * 
	 * @return the number of blocks
	 */

	public static int getBlockNumber() {
		return blockNumber;
	}

	/**
	 * 
	 * Sets how many blocks are on the scene
	 * 
	 * @param blockNumber
	 */

	public static void setBlockNumber(int blockNumber) {
		APProcessHandler.blockNumber = blockNumber;
	}

	/**
	 * 
	 * Get the current tool ID of the player.
	 * 
	 * @return the ID
	 */

	public static int getTool() {
		return tool;
	}

	/**
	 * 
	 * Set the tool ID of the player.
	 * 
	 * @param tool
	 *            the ID
	 */

	public static void setTool(int tool) {
		APProcessHandler.tool = tool;
	}

	// End of the getters and setters

	// Ugly initialization code

	/**
	 * 
	 * Sets up the whole game.
	 * 
	 * Sets up things like the GUI, put through {@link APGUI}, the scene
	 * objects, and other things in {@link APFinalData}.
	 * 
	 * @throws AWTException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 * @throws CloneNotSupportedException
	 */

	public void init() throws AWTException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, CloneNotSupportedException {

		// Get the icon
		try {
			APFinalData.apIconImage = new ImageIcon(ImageIO.read(new APMain()
					.getClass().getResource("icons/apsim.png"))).getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// For mouse control and screenshots
		center = new Robot(APFinalData.gd);

		// Set Graphics
		graphics.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
		graphics.setDoubleBuffer(GraphicsConfigTemplate.PREFERRED);
		graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getBestConfiguration(graphics);
		c3d = new APRenderer(graphicsConfig);
		c3d.setVisible(true);
		c3d.setStereoEnable(false);

		// setLookAndFeel
		PlasticLookAndFeel.set3DEnabled(true);
		PlasticLookAndFeel
				.setTabStyle(Plastic3DLookAndFeel.TAB_STYLE_METAL_VALUE);
		PlasticLookAndFeel.setPlasticTheme(new DesertBluer());

		UIManager.setLookAndFeel(new PlasticLookAndFeel());

		Options.setPopupDropShadowEnabled(true);
		Options.setUseNarrowButtons(true);

		// This is the Processor initialization
		final APProcess firstProcess = new APProcess("Untitled" + untitled
				+ ".aps");
		APList.addProcess(firstProcess);

		APProcess process = APList.getCurrentProcess();

		// The default position
		mainRoutineFinalTransform.setTranslation(new Vector3f(0, 0, 0));

		// Set up the grass
		Transform3D grass3d = new Transform3D();
		grass3d.setScale(50);

		TextureAttributes ta = new TextureAttributes();
		ta.setTextureMode(TextureAttributes.MODULATE);
		ta.setPerspectiveCorrectionMode(TextureAttributes.FASTEST);
		ta.setTextureTransform(grass3d);

		Appearance floorapp = new Appearance();
		floorapp.setTexture(new TextureLoader(new APRandImage(1024, 1024,
				BufferedImage.TYPE_3BYTE_BGR, new Color(111, 71, 40),
				new Color(50, 30, 7), new Color(104, 57, 23), new Color(22, 12,
						6)), "RGB", null).getTexture());
		floorapp.setTextureAttributes(ta);

		// Referencing the position of the floor to floorcoord
		APFinalData.floor.setCoordRefFloat(APFinalData.floorcoord);
		APFinalData.floor.setTexCoordRefFloat(0, APFinalData.floortex);

		// Main geometry array
		cMain = new QuadArray(APFinalData.LIMIT * 24, QuadArray.COORDINATES
				| QuadArray.COLOR_4 | QuadArray.BY_REFERENCE
				| QuadArray.TEXTURE_COORDINATE_2);

		// Wind geometry array
		windLoc = new LineArray(2 * APFinalData.PRESSURE_COUNT,
				LineArray.COORDINATES | LineArray.COLOR_3
						| LineArray.BY_REFERENCE);

		// Dynamic positioning
		cMain.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
		cMain.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);

		windLoc.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
		windLoc.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);

		// Set the reference of the geometry array to the coordinates and the
		// colors
		cMain.setCoordRefFloat(process.coords);
		cMain.setColorRefByte(process.colors);
		cMain.setTexCoordRefFloat(0, process.texturecoords);

		windLoc.setCoordRefFloat(process.windcoords);
		windLoc.setColorRefByte(process.windcolors);

		// Now add the reactionary components

		APRList.addReaction(new APReaction(new APPair(APMaterial.WOOD.getID(),
				APMaterial.LAVA.getID()), APMaterial.FIRE, 0.2f,
				APFinalData.CHANGE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.WOOD.getID(),
				APMaterial.FIRE.getID()), APMaterial.FIRE, 0.1f,
				APFinalData.CHANGE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.STONE.getID(),
				APMaterial.LAVA.getID()), APMaterial.LAVA, 0.1f,
				APFinalData.CHANGE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.WATER.getID(),
				APMaterial.LAVA.getID()), APMaterial.STEAM, 0.4f,
				APFinalData.CHANGE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.LAVA.getID(),
				APMaterial.WATER.getID()), APMaterial.STONE, 0.9f,
				APFinalData.CHANGE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.FIRE.getID(),
				APMaterial.WATER.getID()), APMaterial.NULL, 0.9f,
				APFinalData.REMOVE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.WATER.getID(),
				APMaterial.ICE.getID()), APMaterial.ICE, 0.01f,
				APFinalData.CHANGE_BLOCK_FLAG));
		APRList.addReaction(new APReaction(new APPair(APMaterial.ICE.getID(),
				APMaterial.LAVA.getID()), APMaterial.WATER, 1,
				APFinalData.CHANGE_BLOCK_FLAG));

		APFinalData.mainFrame.init();

		// Set the canvas size
		c3d.setPreferredSize(new Dimension(550, 300));
		c3d.setLocation(0, 0);

		// Don't let the geometry clip off
		final Appearance polyAppearance = new Appearance();
		polyAppearance
				.setPolygonAttributes(new PolygonAttributes(
						PolygonAttributes.POLYGON_FILL,
						PolygonAttributes.CULL_NONE, 0));
		polyAppearance.setTextureAttributes(new TextureAttributes());
		polyAppearance.setTexture(APFinalData.textures);

		final Appearance lineAppearance = new Appearance();
		lineAppearance.setLineAttributes(new LineAttributes(1,
				LineAttributes.PATTERN_SOLID, false));

		TransparencyAttributes trans = new TransparencyAttributes();
		trans.setTransparencyMode(TransparencyAttributes.BLENDED);
		polyAppearance.setTransparencyAttributes(trans);

		// Add the GeometryArray to the Shape3D
		aobjects.addGeometry(cMain);
		aobjects.setAppearance(polyAppearance);

		lobjects.addGeometry(windLoc);
		lobjects.setAppearance(lineAppearance);

		ground.addGeometry(APFinalData.floor);
		ground.setAppearance(floorapp);

		// Initialize the AmbientLight
		APFinalData.whiteLight.setInfluencingBounds(bounds);

		// Sky
		Background back = new Background();
		back.setApplicationBounds(new BoundingSphere(new Point3d(0, 0, 0), 1));

		skyTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		skyTrans.rotX(0);
		skyTG.setTransform(skyTrans);
		skyTG.addChild(sky);

		BranchGroup backgeom = new BranchGroup();
		backgeom.addChild(skyTG);

		back.setGeometry(backgeom);

		// Add the brush
		selection.setCapability(QuadArray.ALLOW_REF_DATA_WRITE);
		selections.addGeometry(selection);
		selections.setAppearance(APFinalData.wireframe);

		APFinalData.brushes.addChild(selections);

		// Scene initialization
		scene = new APSceneGraph(c3d);
		scene.addChild(APFinalData.whiteLight, aobjects, lobjects, ground,
				back, APFinalData.brushes);

		// Add it to the world
		scene.addBranchGraph();

		// Prepare the world
		scene.prepare(mainRoutineFinalTransform);

		// Make the Canvas draw right
		c3d.setDoubleBufferEnable(true);

		// Stereo Red-blue
		c3d.getView().getPhysicalBody()
				.setLeftEyePosition(new Point3d(-0.01, 0, 0));
		c3d.getView().getPhysicalBody()
				.setRightEyePosition(new Point3d(0.01, 0, 0));
		c3d.getView().setDepthBufferFreezeTransparent(false);

		// Note: c3d has no anaglyphs
		//c3d.setStereoMode(StereoMode.OFF);

		// Add Canvas listeners
		c3d.addMouseListener(this);
		c3d.addMouseMotionListener(this);
		c3d.addKeyListener(this);

		// Add menubar listeners
		APFinalData.Exit.addActionListener(this);
		APFinalData.New.addActionListener(this);
		APFinalData.Open.addActionListener(this);
		APFinalData.Save.addActionListener(this);
		APFinalData.Save_As.addActionListener(this);
		APFinalData.Import.addActionListener(this);
		APFinalData.Export.addActionListener(this);
		APFinalData.Element.addActionListener(this);
		APFinalData.Exit.addActionListener(this);
		APFinalData.Join_Server.addActionListener(this);
		APFinalData.Create_Server.addActionListener(this);
		APFinalData.View_Options.addActionListener(this);
		APFinalData.Sound.addActionListener(this);
		APFinalData.Online_Help.addActionListener(this);
		APFinalData.About_Us.addActionListener(this);
		APFinalData.Update.addActionListener(this);
		APFinalData.View_Ok.addActionListener(this);
		APFinalData.View_Cancel.addActionListener(this);
		APFinalData.Element_Ok.addActionListener(this);
		APFinalData.Element_Cancel.addActionListener(this);
		APFinalData.antiaDisable.addActionListener(this);
		APFinalData.antiaEnable.addActionListener(this);
		APFinalData.anaglyphFull.addActionListener(this);
		APFinalData.anaglyphGray.addActionListener(this);
		APFinalData.anaglyphHalf.addActionListener(this);
		APFinalData.anaglyphNone.addActionListener(this);
		APFinalData.anaglyphOptim.addActionListener(this);
		APFinalData.anaglyphRedBlue.addActionListener(this);
		APFinalData.anaglyphRedGreen.addActionListener(this);
		APFinalData.senseSlider.addChangeListener(this);
		APFinalData.elementChooser.addActionListener(this);
		APFinalData.SprayVacuum.addActionListener(this);
		APFinalData.Wind.addActionListener(this);

		APFinalData.processSwitch.setBackground(Color.WHITE);
		APFinalData.processSwitch.addChangeListener(this);
		APFinalData.processSwitch
				.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		// Add frame listeners
		APFinalData.mainFrame.addWindowListener(this);
		// APFinalData.mainFrame.setVisible(true);

		// Make the thread
		if (threedanim == null)
			threedanim = new Thread(new APMain(), "APThread");

		// Make it the best thread
		threedanim.setPriority(APSceneGraph.getJ3DThreadPriority());
		threedanim.setDaemon(false);
		threedanim.start();

		// Make it live
		isRunning = true;
	}

	// UPDATE ARRAY METHOD

	/**
	 * 
	 * Updates the coordinate and color array of the box face array.
	 * 
	 */

	public final static void updateArray() {

		final APProcess process = APList.getCurrentProcess();

		cMain.setCoordRefFloat(process.coords);
		cMain.setColorRefByte(process.colors);
		cMain.setTexCoordRefFloat(0, process.texturecoords);

		windLoc.setCoordRefFloat(process.windcoords);
		windLoc.setColorRefByte(process.windcolors);
	}

	/**
	 * 
	 * Update the server processes.
	 * 
	 * @param srv
	 *            the server thread
	 * @throws IOException
	 */

	public static void updateServer(APServerThread srv) throws IOException {
		srv.getOutputStream().writeByte(APFinalData.REQ_CLIENT_UPDATE);
		srv.getOutputStream().writeObject(APList.getCurrentProcess().status);
	}

	/**
	 * 
	 * Adds a new pressure coordinate to the world or augments/decreases the
	 * power of an existing one.
	 * 
	 * @param leftclick
	 *            whether or not the LMB is depressed
	 */

	static final void addWind(boolean leftclick) {

		APProcess process = APList.getCurrentProcess();
		float persistence = 5;

		int empty = APArrayUtils.findEmptySpace(process.pressures);
		if (empty == -1)
			return;

		Point3d sel = new Point3d(selectionCoords[0], selectionCoords[1],
				selectionCoords[2]);
		Point3i realsel = new Point3i((int) Math.round(sel.x
				/ APFinalData.BOXSIZE), (int) Math.round(sel.y
				/ APFinalData.BOXSIZE), (int) Math.round(sel.z
				/ APFinalData.BOXSIZE));

		if (realsel.y < 0)
			return;

		int multiplier = leftclick ? 1 : -1;

		Integer repl;
		if ((repl = process.reversepressuresort.get(realsel)) != null) {

			process.pressures[repl].setValue(process.pressures[repl].getValue()
					+ multiplier * APFinalData.PRESSURE_ADD);
			process.pressures[repl].setPersistence((float) Math
					.sqrt(process.pressures[repl].getPersistence())); // converge
																		// to 1

			if (Math.abs(process.pressures[repl].getValue()) > APFinalData.PRESSURE_CAP)
				process.pressures[repl].setValue(multiplier
						* APFinalData.PRESSURE_CAP);
			return;
		}

		process.pressures[empty] = new APPressurePoint(realsel.x, realsel.y,
				realsel.z, multiplier * APFinalData.PRESSURE_ADD, persistence);
		process.reversepressuresort.put(realsel, empty);

		System.arraycopy(new float[] {
				(float) (sel.x - APFinalData.BOXSIZE / 2), (float) sel.y,
				(float) sel.z, (float) (sel.x + APFinalData.BOXSIZE / 2),
				(float) sel.y, (float) sel.z, (float) sel.x,
				(float) (sel.y - APFinalData.BOXSIZE / 2), (float) sel.z,
				(float) sel.x, (float) (sel.y + APFinalData.BOXSIZE / 2),
				(float) sel.z, (float) sel.x, (float) sel.y,
				(float) (sel.z - APFinalData.BOXSIZE / 2), (float) sel.x,
				(float) sel.y, (float) (sel.z + APFinalData.BOXSIZE / 2) }, 0,
				process.windcoords, empty * 2 * 3 * 3, 18);
	}

	/**
	 * 
	 * Switch two blocks. Switches their positions.
	 * 
	 * @param process
	 *            the current process
	 * @param index1
	 *            one block index
	 * @param index2
	 *            the other block index
	 */

	static final void switchBlock(final APProcess process, final int index1,
			final int index2) {
		short tempstatus = process.status[index1];

		process.status[index1] = process.status[index2];
		process.status[index2] = tempstatus;
	}

	/**
	 * 
	 * Remove a block. Clears the status ID and resets the position back to the
	 * negative y axis. Also clears the coordinate HashMaps.
	 * 
	 * @param process
	 *            the current process
	 * @param index
	 *            the block index to be removed
	 */

	static final void removeBlock(final APProcess process, final int index) {
		process.status[index] = 0;
		process.coords[index * 24 * 3 + 1] = -index - 1;
		process.realcoords[index * 3 + 1] = -index - 1;

		process.reversecoordsort.remove(process.coordsort.get(index));
		process.reversecoordsort.put(null, index);

		process.coordsort.put(index, null);

		blockNumber--;
	}

	/**
	 * 
	 * Add a block. Sets the status ID and sets the position to the argument.
	 * Also sets the coordinate HashMaps. Assumes the index of the block is not
	 * defined.
	 * 
	 * @param process
	 *            the current process
	 * @param index
	 *            the block index to be added
	 * @param loc
	 *            the location of the new block
	 * @param status
	 *            the status (material) of the block
	 */

	static final void addBlock(final APProcess process, final int index,
			Point3i loc, short status) {
		process.status[index] = status;

		process.coords[index * 24 * 3] = loc.x * APFinalData.BOXSIZE;
		process.coords[index * 24 * 3 + 1] = loc.y * APFinalData.BOXSIZE;
		process.coords[index * 24 * 3 + 2] = loc.z * APFinalData.BOXSIZE;

		process.realcoords[index * 3] = loc.x;
		process.realcoords[index * 3 + 1] = loc.y;
		process.realcoords[index * 3 + 2] = loc.z;

		process.texturecoords[index * 24 * 2] = 64 * (process.status[index] - 1);

		process.reversecoordsort.put(loc, index);
		process.coordsort.put(index, loc);
	}

	/**
	 * 
	 * Changes a block to a new block by changing the block ID.
	 * 
	 * @param process
	 *            the current process
	 * @param index
	 *            the block index to be changed
	 * @param material
	 *            the new material
	 */

	static final void changeBlock(final APProcess process, final int index,
			final APMaterial material) {
		process.status[index] = material.getID();
	}

	/**
	 * 
	 * Performs all functions to update the blocks through moving them around or
	 * causing reactions. It takes into account the blocks' attributes, defined
	 * in {@link APMaterial}.
	 * 
	 */

	public static void performEnvironmentFunctions() {

		// This is used a lot
		final APProcess process = APList.getCurrentProcess();

		if (process.isFrozen())
			return;

		// Update pressure points
		APArrayUtils.updatePressure(process);

		for (int i = 0; i < APFinalData.LIMIT; i++) {

			// if(process.status[i] == 0) continue;

			// First things first: get all the blocks around the first block
			if (!APArrayUtils.getFlagger().get(i))
				APArrayUtils.computeAroundIndices(process, i);

			// debug(indices.toString());

			// Make all velocities tend to fall toward the ground except buoyant
			// elements, which actually float upwards
			if (process.realcoords[i * 3 + 1] != 0)
				process.dVelocity[i].y -= 9.8f;

			// Make buoyant elements float up
			if (APMaterialsList.isBuoyant(process.status[i]))
				process.dVelocity[i].y += 15.8f;
			// Grounded Blocks
			else if (process.realcoords[i * 3 + 1] == 0
					&& process.dVelocity[i].y < 0)
				if (process.status[i] == APMaterial.BALL.getID()
						&& Math.abs(process.dVelocity[i].y) > 14f)
					process.dVelocity[i].y *= -0.5f;
				else
					process.dVelocity[i].y = 0;

			// Remove the dead fire
			if (Math.signum(process.dVelocity[i].y) == 1
					&& process.status[i] == APMaterial.FIRE.getID()
					&& APFinalData.random.nextDouble() > 0.9)
				removeBlock(process, i);

			// Change steam
			if (Math.signum(process.dVelocity[i].y) == 1
					&& process.status[i] == APMaterial.STEAM.getID()
					&& APFinalData.random.nextDouble() > 0.8
					&& APFinalData.random.nextBoolean())
				if (APFinalData.random.nextBoolean())
					removeBlock(process, i);
				else
					changeBlock(process, i, APMaterial.WATER);

			if (!APArrayUtils.getFlagger().get(i))
				APArrayUtils.doReqCheckLoopActions(process, i);

			// Return the "Underground" blocks back home as null
			if (process.status[i] == 0) {

				process.coords[i * 24 * 3] = 0;
				process.coords[i * 24 * 3 + 2] = 0;

				process.realcoords[i * 3] = 0;
				process.realcoords[i * 3 + 2] = 0;

			}

			// if (process.realcoords[i * 3 + 1] != -1)
			APArrayUtils.setCoordBlocks(process.coords, i * 24 * 3);
			if (process.status[i] != 0)
				APArrayUtils.setTextureCoordBlocks(process.texturecoords,
						i * 24 * 2, process.status[i]);
			APArrayUtils.setColorBlocks(process.colors,
					APMaterialsList.getMaterialByID(process.status[i]),
					i * 24 * 4);
		}

		APArrayUtils.clearFlagger();
	}

	// UPDATE POSITION METHOD

	/**
	 * 
	 * Renews the brush box locations. It takes the original brush location and
	 * appends it to the player position.
	 * 
	 * @param process
	 *            the current process
	 * @param SR_VECTOR
	 *            the original brush location
	 */

	public static void renewBrushLocs(APProcess process, Vector3d SR_VECTOR) {

		int size = process.getBrushSize();
		for (int i = 0; i < APFinalData.spherecoords[size].length / 3; i++) {
			selectionCoords[i * 3] = (float) (APFinalData.spherecoords[size][i * 3] + SR_VECTOR.x);
			selectionCoords[i * 3 + 1] = (float) (APFinalData.spherecoords[size][i * 3 + 1] + SR_VECTOR.y);
			selectionCoords[i * 3 + 2] = (float) (APFinalData.spherecoords[size][i * 3 + 2] + SR_VECTOR.z);
		}

		selection.setCoordRefFloat(selectionCoords);

	}

	private static Vector3d genSelectorVector(APProcess process) {
		Vector3d SR_VECTOR = Transform3DUtils.rotateEulerVector3d(
				(Math.PI * 2 - process.getRotateY()) * APFinalData.DEGREE,
				(process.getRotateX() + Math.PI) * APFinalData.DEGREE, 2);
		SR_VECTOR.add(process.getCurPos());
		SR_VECTOR = Transform3DUtils.roundVector3d(SR_VECTOR,
				APFinalData.BOXSIZE);
		// SR_VECTOR.add(new Point3d(APFinalData.SHIFT));

		return SR_VECTOR;
	}

	/**
	 * 
	 * Updates the player position and the sky position.
	 * 
	 */

	public static void updatePosition() {

		APProcess process = APList.getCurrentProcess();

		// Also update the stars' position here
		skyRot += Math.PI / 180 / 200;
		skyTrans.rotX(skyRot);
		skyTG.setTransform(skyTrans);

		process.busy = true;

		TransformGroup platform = scene.getViewingPlatform()
				.getViewPlatformTransform();

		Vector3d SR_VECTOR = genSelectorVector(process);

		if (!SR_VECTOR.equals(OLD_SR_VECTOR)) {
			renewBrushLocs(process, SR_VECTOR);
			OLD_SR_VECTOR = new Vector3d(SR_VECTOR);
		}

		// Viewpoint
		mainRoutineFinalTransform.setTranslation(new Vector3f());
		mainRoutineFinalTransform.setEuler(new Vector3d(APList
				.getCurrentProcess().getRotateY(), process.getRotateX(), 0));
		mainRoutineFinalTransform.setTranslation(Transform3DUtils
				.convertPoint3dToVector3d(process.getCurPos()));

		bounds.setCenter(process.getCurPos());

		platform.setTransform(mainRoutineFinalTransform);

		process.busy = false;

		return;
	}

	// Kill the task

	/**
	 * 
	 * Kills the task and ends the game.
	 * 
	 */

	public static void destroy() {

		isRunning = false;

		c3d.stopRenderer();
		APList.removeAll();
		APFinalData.mainFrame.removeAll();
		c3d.stopRenderer();
		APFinalData.mainFrame.dispose();
		System.runFinalization();

		System.exit(0);

	}

	/**
	 * 
	 * Adds a block to the scene.
	 * 
	 * Calculates the positions added via brush box locations, then loops
	 * through them, adding them one by one to the scene. It does not add blocks
	 * if there is a block already there. If the limit is reached it stops.
	 * 
	 * Adding new blocks requires updating coordinates, integral coordinates,
	 * and colors.
	 * 
	 */

	public static void countUp() {

		// This is used a lot
		final APProcess process = APList.getCurrentProcess();

		for (int i = 0; i < APFinalData.brushlocs.get(process.getBrushSize())
				.size(); i++) {
			// Temporary position
			final Point3d cT = new Point3d(selectionCoords[i * 72],
					selectionCoords[i * 72 + 1], selectionCoords[i * 72 + 2]);

			// System.out.println(cT);

			// Get the next available index
			process.aCount = APArrayUtils.findEmptySpace(process.status);

			// Conditionals
			if (process.aCount != -1
					&& !(cT.y < 0)
					&& (process.aCount == 0 || !process.reversecoordsort
							.containsKey(new Point3i((int) Math.round(cT.x
									/ APFinalData.BOXSIZE), (int) Math
									.round(cT.y / APFinalData.BOXSIZE),
									(int) Math
											.round(cT.z / APFinalData.BOXSIZE))))) {

				final int index = process.aCount;

				addBlock(
						process,
						index,
						new Point3i((int) Math
								.round(cT.x / APFinalData.BOXSIZE), (int) Math
								.round(cT.y / APFinalData.BOXSIZE), (int) Math
								.round(cT.z / APFinalData.BOXSIZE)), process
								.getMaterial().getID());

				// Update coord and color
				APArrayUtils.setCoordBlocks(process.coords, index * 24 * 3);
				APArrayUtils.setColorBlocks(process.colors,
						process.getMaterial(), index * 24 * 4);

				blockNumber++;
			}
		}
	}

	/**
	 * 
	 * Removes a block from the scene.
	 * 
	 * Calculates the positions added via brush box locations, then loops
	 * through them, removing blocks one by one from the scene. It does not
	 * remove blocks if there is no block there.
	 * 
	 */

	public static void countDown() {

		// This is used a lot
		final APProcess process = APList.getCurrentProcess();

		for (int i = 0; i < APFinalData.brushlocs.get(process.getBrushSize())
				.size(); i++) {
			// Temporary position
			final Point3d cT = new Point3d(selectionCoords[i * 72],
					selectionCoords[i * 72 + 1], selectionCoords[i * 72 + 2]);

			final Integer index;

			// Conditionals
			if (!(cT.y < 0)
					&& (index = process.reversecoordsort.get(new Point3i(
							(int) Math.round(cT.x / APFinalData.BOXSIZE),
							(int) Math.round(cT.y / APFinalData.BOXSIZE),
							(int) Math.round(cT.z / APFinalData.BOXSIZE)))) != null) {

				removeBlock(process, index);

				// Update coord and color
				APArrayUtils.setCoordBlocks(process.coords, index * 24 * 3);
				APArrayUtils.setColorBlocks(process.colors, APMaterial.NULL,
						index * 24 * 4);
			}
		}
	}

	/**
	 * 
	 * Updates the player viewpoint based on the mouse position.
	 * 
	 * Sensitivity also affects mouse movement. The unit is in pi; Eulerian
	 * rotation is used.
	 * 
	 * @param e
	 */

	public void processMouseMovement(MouseEvent e) {

		if (!APList.getCurrentProcess().isPaused) {

			final double x = MouseInfo.getPointerInfo().getLocation().getX();
			final double y = MouseInfo.getPointerInfo().getLocation().getY();

			APProcess process = APList.getCurrentProcess();

			final double rotx = process.getRotateX();
			final double roty = process.getRotateY();

			process.setRotateX(rotx
					- (x - (APFinalData.mainFrame.getX() + c3d.getWidth() / 2))
					/ 50 * (float) (sensitivity / 500f));
			process.setRotateY(roty
					- (y - (APFinalData.mainFrame.getY() + c3d.getHeight() / 2
							+ APFinalData.processSwitch.getHeight() + APFinalData.top
								.getHeight())) / 50
					* (float) (sensitivity / 500f));

			if (process.getRotateX() < 0) {
				process.setRotateX(Math.PI * 2);
			} else {
				if (process.getRotateX() > Math.PI * 2) {
					process.setRotateX(rotx - Math.PI * 2);
				}
			}
			if (process.getRotateY() < -1.54) {
				process.setRotateY(-1.54);
			}
			if (process.getRotateY() > 1.54) {
				process.setRotateY(1.54);
			}

		}

	}

	/**
	 * Updates game data in the event that a process is changed.
	 */

	public static void updateProcess() {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
			isLeftMouseDown = false;
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
			isRightMouseDown = false;
		e.consume();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
			isLeftMouseDown = true;
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
			isRightMouseDown = true;

		e.consume();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
			isLeftMouseDown = true;
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
			isRightMouseDown = true;

		if (!APList.getCurrentProcess().isRobot
				&& !APList.getCurrentProcess().isPaused)
			processMouseMovement(e);

		e.consume();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (!APList.getCurrentProcess().isRobot
				&& !APList.getCurrentProcess().isPaused)
			processMouseMovement(e);

		e.consume();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_A:
			keys[0] = true;
			break;
		case KeyEvent.VK_D:
			keys[1] = true;
			break;
		case KeyEvent.VK_W:
			keys[2] = true;
			break;
		case KeyEvent.VK_S:
			keys[3] = true;
			break;
		case KeyEvent.VK_Q:
			keys[5] = true;
			break;
		case KeyEvent.VK_Z:
			keys[6] = true;
			break;
		case KeyEvent.VK_F1:
			keys[7] = true;
			break;
		case KeyEvent.VK_F2:
			keys[8] = true;
			break;
		case KeyEvent.VK_ESCAPE:
			keys[4] = true;
			break;
		case KeyEvent.VK_E:
			keys[9] = true;
			break;
		case KeyEvent.VK_C:
			keys[10] = true;
			break;
		case KeyEvent.VK_F3:
			keys[11] = true;
			break;
		case KeyEvent.VK_F4:
			keys[12] = true;
			break;
		case KeyEvent.VK_H:
			keys[13] = true;
			break;
		case KeyEvent.VK_SHIFT:
			keys[14] = true;
			break;
		default:
			APFinalData.debug(keyCode);
		}
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_A:
			keys[0] = false;
			break;
		case KeyEvent.VK_D:
			keys[1] = false;
			break;
		case KeyEvent.VK_W:
			keys[2] = false;
			break;
		case KeyEvent.VK_S:
			keys[3] = false;
			break;
		case KeyEvent.VK_Q:
			keys[5] = false;
			break;
		case KeyEvent.VK_Z:
			keys[6] = false;
			break;
		case KeyEvent.VK_ESCAPE:
			keys[4] = false;
			prevaction[0] = false;
			break;
		case KeyEvent.VK_F1:
			keys[7] = false;
			prevaction[1] = false;
			break;
		case KeyEvent.VK_F2:
			keys[8] = false;
			prevaction[2] = false;
			break;
		case KeyEvent.VK_E:
			keys[9] = false;
			prevaction[3] = false;
			break;
		case KeyEvent.VK_C:
			keys[10] = false;
			prevaction[4] = false;
			break;
		case KeyEvent.VK_F3:
			keys[11] = false;
			prevaction[5] = false;
			break;
		case KeyEvent.VK_F4:
			keys[12] = false;
			prevaction[6] = false;
			break;
		case KeyEvent.VK_H:
			keys[13] = false;
			prevaction[7] = false;
			break;
		case KeyEvent.VK_SHIFT:
			keys[14] = false;
			break;
		default:
			APFinalData.debug(keyCode);
		}

		e.consume();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == APFinalData.Exit) {
			int pref = JOptionPane.showOptionDialog(new JFrame(),
					"Do you want to exit now? All worlds will be lost.",
					"Exit Confirmation", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null,
					APFinalData.prefoptions, APFinalData.prefoptions[2]);
			if (pref == JOptionPane.YES_OPTION) {
				isRunning = false;
				destroy();
			}
		} else if (e.getSource() == APFinalData.New) {

			untitled++;
			APList.addProcess(new APProcess("Untitled" + untitled + ".aps"));
			APList.changeCurrentProcess(APList.getProcess(APList
					.getCurrentProcess().getProcessID()));

		} else if (e.getSource() == APFinalData.Online_Help)

			BareBonesBrowserLaunch
					.openURL("http://apsim.wikispaces.com/APSimulator+Tutorial+and+Help");

		else if (e.getSource() == APFinalData.View_Options) {

			APFinalData.senseBox.setText(((Integer) sensitivity).toString());
			APFinalData.viewop.setVisible(true);

		} else if (e.getSource() == APFinalData.View_Ok) {

			dumpViewVariables();
			APFinalData.viewop.setVisible(false);

		} else if (e.getSource() == APFinalData.About_Us) {

			JLabel aboutLabel = new JLabel(
					"<html><div style=\"width:350px;\">APSimulator "
							+ APFinalData.getVersion()
							+ "<br />"
							+ "Credits<br /><br />"
							+ "Founder: Jonathan Ni<br />"
							+ "Lead Programmer: Jonathan Ni<br />"
							+ "Programmer: Sachin Pandey<br />"
							+ "Physics Adviser: Sachin Pandey<br />"
							+ "PR: Jonathan Ni<br />"
							+ "Project Manager: Jonathan Ni<br />"
							+ "Graphics: Alex Yu, Michael Zhang<br />"
							+ "Original 64x64 Textures: Jonathan Ni, Alex Yu <br /><br />"
							+ "Libraries used: <br />"
							+ "<ul>"
							+ "<li>Java 3D</li>"
							+ "<li>Bare Bones Browser Launch</li>"
							+ "<li>JGoodies Core & LookAndFeel</li>"
							+ "<li>Anaglyph Canvas3D</li>"
							+ "<li>Screenshot Utility: http://www.java.net/node/647363</li>"
							+ "</ul><br />"
							+ "<ul>"
							+ "<li><a href=\"http://apsim.dyndns.org/productiveproductions/licenses/apsimlicense.txt\">APSim License: http://apsim.dyndns.org/productiveproductions/licenses/apsimlicense.txt</a></li>"
							+ "<li><a href=\"http://apsim.dyndns.org/productiveproductions/licenses/APACHE-LICENSE-2.0.txt\">Apache License 2.0: http://apsim.dyndns.org/productiveproductions/licenses/APACHE-LICENSE-2.0.txt</a></li>"
							+ "<li><a href=\"http://apsim.dyndns.org/productiveproductions/licenses/LICENSE-Java3D-v1_5_1.txt\">Java 3D License: http://apsim.dyndns.org/productiveproductions/licenses/LICENSE-Java3D-v1_5_1.txt</a></li>"
							+ "<li><a href=\"http://apsim.dyndns.org/productiveproductions/licenses/JGoodies_LICENSE.txt\">JGoodies License: http://apsim.dyndns.org/productiveproductions/licenses/JGoodies_LICENSE.txt</a></li>"
							+ "</ul>"
							+ "<p>&copy;"
							+ Calendar.getInstance().get(Calendar.YEAR)
							+ " Productive Productions All Rights Reserved</p><br />"
							+ "<p>BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE (AS OF THE TIME OF THIS WRITING), "
							+ "THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. "
							+ " EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS "
							+ "AND/OR OTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY KIND, "
							+ "EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED "
							+ "WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. "
							+ "THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. "
							+ "SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING, "
							+ "REPAIR OR CORRECTION.</p></div></html>");

			JFrame about = new JFrame();

			about.setIconImage(APFinalData.apIconImage);
			about.setTitle("About APSimulator");
			about.setSize(550, 400);

			JScrollPane scroll = new JScrollPane(aboutLabel);

			about.add(scroll);

			about.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			about.setVisible(true);

		} else if (e.getSource() == APFinalData.Save) {
			Matcher mat = Pattern.compile("Untitled[0-9]+\\.aps").matcher(
					APList.getCurrentProcess().save.getPath());
			if (mat.find())
				try {
					saveAs();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			else {
				try {
					APList.getCurrentProcess().save.write();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == APFinalData.Save_As)
			try {
				saveAs();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		else if (e.getSource() == APFinalData.Element)
			APFinalData.elementop.setVisible(true);

		else if (e.getSource() == APFinalData.Element_Ok) {
			APList.getCurrentProcess().setMaterial(
					APMaterialsList.getMaterialByID((short) (tempelement + 1)));
			APFinalData.elementop.setVisible(false);

		} else if (e.getSource() == APFinalData.elementChooser)
			tempelement = (short) ((JComboBox) e.getSource())
					.getSelectedIndex();

		else if (e.getSource() == APFinalData.Element_Cancel)
			APFinalData.elementop.setVisible(false);

		else if (e.getSource() == APFinalData.View_Cancel)
			APFinalData.viewop.setVisible(false);

		else if (e.getSource() == APFinalData.antiaDisable)
			antiaOn = false;

		else if (e.getSource() == APFinalData.antiaEnable)
			antiaOn = true;

		else if (e.getSource() == APFinalData.Open)
			try {
				open();
			} catch (IOException io) {
				io.printStackTrace();
			}
		else if (e.getSource() == APFinalData.Update)
			try {
				checkUpdate();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		else if (e.getSource() == APFinalData.Join_Server)
			try {
				client.init();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		else if (e.getSource() == APFinalData.Create_Server)
			try {
				server.init();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		else if (e.getSource() == APFinalData.anaglyphFull)
			amode = AnaglyphMode.COLOR_ANAGLYPHS;

		else if (e.getSource() == APFinalData.anaglyphGray)
			amode = AnaglyphMode.GRAY_ANAGLYPHS;

		else if (e.getSource() == APFinalData.anaglyphHalf)
			amode = AnaglyphMode.HALFCOLOR_ANAGLYPHS;

		else if (e.getSource() == APFinalData.anaglyphNone)
			amode = null;

		else if (e.getSource() == APFinalData.anaglyphOptim)
			amode = AnaglyphMode.OPTIMIZED_ANAGLYPHS;

		else if (e.getSource() == APFinalData.anaglyphRedBlue)
			amode = AnaglyphMode.REDBLUE_ANAGLYPHS;

		else if (e.getSource() == APFinalData.anaglyphRedGreen)
			amode = AnaglyphMode.REDGREEN_ANAGLYPHS;

		else if (e.getSource() == APFinalData.SprayVacuum) {
			setTool(APFinalData.TOOL_SPRAYVAC);
			APFinalData.SprayVacuum.setSelected(true);
			APFinalData.Wind.setSelected(false);
		}

		else if (e.getSource() == APFinalData.Wind) {
			setTool(APFinalData.TOOL_WIND);
			APFinalData.SprayVacuum.setSelected(false);
			APFinalData.Wind.setSelected(true);
		}
	}

	private void checkUpdate() throws IOException {

		String ver = null;

		try {
			ver = new BufferedReader(
					new InputStreamReader(
							new URL(
									"http://apsim.dyndns.org/productiveproductions/apsim/version.txt")
									.openStream())).readLine();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;

		} catch (IOException e) {
			e.printStackTrace();
			return;

		}

		System.out.println("Client version is " + APFinalData.getVersion()
				+ " while server version is " + ver);

		if (!APFinalData.getVersion().equals(ver)) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Runtime.getRuntime().exec("java -jar APDownloader.jar");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			destroy();
		} else
			JOptionPane.showMessageDialog(new JFrame(), "No updates!");

	}

	/**
	 * 
	 * Opens an APSim file and reads it into the game data.
	 * 
	 * @throws IOException
	 */

	public void open() throws IOException {
		JFileChooser dialog = new JFileChooser(".");
		FileFilter aps = new APFileFilter();
		dialog.addChoosableFileFilter(aps);

		int openval = dialog.showDialog(new JFrame(), "Open");

		if (openval == JFileChooser.APPROVE_OPTION) {

			if (dialog.getSelectedFile().exists())
				// Be careful
				synchronized (APList) {
					APList.addProcess(new APProcess(dialog.getSelectedFile()
							.getAbsolutePath()));
					APList.getCurrentProcess().save.read();
				}
			else
				JOptionPane.showMessageDialog(new JFrame(),
						"Error reading file!");
		}
	}

	/**
	 * 
	 * Exports a 3D model of the scene.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static void exportModel() throws FileNotFoundException, IOException {
		JFileChooser choose = new JFileChooser(".");
		APOBJFilter filter = new APOBJFilter();
		choose.setFileFilter(filter);
		File file;

		int saveVal = choose.showSaveDialog(new JFrame());

		if (saveVal == JFileChooser.APPROVE_OPTION) {

			if (!choose.getSelectedFile().getName().endsWith(".obj"))
				file = new File(choose.getSelectedFile().getAbsolutePath()
						+ ".obj");
			else
				file = choose.getSelectedFile();

			APOBJWriter writer = new APOBJWriter(file,
					APList.getCurrentProcess());
			writer.write();
			writer.close();
		}
	}

	/**
	 * 
	 * Records a series of 3D models; these can be rendered as a sequence when
	 * converted to renderable format.
	 * 
	 * It uses a timer to take a snapshot of the scene every while.
	 * 
	 */

	public static void startRecording() {
		JOptionPane
				.showMessageDialog(
						new JFrame(),
						"WARNING: This function may spew as many as 10000+ files in \n"
								+ "very little time. (BETA FUNCTION) If you want to use this function \n"
								+ ", please do not use numbers at the end of the file name.");

		JFileChooser choose = new JFileChooser(".");
		APOBJFilter filter = new APOBJFilter();
		choose.setFileFilter(filter);
		File file;

		int saveVal = choose.showSaveDialog(new JFrame());

		if (saveVal == JFileChooser.APPROVE_OPTION) {

			// Do not include .obj file extension (the video recorder will take
			// care of that)
			if (choose.getSelectedFile().getName().endsWith(".obj"))
				file = new File(choose
						.getSelectedFile()
						.getAbsolutePath()
						.substring(
								0,
								choose.getSelectedFile().getAbsolutePath()
										.length() - 4));
			else
				file = choose.getSelectedFile();

			APProcess process = APList.getCurrentProcess();

			process.isRecording = true;
			process.setRecord(new APVideoRecorder(file));
			process.getRecord().start();

		}
	}

	/**
	 * 
	 * Stops recording the scene.
	 * 
	 * @see #startRecording()
	 * 
	 */

	public static void stopRecording() {
		APProcess process = APList.getCurrentProcess();

		process.isRecording = false;
		process.getRecord().stop();
	}

	/**
	 * 
	 * Saves the world (haha) under a different name.
	 * 
	 * @throws IOException
	 */

	public void saveAs() throws IOException {

		JFileChooser dialog = new JFileChooser(".");
		FileFilter aps = new APFileFilter();
		dialog.addChoosableFileFilter(aps);

		int saveVal = dialog.showSaveDialog(new JFrame());

		if (saveVal == JFileChooser.APPROVE_OPTION) {
			if (!dialog.getSelectedFile().getName().endsWith(".apsg"))
				APList.getCurrentProcess().save = new APWorld(dialog
						.getSelectedFile().getAbsolutePath() + ".apsg");
			else
				APList.getCurrentProcess().save = new APWorld(dialog
						.getSelectedFile().getAbsolutePath());

			// Be careful

			APList.getCurrentProcess().save.write();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {

		if (we.getSource() == APFinalData.mainFrame) {
			isRunning = false;
			destroy();
		} else {
			if (we.getSource() == APFinalData.viewop) {
				APFinalData.viewop.setVisible(false);
			}
		}
	}

	/**
	 * 
	 * Dumps the options set in the view options dialog into the game.
	 * 
	 */

	public void dumpViewVariables() {

		if (antiaOn)
			c3d.getView().setSceneAntialiasingEnable(true);
		else
			c3d.getView().setSceneAntialiasingEnable(false);

		try {
			if (Integer.parseInt(APFinalData.senseBox.getText()) < 100
					&& Integer.parseInt(APFinalData.senseBox.getText()) >= 0) {
				sensitivity = Integer.parseInt(APFinalData.senseBox.getText());
			} else {
				APFinalData.senseBox.setText("90");
				sensitivity = 90;
			}
		} catch (NumberFormatException ignore) {
			sensitivity = 90;
			APFinalData.senseBox.setText("90");
		}

		//if (amode != null) {
			//c3d.setStereoMode(StereoMode.ANAGLYPH);
			//c3d.setAnaglyphMode(amode);
		//} else
			//c3d.setStereoMode(StereoMode.OFF);
	}

	@Override
	public void stateChanged(ChangeEvent ce) {
		if (ce.getSource() == APFinalData.senseSlider) {
			sensitivity = (int) ((JSlider) ce.getSource()).getValue();
			APFinalData.senseBox.setText(((Integer) sensitivity).toString());

		} else if (ce.getSource() == APFinalData.processSwitch)
			APList.changeCurrentProcessByIndex(((JTabbedPane) ce.getSource())
					.getSelectedIndex());
	}

	/**
	 * 
	 * Draw the pause RESUME [ESC] text on top of the canvas directly.
	 * 
	 * @param x
	 *            the x coordinate of the text
	 * @param y
	 *            the y coordinate of the text
	 */

	public static void drawPause(final int x, final int y) {
		final Graphics g = c3d.getGraphics();

		if (g != null) {
			g.setColor(Color.green);
			g.drawString("RESUME [ESC]", x, y);
		}
	}

	// unused listener functions

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent we) {
	}

	@Override
	public void windowClosed(WindowEvent we) {
	}

	@Override
	public void windowDeactivated(WindowEvent we) {
	}

	@Override
	public void windowDeiconified(WindowEvent we) {
	}

	@Override
	public void windowIconified(WindowEvent we) {
	}

	@Override
	public void windowOpened(WindowEvent we) {
	}
}