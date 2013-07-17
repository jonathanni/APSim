package com.prodp.apsim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticButtonUI;
import com.jgoodies.looks.plastic.PlasticComboBoxUI;
import com.jgoodies.looks.plastic.PlasticMenuBarUI;
import com.jgoodies.looks.plastic.PlasticTextAreaUI;
import com.jgoodies.looks.windows.WindowsTabbedPaneUI;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-2-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * This initiates and creates every GUI element used in the game, including the
 * main {@link JFrame} and the title screen.
 * 
 */

public class APGUI extends JFrame implements ActionListener {

	// Called by APProcessHandler

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -894893343034109868L;

	/**
	 * Initiates all the GUI elements in the game.
	 */

	public void init() {

		APFinalData.fps.setEditable(false);

		// Add the sensitivity JTextArea
		APFinalData.senseBox = new JTextArea(
				((Integer) APProcessHandler.getSensitivity()).toString());
		APFinalData.senseBox.setColumns(3);
		APFinalData.senseBox.setSize(50, 50);
		APFinalData.senseBox.setEditable(false);

		// Server components
		APFinalData.serverFrame.setSize(550, 400);
		APFinalData.serverPane.setEditable(false);
		APFinalData.serverFrame.add(APFinalData.serverPane);
		APFinalData.serverFrame
				.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Add the menu bar components
		APFinalData.File.add(APFinalData.New);
		APFinalData.File.addSeparator();
		APFinalData.File.add(APFinalData.Open);
		APFinalData.File.add(APFinalData.Save);
		APFinalData.File.add(APFinalData.Save_As);
		APFinalData.File.addSeparator();
		APFinalData.File.add(APFinalData.Import);
		APFinalData.File.add(APFinalData.Export);
		APFinalData.File.addSeparator();
		APFinalData.File.add(APFinalData.Join_Server);
		APFinalData.File.add(APFinalData.Create_Server);
		APFinalData.File.addSeparator();
		APFinalData.File.add(APFinalData.Exit);

		APFinalData.Edit.add(APFinalData.Element);
		APFinalData.Edit.add(APFinalData.Tools);

		APFinalData.View.add(APFinalData.View_Options);

		APFinalData.Options.add(APFinalData.Sound);

		APFinalData.Help.add(APFinalData.Online_Help);
		APFinalData.Help.add(APFinalData.About_Us);
		APFinalData.Help.add(APFinalData.Update);

		APFinalData.fps.setDoubleBuffered(true);
		APFinalData.fps.setEditable(false);
		APFinalData.fps.setUI(new PlasticTextAreaUI());

		APFinalData.top.add(APFinalData.File);
		APFinalData.top.add(APFinalData.Edit);
		APFinalData.top.add(APFinalData.View);
		APFinalData.top.add(APFinalData.Options);
		APFinalData.top.add(APFinalData.Help);
		APFinalData.top.add(APFinalData.fps);

		APFinalData.top.setUI(new PlasticMenuBarUI());
		APFinalData.top.putClientProperty(Options.HEADER_STYLE_KEY,
				HeaderStyle.SINGLE);

		APFinalData.Tools.add(APFinalData.SprayVacuum);
		APFinalData.Tools.add(APFinalData.Wind);

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(550, 400));
		setLocation(0, 0);

		// View Components
		APFinalData.senseSlider.setSize(200, 20);

		APFinalData.vbuttonSection.add(APFinalData.View_Ok);
		APFinalData.vbuttonSection.add(APFinalData.View_Cancel);

		APFinalData.senseSlider.setMajorTickSpacing(10);
		APFinalData.senseSlider.setPaintTicks(true);
		APFinalData.senseSlider.setPaintLabels(true);

		APFinalData.senseSection.add(APFinalData.sense);
		APFinalData.senseSection.add(APFinalData.senseBox);
		APFinalData.senseSection.add(APFinalData.senseSlider);

		APFinalData.antiaDisable.setSelected(true);

		APFinalData.antiaGroup.add(APFinalData.antiaDisable);
		APFinalData.antiaGroup.add(APFinalData.antiaEnable);

		APFinalData.antiaSection.add(APFinalData.antia);
		APFinalData.antiaSection.add(APFinalData.antiaEnable);
		APFinalData.antiaSection.add(APFinalData.antiaDisable);

		APFinalData.anaglyphNone.setSelected(true);

		APFinalData.anaglyphGroup.add(APFinalData.anaglyphFull);
		APFinalData.anaglyphGroup.add(APFinalData.anaglyphGray);
		APFinalData.anaglyphGroup.add(APFinalData.anaglyphHalf);
		APFinalData.anaglyphGroup.add(APFinalData.anaglyphNone);
		APFinalData.anaglyphGroup.add(APFinalData.anaglyphOptim);
		APFinalData.anaglyphGroup.add(APFinalData.anaglyphRedBlue);
		APFinalData.anaglyphGroup.add(APFinalData.anaglyphRedGreen);

		APFinalData.lanaglyphSection.setLayout(new BoxLayout(
				APFinalData.lanaglyphSection, BoxLayout.PAGE_AXIS));
		APFinalData.lanaglyphSection.add(APFinalData.anaglyph);
		APFinalData.lanaglyphSection.add(APFinalData.anaglyphFull);
		APFinalData.lanaglyphSection.add(APFinalData.anaglyphGray);
		APFinalData.lanaglyphSection.add(APFinalData.anaglyphHalf);

		APFinalData.ranaglyphSection.setLayout(new BoxLayout(
				APFinalData.ranaglyphSection, BoxLayout.PAGE_AXIS));
		APFinalData.ranaglyphSection.add(APFinalData.anaglyphNone);
		APFinalData.ranaglyphSection.add(APFinalData.anaglyphOptim);
		APFinalData.ranaglyphSection.add(APFinalData.anaglyphRedBlue);
		APFinalData.ranaglyphSection.add(APFinalData.anaglyphRedGreen);

		APFinalData.fanaglyphSection.add(APFinalData.lanaglyphSection,
				BorderLayout.WEST);
		APFinalData.fanaglyphSection.add(APFinalData.ranaglyphSection,
				BorderLayout.EAST);

		// Element Components
		APFinalData.elementChooser.setUI(new PlasticComboBoxUI());
		APFinalData.elementChooser.setEditable(false);
		APFinalData.elementChooser.setSelectedIndex(APProcessHandler.APList
				.getCurrentProcess().getMaterial().getID() - 1);

		APFinalData.elementSection.add(APFinalData.elementl);
		APFinalData.elementSection.add(APFinalData.elementChooser);

		APFinalData.Element_Ok.setUI(new PlasticButtonUI());
		APFinalData.Element_Cancel.setUI(new PlasticButtonUI());

		APFinalData.ebuttonSection.add(APFinalData.Element_Ok);
		APFinalData.ebuttonSection.add(APFinalData.Element_Cancel);

		APFinalData.mainFrame.setJMenuBar(APFinalData.top);
		APFinalData.mainFrame.setGlassPane(new APGlassPane());
		APFinalData.mainFrame.getGlassPane().setVisible(true);
		APFinalData.mainFrame.add(APProcessHandler.getCanvas());
		APFinalData.mainFrame
				.add(APFinalData.processSwitch, BorderLayout.NORTH);
		APFinalData.mainFrame.setLocation(500, 200);
		APFinalData.mainFrame.setTitle("APSimulator");
		APFinalData.mainFrame.setIconImage(APFinalData.apIconImage);
		APFinalData.mainFrame.pack();

		APFinalData.start.addActionListener(this);
		APFinalData.exit.addActionListener(this);

		APBlurLabel title = new APBlurLabel("APSim " + APFinalData.getVersion());
		title.setFont(APFinalData.header3);

		APFinalData.titleMenu.setUndecorated(true);
		APFinalData.titleMenu.setAlwaysOnTop(true);

		APFinalData.titleMenu.setLayout(new FlowLayout(FlowLayout.CENTER));

		APFinalData.titleButtons.setLayout(new BoxLayout(
				APFinalData.titleButtons, BoxLayout.X_AXIS));

		APFinalData.titleButtons.add(Box.createHorizontalStrut(15));
		APFinalData.titleButtons.add(title, BorderLayout.NORTH);
		APFinalData.titleButtons.add(Box.createHorizontalStrut(15));

		APFinalData.titleButtons.add(APFinalData.start);
		APFinalData.titleButtons.add(Box.createHorizontalStrut(15));
		APFinalData.titleButtons.add(APFinalData.exit);

		APFinalData.titleMenu.add(APFinalData.titleButtons);
		APFinalData.titleMenu.pack();
		APFinalData.titleMenu.setSize(APFinalData.titleMenu.getWidth(),
				APFinalData.titleMenu.getHeight() * 2);
		APFinalData.titleMenu.pack();
		APFinalData.titleMenu.setVisible(true);
		APFinalData.titleMenu
				.setLocation(
						(Toolkit.getDefaultToolkit().getScreenSize().width - APFinalData.titleMenu
								.getWidth()) / 2, (Toolkit.getDefaultToolkit()
								.getScreenSize().height - APFinalData.titleMenu
								.getHeight()) / 2);

		// Layout
		APFinalData.viewop.getContentPane().setLayout(
				new BoxLayout(APFinalData.viewop.getContentPane(),
						BoxLayout.PAGE_AXIS));

		// Set the Viewing Options Size
		APFinalData.viewop.setIconImage(APFinalData.apIconImage);
		APFinalData.viewop.setTitle("View Options...");

		// Set the Element Options Size
		APFinalData.elementop.setIconImage(APFinalData.apIconImage);
		APFinalData.elementop.setTitle("Element Selection...");

		// Add the Sensitivity Components
		APFinalData.viewop.add(APFinalData.senseSection, BorderLayout.NORTH);
		APFinalData.viewop.add(APFinalData.antiaSection, BorderLayout.NORTH);
		APFinalData.viewop
				.add(APFinalData.fanaglyphSection, BorderLayout.SOUTH);
		APFinalData.viewop.add(APFinalData.vbuttonSection, BorderLayout.SOUTH);
		APFinalData.viewop.pack();

		// Add the Elemental Components
		APFinalData.elementop.add(APFinalData.elementSection);
		APFinalData.elementop.add(APFinalData.ebuttonSection,
				BorderLayout.SOUTH);
		APFinalData.elementop.pack();

		APFinalData.processSwitch.setDoubleBuffered(true);
		APFinalData.processSwitch.setUI(new WindowsTabbedPaneUI());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == APFinalData.start) {
			APFinalData.mainFrame.setVisible(true);
			APFinalData.titleMenu.dispose();
		} else
			APProcessHandler.destroy();
	}
}
