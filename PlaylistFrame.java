package application;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javazoom.jlgui.basicplayer.BasicController;

public class PlaylistFrame {
	private JFrame frame;
	public JPanel panel;
	public BasicController control;
	private JButton create;
	private JButton play;

	public PlaylistFrame(String name, BasicController control, int widthD, int heightD, int widthP, int heightP){
		this.control = control;
		frame = new JFrame(name);
		frame.setSize(widthD, heightD);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.setResizable(false);
		panel = new JPanel();
		panel.setBounds(0, 0, 0, 0);
		panel.setSize(widthP, heightP);
		panel.setBackground(Color.white);
		panel.setLayout(null);
		create = new JButton("create playlist");
		create.setBounds(20, 40, 130, 30);
		create.addActionListener(new ButtonListener("create", control));
		play = new JButton("play playlist");
		play.setBounds(30, 80, 110, 30);
		play.addActionListener(new ButtonListener("play playlist", control));
		panel.add(create);
		panel.add(play);
		frame.add(panel);
	}

	public void setVisible(boolean isTrue) {
		frame.setVisible(isTrue);
	}
}
