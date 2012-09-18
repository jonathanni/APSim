package com.prodp.apsim;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Point3i;
import javax.vecmath.Tuple3i;

/**
 * 
 * @author Jonathan
 * @version 0.1
 * @since 7-1-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Contains all of the constants in APSim as well as some miscellaneous members.
 * 
 */

public class APFinalData extends APObject {

	/**
	 * 
	 * Target FPS for viewer.
	 * 
	 */

	public static final int TARGET_FPS = 120;

	/**
	 * Either 16 or 32 bits.
	 */

	public static final byte byteSize = Byte.parseByte(System
			.getProperty("sun.arch.data.model"));

	// colors

	/**
	 * The color red, (1r, 0g, 0b).
	 */

	public static final Color3f red = new Color3f(1.0f, 0.0f, 0.0f);

	/**
	 * The color green, (0r, 1g, 0b).
	 */

	public static final Color3f green = new Color3f(0.0f, 1.0f, 0.0f);

	/**
	 * The color white, (1r, 1g, 1b).
	 */

	public static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

	/**
	 * The color black, (0r, 0g, 0b).
	 */

	public static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

	/**
	 * The color blue, (0r, 0g, 1b).
	 */

	public static final Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);

	/**
	 * The color sky blue, (.3r, 0g, 1b).
	 */

	public static final Color3f skyblue = new Color3f(0.3f, 0.0f, 1.0f);

	// server frame

	/**
	 * The window for the server, showing server data.
	 */

	public static final JFrame serverFrame = new JFrame();

	/**
	 * The pane to write on for the server frame.
	 */

	public static final JTextPane serverPane = new JTextPane();

	/**
	 * The Styling Controller for the server pane; allows the Courier text to be
	 * used.
	 */
	public static final StyledDocument serverDocument = serverPane
			.getStyledDocument();

	// white light

	/**
	 * Light seen everywhere. Allows the boxes to be seen.
	 */

	public static final AmbientLight whiteLight = new AmbientLight(
			APFinalData.white);

	/**
	 * 
	 * A single Quad containing the floor geometry.
	 * 
	 */

	// ground
	public static final QuadArray floor = new QuadArray(4,
			QuadArray.COORDINATES | QuadArray.COLOR_3 | QuadArray.BY_REFERENCE);

	/**
	 * 
	 * The colors of the floor.
	 * 
	 */

	// floor data
	public static final float[] floorcolor = new float[] { 0.22f, 0.44f, 0.8f,
			0.22f, 0.44f, 0.8f, 0.22f, 0.44f, 0.8f, 0.22f, 0.44f, 0.8f };

	/**
	 * 
	 * The coordinates of the floor.
	 * 
	 */

	public static final float[] floorcoord = { -100, 0, 100, 100, 0, 100, 100,
			0, -100, -100, 0, -100 };

	/**
	 * 
	 * The maximum brush size.
	 * 
	 */

	// Brush blocks (int)
	public static final int MAX_BRUSH_SIZE = 5;

	/**
	 * 
	 * The rough spherical coordinates of each block of the brush.
	 * 
	 */

	public static final HashMap<Integer, ArrayList<Tuple3i>> brushlocs = createBrushLocs(MAX_BRUSH_SIZE);

	/**
	 * 
	 * The brushes' parents.
	 * 
	 */

	// Group for brush blocks
	public static final BranchGroup brushes = new BranchGroup();

	/**
	 * 
	 * The APSim logo.
	 * 
	 */

	// Icon image for window
	public static Image apIconImage;

	/**
	 * 
	 * The selection box's wireframe appearance.
	 * 
	 */

	// Wireframe box
	public static final Appearance wireframe = new Appearance();

	/**
	 * 
	 * A random number generator.
	 * 
	 */

	// Random
	public static final Random random = new Random();

	/**
	 * 
	 * An array containing all true values.
	 * 
	 */

	// Full
	public static final boolean[] full = new boolean[] { true, true, true,
			true, true, true, true, true };

	/**
	 * 
	 * All the icons.
	 * 
	 */

	// menu icons
	@SuppressWarnings("javadoc")
	public static ImageIcon New_Icon, Open_Icon, Save_Icon, Save_As_Icon,
			Import_Icon, Export_Icon, Exit_Icon, Element_Icon,
			View_Options_Icon, Sound_Icon, Online_Help_Icon, About_Us_Icon,
			Update_Icon, Create_Server_Icon, Join_Server_Icon;

	/**
	 * 
	 * Supposed to be an animated GIF on the menu screen.
	 * 
	 * @category unused
	 * 
	 */

	// Animated gifs
	public static JLabel animated0;

	static {
		wireframe
				.setPolygonAttributes(new PolygonAttributes(
						PolygonAttributes.POLYGON_LINE,
						PolygonAttributes.CULL_NONE, 0));
		try {
			New_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/new.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Open_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/folder.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Save_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/save.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Save_As_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/save_as.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Exit_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/exit.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Update_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/update.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Import_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/import.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Export_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/export.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Online_Help_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/help.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			About_Us_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/aboutus.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Element_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/element.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			Sound_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/sound.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			View_Options_Icon = new ImageIcon(ImageIO.read(
					APMain.class.getResource("icons/magnify.png"))
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			// TODO join server icon
			// TODO create server icon

			// animated0 = new JLabel(new ImageIcon(
			// APMain.class.getResource("icons/animated0.gif")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * The array limit. Limits the number of blocks that can be put onto the
	 * screen.
	 * 
	 */

	// array limit
	public static final short LIMIT = 16384;

	/**
	 * 
	 * The main frame; contains the toolbar, the canvas, and other components.
	 * 
	 */

	// components
	public static final APGUI mainFrame = new APGUI();

	/**
	 * 
	 * The front title menu; contains start and the exit button.
	 * 
	 */

	public static final JFrame titleMenu = new JFrame();

	/**
	 * 
	 * The menu bar; contains all the necessary functions the user needs.
	 * 
	 */

	public static final JMenuBar top = new JMenuBar();

	/**
	 * 
	 * The panel containing the buttons on the menu.
	 * 
	 */

	public static final JPanel titleButtons = new JPanel();

	/**
	 * 
	 * Big monospaced font.
	 * 
	 */

	public static final Font header = new Font("Courier New", Font.BOLD, 72);

	/**
	 * 
	 * Medium monospaced font.
	 * 
	 */

	public static final Font header2 = new Font("Courier New", Font.BOLD, 36);

	/**
	 * 
	 * Medium Sans-Serif font.
	 * 
	 */

	public static final Font header3 = new Font("Trebuchet MS", Font.BOLD, 36);

	/**
	 * 
	 * Big Sans-Serif font.
	 * 
	 */

	public static final Font button = new Font("Trebuchet MS", Font.BOLD, 72);

	/**
	 * 
	 * Start button.
	 * 
	 */

	public static final APButton start = new APButton("Start", button);

	/**
	 * 
	 * Exit button.
	 * 
	 */

	public static final APButton exit = new APButton("Exit", button);

	/**
	 * 
	 * File menu selection. Contains all the functions for the APSim file, and
	 * exiting.
	 * 
	 */

	public static final JMenu File = new JMenu("File");

	/**
	 * 
	 * Edit menu selection. Contains all the functions for editing the APSim
	 * world.
	 * 
	 */

	public static final JMenu Edit = new JMenu("Edit");

	/**
	 * 
	 * View menu selection. Contains all the functions for managing the game
	 * view.
	 * 
	 */

	public static final JMenu View = new JMenu("View");

	/**
	 * 
	 * Options menu selection. Contains other options.
	 * 
	 */

	public static final JMenu Options = new JMenu("Options");

	/**
	 * 
	 * Help menu selection. Contains help information, game information, and
	 * developer information.
	 * 
	 */

	public static final JMenu Help = new JMenu("Help");

	/**
	 * 
	 * Tools menu selection. Contains tools for gameplay.
	 * 
	 */

	public static final JMenu Tools = new JMenu("Tools");

	/**
	 * 
	 * Opens existing file.
	 * 
	 */

	public static final JMenuItem Open = new JMenuItem("Open", Open_Icon);

	/**
	 * 
	 * Exits the game.
	 * 
	 */

	public static final JMenuItem Exit = new JMenuItem("Exit", Exit_Icon);

	/**
	 * 
	 * Creates a new world and file.
	 * 
	 */

	public static final JMenuItem New = new JMenuItem("New", New_Icon);

	/**
	 * 
	 * Saves the world (Get it?).
	 * 
	 */

	public static final JMenuItem Save = new JMenuItem("Save", Save_Icon);

	/**
	 * 
	 * Saves the world under a different name.
	 * 
	 */

	public static final JMenuItem Save_As = new JMenuItem("Save As...",
			Save_As_Icon);

	/**
	 * 
	 * Imports objects.
	 * 
	 * @category unused
	 * 
	 */

	public static final JMenuItem Import = new JMenuItem("Import...",
			Import_Icon);

	/**
	 * 
	 * Exports objects.
	 * 
	 * @category unused
	 * 
	 */

	public static final JMenuItem Export = new JMenuItem("Export...",
			Export_Icon);

	/**
	 * 
	 * Joins a server.
	 * 
	 * @category development
	 * 
	 */

	public static final JMenuItem Join_Server = new JMenuItem("Join server...");

	/**
	 * 
	 * Creates a server.
	 * 
	 * @category development
	 * 
	 */

	public static final JMenuItem Create_Server = new JMenuItem(
			"Create new server...");

	/**
	 * 
	 * Brings up a element chooser.
	 * 
	 */

	public static final JMenuItem Element = new JMenuItem("Elements",
			Element_Icon);

	/**
	 * 
	 * Brings up a shape dialog.
	 * 
	 * @category unused
	 * 
	 */

	public static final JMenuItem Shapes = new JMenuItem("Shapes");

	/**
	 * 
	 * Brings up view options.
	 * 
	 */

	public static final JMenuItem View_Options = new JMenuItem(
			"View Options...", View_Options_Icon);

	/**
	 * 
	 * Brings up sound options.
	 * 
	 * @category development
	 * 
	 */

	public static final JMenuItem Sound = new JMenuItem("Sound", Sound_Icon);

	/**
	 * 
	 * Brings up online help. Uses the default browser.
	 * 
	 */

	public static final JMenuItem Online_Help = new JMenuItem("Online Help",
			Online_Help_Icon);

	/**
	 * 
	 * Brings up an about APSim/us dialog.
	 * 
	 */

	public static final JMenuItem About_Us = new JMenuItem("About APSimulator",
			About_Us_Icon);

	/**
	 * 
	 * Checks for an update.
	 * 
	 * @category development
	 * 
	 */

	public static final JMenuItem Update = new JMenuItem("Check for Update",
			Update_Icon);

	/**
	 * 
	 * Tells the game to put blocks.
	 * 
	 */

	public static final JRadioButtonMenuItem Spray = new JRadioButtonMenuItem(
			"Spray", true);

	/**
	 * 
	 * Tells the game to remove blocks.
	 * 
	 */

	public static final JRadioButtonMenuItem Vacuum = new JRadioButtonMenuItem(
			"Vacuum", false);

	/**
	 * 
	 * Sensitivity meter.
	 * 
	 */

	public static JTextArea senseBox;

	/**
	 * 
	 * Transparent cursor used to hide the cursor.
	 * 
	 */

	// cursor transparency
	public static final Cursor transparentCursor = Toolkit.getDefaultToolkit()
			.createCustomCursor(
					Toolkit.getDefaultToolkit().createImage(
							new MemoryImageSource(16, 16, new int[16 * 16], 0,
									16)), new Point(0, 0), "invisibleCursor");

	/**
	 * 
	 * Version number.
	 * 
	 * @see #getVersion()
	 * 
	 */

	// Version number
	private static final String VERSION = "V0.1.4.2pa";

	/**
	 * 
	 * Returns the current version of APSim. Of form
	 * V#[#].#[#].#[#].#[#][pa/a/b/g../f].
	 * 
	 * @return the version of APSim
	 */

	// Version Function
	protected static final String getVersion() {
		return VERSION;
	}

	/**
	 * 
	 * Gets the spherical "boxy" brush coordinates.
	 * 
	 * @param i
	 *            the radius
	 * @return the positions
	 */

	private static HashMap<Integer, ArrayList<Tuple3i>> createBrushLocs(int i) {
		HashMap<Integer, ArrayList<Tuple3i>> list = new HashMap<Integer, ArrayList<Tuple3i>>();

		for (int j = 0; j <= i; j++) {
			list.put(j, new ArrayList<Tuple3i>());

			for (int x = -j; x < j + 1; x++)
				for (int y = -j; y < j + 1; y++)
					for (int z = -j; z < j + 1; z++)
						if ((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= Math
								.pow(j, 2))
							list.get(j).add(new Point3i(x, y, z));
		}

		return list;
	}

	/**
	 * 
	 * FPS meter.
	 * 
	 */

	// FPS
	public static final JTextArea fps = new JTextArea();

	/**
	 * 
	 * The box size. (a scale in fact)
	 * 
	 */

	// Box size
	public static final float BOXSIZE = 0.02f;

	/**
	 * 
	 * Conversion from radians to degrees.
	 * 
	 */

	// Used for Easy Degree Conversion w/o Transform3DUtils
	public final static double DEGREE = (180 / Math.PI);

	/**
	 * 
	 * The speed of movement.
	 * 
	 */

	// Speed of movement
	public final static double SPEED = 0.15;

	/**
	 * 
	 * Default dialog options.
	 * 
	 */

	// Default options
	public final static String[] prefoptions = { "Yes", "No", "Cancel" };

	/**
	 * 
	 * Shifts the blocks by this much to make it aligned to the ground.
	 * 
	 */

	// Shift array by this amount
	public static final Point3f SHIFT = new Point3f(-BOXSIZE / 2f,
			-BOXSIZE / 2f, -BOXSIZE / 2f);

	/**
	 * 
	 * Box measurement pointing down.
	 * 
	 */

	public static final Point3f BOX = new Point3f(0, -BOXSIZE, 0);

	// BOX finals

	/**
	 * 
	 * Box vertex +x.
	 * 
	 */

	public static final Point3f BOXX = new Point3f(BOXSIZE, 0, 0);

	/**
	 * 
	 * Box vertex +y.
	 * 
	 */

	public static final Point3f BOXY = new Point3f(0, APFinalData.BOXSIZE, 0);

	/**
	 * 
	 * Box vertex +z.
	 * 
	 */

	public static final Point3f BOXZ = new Point3f(0, 0, APFinalData.BOXSIZE);

	/**
	 * 
	 * Box vertex +x+y.
	 * 
	 */

	public static final Point3f BOXXY = new Point3f(APFinalData.BOXSIZE,
			APFinalData.BOXSIZE, 0);

	/**
	 * 
	 * Box vertex +y+z.
	 * 
	 */

	public static final Point3f BOXYZ = new Point3f(0, APFinalData.BOXSIZE,
			APFinalData.BOXSIZE);

	/**
	 * 
	 * Box vertex +x+z.
	 * 
	 */

	public static final Point3f BOXXZ = new Point3f(APFinalData.BOXSIZE, 0,
			APFinalData.BOXSIZE);

	/**
	 * 
	 * Box vertex +x+y+z.
	 * 
	 */

	public static final Point3f BOXXYZ = new Point3f(APFinalData.BOXSIZE,
			APFinalData.BOXSIZE, APFinalData.BOXSIZE);

	/**
	 * 
	 * View options frame.
	 * 
	 */

	// Alternate frames
	public static final JFrame viewop = new JFrame();

	/**
	 * 
	 * Element selection frame.
	 * 
	 */

	public static final JFrame elementop = new JFrame();

	/**
	 * 
	 * String version of {@link #byteSize}.
	 * 
	 */

	// BITMODE
	public static final String BITMODE = System
			.getProperty("sun.arch.data.model");

	/**
	 * 
	 * Sensitivity label.
	 * 
	 */

	// LABELS & PANELS FOR VIEWMODE
	public static final JLabel sense = new JLabel("Sensitivity: ");

	/**
	 * 
	 * Antialiasing option label.
	 * 
	 */

	public static final JLabel antia = new JLabel(
			"Antialiasing Enabled (Only if Supported): ");

	/**
	 * 
	 * 3D anaglyph label.
	 * 
	 */

	public static final JLabel anaglyph = new JLabel("Anaglyph Type: ");

	/**
	 * 
	 * Sensitivity panel.
	 * 
	 */

	public static final JPanel senseSection = new JPanel();

	/**
	 * 
	 * Antialiasing panel.
	 * 
	 */

	public static final JPanel antiaSection = new JPanel();

	/**
	 * 
	 * Left anaglyph panel.
	 * 
	 */

	public static final JPanel lanaglyphSection = new JPanel();

	/**
	 * 
	 * Right anaglyph panel.
	 * 
	 */

	public static final JPanel ranaglyphSection = new JPanel();

	/**
	 * 
	 * Other anaglyph panel.
	 * 
	 */

	public static final JPanel fanaglyphSection = new JPanel();

	/**
	 * 
	 * View button panel.
	 * 
	 */

	public static final JPanel vbuttonSection = new JPanel();

	/**
	 * 
	 * Option for enabling antialiasing.
	 * 
	 */

	public static final JRadioButton antiaEnable = new JRadioButton("Enable");

	/**
	 * 
	 * Option for disabling antialiasing.
	 * 
	 */

	public static final JRadioButton antiaDisable = new JRadioButton("Disable");

	/**
	 * 
	 * The button group containing the antialiasing options.
	 * 
	 */

	public static final ButtonGroup antiaGroup = new ButtonGroup();

	/**
	 * 
	 * No anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphNone = new JRadioButton("None");

	/**
	 * 
	 * Red-Blue anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphRedBlue = new JRadioButton(
			"Red-Blue 3D");

	/**
	 * 
	 * Red-Green anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphRedGreen = new JRadioButton(
			"Red-Green 3D");

	/**
	 * 
	 * Grayscale anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphGray = new JRadioButton("Gray 3D");

	/**
	 * 
	 * Full-color anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphFull = new JRadioButton(
			"Full Color 3D");

	/**
	 * 
	 * Half-color anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphHalf = new JRadioButton(
			"Half Color 3D");

	/**
	 * 
	 * Optimized anaglyph option.
	 * 
	 */

	public static final JRadioButton anaglyphOptim = new JRadioButton(
			"Optimized 3D");

	/**
	 * 
	 * Button group containing the anaglyph options.
	 * 
	 */

	public static final ButtonGroup anaglyphGroup = new ButtonGroup();

	/**
	 * 
	 * OK button.
	 * 
	 */

	public static final JButton View_Ok = new JButton("OK");

	/**
	 * 
	 * Cancel button.
	 * 
	 */

	public static final JButton View_Cancel = new JButton("Cancel");

	/**
	 * 
	 * Sensitivity slider (0-100).
	 * 
	 */

	public static final JSlider senseSlider = new JSlider(JSlider.HORIZONTAL,
			0, 100, 90);

	/**
	 * 
	 * Element picker label.
	 * 
	 */

	// LABELS & PANELS FOR ELEMENT PICKING
	public static final JLabel elementl = new JLabel("Choose an Element: ");

	/**
	 * 
	 * Element picker.
	 * 
	 */

	public static final JComboBox elementChooser = new JComboBox(
			APMaterialsList.getMaterialList());

	/**
	 * 
	 * Element picker panel.
	 * 
	 */

	public static final JPanel elementSection = new JPanel();

	/**
	 * 
	 * Element button panel.
	 * 
	 */

	public static final JPanel ebuttonSection = new JPanel();

	/**
	 * 
	 * OK button.
	 * 
	 */

	public static final JButton Element_Ok = new JButton("OK");

	/**
	 * 
	 * Cancel button.
	 * 
	 */

	public static final JButton Element_Cancel = new JButton("Cancel");

	/**
	 * 
	 * Tabs for each process.
	 * 
	 */

	// TABS
	public static final JTabbedPane processSwitch = new JTabbedPane();

	/**
	 * 
	 * Sleeping precision.
	 * 
	 */

	// SLEEPTIME AND PRECISION
	public static final long SLEEP_PRECISION = TimeUnit.MILLISECONDS
			.toNanos(10);

	/**
	 * 
	 * Sleeping time.
	 * 
	 */

	public static final int SLEEPTIME = 30;

	/**
	 * 
	 * Nanoseconds in a second.
	 * 
	 */

	public static final int NANOS_IN_SECOND = 1000000000;

	/**
	 * 
	 * Change block flag for networking.
	 * 
	 */

	// FLAGS START AT 0x10000000
	public static final int CHANGE_BLOCK_FLAG = 0x10000000;

	/**
	 * 
	 * Remove block flag for networking.
	 * 
	 */

	public static final int REMOVE_BLOCK_FLAG = 0x10000001;

	/**
	 * 
	 * Exchange block flag for networking.
	 * 
	 */

	public static final int MOVE_BLOCK_FLAG = 0x10000002;

	// TOOL CODES START AT 0x01000000

	/**
	 * 
	 * Put block flag for networking.
	 * 
	 */

	public static final int SPRAY_FLAG = 0x01000000;

	/**
	 * 
	 * Vacuum block flag for networking.
	 * 
	 */

	public static final int VACUUM_FLAG = 0x01000001;

	// DATA CODES START AT 0x00

	/**
	 * 
	 * Keep the connection alive.
	 * 
	 */

	public static final byte KEEP_ALIVE = 0x00;

	/**
	 * 
	 * Send the name of the user.
	 * 
	 */

	public static final byte NAME = 0x01;

	/**
	 * 
	 * End sending the name.
	 * 
	 */

	public static final byte END_NAME = 0x02;

	/**
	 * 
	 * A user enters.
	 * 
	 */

	public static final byte ENTER = 0x03;

	/**
	 * 
	 * A user leaves.
	 * 
	 */

	public static final byte EXIT = 0x04;

	/**
	 * 
	 * A client requests an update of the player position.
	 * 
	 */

	public static final byte UPDATE_POSITION = 0x05;

	/**
	 * 
	 * A request for block updates.
	 * 
	 */

	public static final byte REQ_BLOCK_UPDATE = 0x06;

	/**
	 * 
	 * A request for client updates.
	 * 
	 */

	public static final byte REQ_CLIENT_UPDATE = 0x07;

	/**
	 * 
	 * A request for client loading all the data.
	 * 
	 */

	public static final byte REQ_CLIENT_LOAD = 0x08;

	/**
	 * 
	 * Slack for float operations.
	 * 
	 */

	// PRECISION ADJUSTMENT
	public static final float EPSILON = BOXSIZE / 4;

	/**
	 * 
	 * The version of the file format. New in 0.1.4.3pa.
	 * 
	 */

	public static final int FORMAT_VERSION = 1;

	/**
	 * 
	 * Graphics device for the canvas.
	 * 
	 */

	// graphics info
	public static GraphicsDevice gd = mainFrame.getGraphicsConfiguration()
			.getDevice();

}
