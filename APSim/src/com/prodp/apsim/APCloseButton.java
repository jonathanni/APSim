package com.prodp.apsim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 
 * @author Jonathan
 * @version 0.0
 * @since 7-1-2012 (Javadoc Created)
 * 
 */

/**
 * 
 * Since {@link JTabbedPane} does not have close buttons, this is an
 * implementation of them, a custom component.
 * 
 * This class listens to the ActionListener associated with this button and
 * controls the {@link JTabbedPane} associated with this button.
 * 
 */

public class APCloseButton extends JButton implements ActionListener {

	/**
	 * Serial Version UID.
	 */
	
	private static final long serialVersionUID = -5141270571862598997L;

	/**
	 * 
	 * Creates a new close button.
	 * 
	 * @param string
	 *            the name of the button (should be "X")
	 */

	public APCloseButton(String string) {
		super(string);
		setOpaque(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (APFinalData.processSwitch.getTabCount() != 1) {
			int pref = JOptionPane.showConfirmDialog(new JFrame(),
					"Are you sure you want to close?", "Close?",
					JOptionPane.YES_NO_OPTION);

			if (pref == JOptionPane.YES_OPTION) {
				String com = ((JButton) ae.getSource()).getActionCommand();

				for (int i = 0; i < APFinalData.processSwitch.getTabCount(); i++) {
					JPanel panel = (JPanel) APFinalData.processSwitch
							.getTabComponentAt(i);
					JButton button = ((JButton) panel.getComponent(4));

					if (com.equals(button.getActionCommand())) {
						APProcessHandler.APList.removeProcess((byte) i);
						APFinalData.processSwitch.removeTabAt(i);
						break;
					}
				}
			}
		}
	}

}
