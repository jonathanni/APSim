package com.prodp.apsim;

import java.awt.Dimension;
import java.awt.Image;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-3-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * List of all the processes (games) in progress.
 * 
 * Contains many functions for changing, removing, adding, or viewing processes.
 * 
 */

public class APProcessList extends ArrayList<APProcess> {

	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = 2367149758814065814L;

	private byte current = 0;
	private byte size = 0;

	private volatile boolean isDead = false;

	/**
	 * 
	 * Gets the current process's index.
	 * 
	 * @return the current process index
	 */

	public synchronized byte getCurrentIndex() {
		return current;
	}

	/**
	 * 
	 * Gets the current process.
	 * 
	 * @return the current process
	 */

	public synchronized APProcess getCurrentProcess() {
		if (this != null && size() == size)
			return getProcess((int) current);
		return new APProcess();
	}

	/**
	 * 
	 * Changes the current process to a new process by index.
	 * 
	 * @param i
	 *            the index
	 */

	public synchronized void changeCurrentProcessByIndex(int i) {
		current = (byte) i;
		APProcessHandler.updateProcess();
	}

	/**
	 * 
	 * Changes the current process to a new specified process.
	 * 
	 * On failure (does not contain) it does nothing.
	 * 
	 * @param ap
	 *            the process
	 */

	public synchronized void changeCurrentProcess(APProcess ap) {
		if (contains(ap))
			changeCurrentProcessByIndex(indexOf(ap));
	}

	/**
	 * 
	 * Adds a new process to the list.
	 * 
	 * Performs specific operations on the tabs to add new custom tabs.
	 * 
	 * @param ap
	 *            the process
	 */

	public synchronized void addProcess(APProcess ap) {
		String path;

		ap.setProcessID(size);

		current = size;

		if (ap.save == null)
			ap.save = new APWorld();

		add(ap);

		size++;

		APFinalData.processSwitch
				.addTab((path = StringUtils.substringAfterLast(
						ap.getWorldPath(),
						System.getProperties().getProperty("file.separator"))) == "" ? ap
						.getWorldPath() : path,
						new ImageIcon(APFinalData.apIconImage
								.getScaledInstance(15, 15, Image.SCALE_SMOOTH)),
						null, ap.getWorldPath());
		APFinalData.processSwitch.setSelectedIndex(current);

		APCloseButton close = new APCloseButton("x");
		APIconComponent iconc = new APIconComponent(APFinalData.apIconImage, 5,
				5);
		JPanel pan = new JPanel();

		close.addActionListener(close);
		close.setActionCommand("" + current);
		close.setBorder(new EmptyBorder(0, 0, 0, 0));

		pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));

		pan.add(iconc);
		pan.add(new Box.Filler(new Dimension(5, 5), new Dimension(5, 5),
				new Dimension(5, 5)));
		pan.add(new JLabel(APFinalData.processSwitch.getTitleAt(current)));
		pan.add(new Box.Filler(new Dimension(5, 5), new Dimension(5, 5),
				new Dimension(5, 5)));
		pan.add(close);

		APFinalData.processSwitch.setTabComponentAt(current, pan);

		changeCurrentProcess(ap);
	}

	/**
	 * 
	 * Removes a process by ID.
	 * 
	 * @param ID
	 *            the ID
	 */

	public synchronized void removeProcess(byte ID) {
		remove(ID);
		size--;
		changeCurrentProcessByIndex(size - 1);
	}

	/**
	 * 
	 * Gets a process by ID.
	 * 
	 * @param i
	 *            the ID
	 * @return the process matching the ID
	 */

	public synchronized APProcess getProcess(int i) {
		return get(i);
	}

	/**
	 * 
	 * Cast an object to an APProcess.
	 * 
	 * Equivalent to (APProcess) i.
	 * 
	 * @param i
	 *            the object
	 * @return the casted process
	 */

	public synchronized APProcess cast(Object i) {
		return (APProcess) i;
	}

	/**
	 * 
	 * Removes all the elements of the list, and tells the game that the list is
	 * dead.
	 * 
	 */

	public synchronized void removeAll() {
		isDead = true;
		removeAll(this);
	}
	
	/**
	 * 
	 * Returns if the list is dead.
	 * 
	 * @return the flag
	 */

	public synchronized boolean isDead() {
		return isDead;
	}

}
