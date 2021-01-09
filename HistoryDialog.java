package application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class HistoryDialog extends GenerateDialog{
	static DefaultListModel<String> model = new DefaultListModel<String>();
	static JList historyList;
	static ArrayList<String> nameList = new ArrayList<String>();
	private JScrollPane sp = new JScrollPane();
	private MouseListener mouseListener;
	private int index;

	public HistoryDialog(JFrame frame, String name, BasicPlayer player, BasicController control, boolean modal, int widthD, int heightD, int widthP, int heightP) {
		super(frame, name, player, control, modal, widthD, heightD, widthP, heightP);
	}

	public void setListInitData(int x, int y, int width, int height) {
		historyList = new JList(model);
		sp.getViewport().setView(historyList);
	    	sp.setBounds(x, y, width, height);
	    	panel.add(sp);
	    	mouseListener = new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	        	if (e.getClickCount() == 2) {
	        		index = historyList.locationToIndex(e.getPoint());

	            		try {
	            			control.stop();
	    			} catch (BasicPlayerException e1) {
	    				e1.printStackTrace();
	    			}
	              		play(nameList.get(index));
	           		}
	       		}
	    	};
	    	historyList.addMouseListener(mouseListener);
	}

	public void add(String path) {
		nameList.add(path);
		model.addElement(path.substring(path.lastIndexOf("\\") + 1));
	}

	public String getPrev(int index) {
		return nameList.get(index);
	}

	public void play(String filename){
		try{
			//ファイルを開いて再生、音量、パンなどを設定する
			control.open(new File(filename));
			control.play();
			control.setGain(ButtonListener.volume);
		    	control.setPan(0.0);
		}catch (BasicPlayerException e){
			e.printStackTrace();
		}
	}
}
