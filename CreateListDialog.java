package application;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;

public class CreateListDialog extends GenerateDialog{
	static DefaultListModel<String> listModel = new DefaultListModel<String>();
	static JList playList;
	static ArrayList<String> pathList = new ArrayList<String>();
	private JScrollPane sp = new JScrollPane();
	private String name;
	private MouseListener mouseListener;
	private int index;

	public CreateListDialog(JFrame frame, String name, BasicPlayer player, BasicController control, boolean modal, int widthD, int heightD, int widthP, int heightP) {
		super(frame, name, player, control, modal, widthD, heightD, widthP, heightP);
	}
}
