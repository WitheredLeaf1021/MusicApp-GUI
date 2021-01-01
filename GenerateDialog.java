package application;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;

public class GenerateDialog {
	private JDialog dialog;
	public JPanel panel;
	public BasicPlayer player;
	public BasicController control;

	public GenerateDialog(JFrame frame, String name, BasicPlayer player, BasicController control, boolean modal, int widthD, int heightD, int widthP, int heightP){
		this.player = player;
		this.control = control;
		dialog = new JDialog(frame, name, modal);
		dialog.setSize(widthD, heightD);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 0, 0);
		panel.setSize(widthP, heightP);
		panel.setBackground(Color.white);
		panel.setLayout(null);
		dialog.add(panel);
	}

	public void setVisible(boolean isTrue) {
		dialog.setVisible(isTrue);
	}
}
