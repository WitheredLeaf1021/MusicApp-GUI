package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class ButtonListener implements ActionListener {
	private JFrame frame;
    private String text;
    private BasicPlayer player;
	private BasicController control;
	private JFileChooser chooser = new JFileChooser();
	private HistoryDialog history;
	private PlaylistFrame playlist;
	private CreateListDialog listdialog;
	static DefaultListModel<String> defModel = new DefaultListModel<String>();
	static JList defList;
	static ArrayList<String> ptList = new ArrayList<String>();
	private int choice;
	private File file;
	private FileReader fr;
	private BufferedReader bf;
	private String name;
	private int count = 0;
	private AudioInputStream stream;
	private long length = 0;
	private AudioFormat format;
	private float flame = 0;
	private int playTime = 0;
	static int counter = -1;
	static int now = -1;
	static double volume = 0.50;


	public ButtonListener(JFrame frame, String text, BasicController control) {
		this.frame = frame;
		this.text = text;
		this.control = control;

		history = new HistoryDialog(frame, text, player, control, true, 300, 300, 300, 300);
		history.setListInitData(10, 10, 260, 230);
		playlist = new PlaylistFrame(text, control, 200, 200, 200, 200);
	}


	public ButtonListener(JFrame frame, String text, BasicController control, BasicPlayer player) {
		this.frame = frame;
		this.text = text;
		this.control = control;
		this.player = player;
	}

	public ButtonListener(String text, BasicController control) {
		this.text = text;
		this.control = control;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(text.equals("Open")) {
			choice = chooser.showOpenDialog(null);
			try {
				if (choice == JFileChooser.APPROVE_OPTION){
					//選択されたファイルのパスを取得する
					file = chooser.getSelectedFile();
				    //ファイル名の読み込みをする
					fr = new FileReader(file);
					//タイトルを選ばれたファイルの名前にする(絶対パス)
					frame.setTitle(file.getAbsolutePath());
					history.add(file.getAbsolutePath());
					//ファイルの解放
					fr.close();
					//指定されたサウンドファイルの絶対パスを取得し、再生する
					System.out.println(file.getAbsolutePath());
					try {
						control.stop();
					} catch (BasicPlayerException e1) {
						e1.printStackTrace();
					}
					play(file.getAbsolutePath());
				}
			}catch(Exception ex){
			    ex.printStackTrace();
			}
		}

		if(text.equals("PauseorResume")) {
			if(player.getStatus() == BasicPlayer.PAUSED) {
				try {
					control.resume();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
			}else {
				try {
					control.pause();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(text.equals("Stop")) {
			try {
				control.stop();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}

		if(text.equals("Next")) {
			if(now < counter) {
				try {
					control.stop();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
				now += 1;
				play(history.getPrev(now));
			}
		}

		if(text.equals("Preview")) {
			if(counter > 0 && now > 0) {
				try {
					control.stop();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
				now -= 1;
				play(history.getPrev(now));
			}
		}

		if(text.equals("History")) {
		    history.setVisible(true);
		}

		if(text.equals("Playlist")) {
		    playlist.setVisible(true);
		}

		if(text.equals("create")) {

		}

		if(text.equals("play playlist")) {
			choice = chooser.showOpenDialog(null);
			try {
				if (choice == JFileChooser.APPROVE_OPTION){
					//選択されたファイルのパスを取得する
					file = chooser.getSelectedFile();
				    //ファイル名の読み込みをする
					fr = new FileReader(file);
					bf = new BufferedReader(fr);
					defList = new JList(defModel);

					while((name = bf.readLine()) != null) {
						ptList.add(name);
						defModel.addElement(name.substring(name.lastIndexOf("\\") + 1));
						count++;
					}

					bf.close();

					listPlay(ptList, count);
				}
			}catch(Exception ex){
				System.out.println("c");
			    ex.printStackTrace();
			}
		}
	}

	private void listPlay(ArrayList<String> ptList, int count2) {
		for(int i = 0;i < count;i++) {
			file = new File(ptList.get(i));
			try {
				stream = AudioSystem.getAudioInputStream(file);
			} catch (UnsupportedAudioFileException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			length = stream.getFrameLength();
			format = stream.getFormat();
			flame = format.getSampleRate();
			playTime = (int) (length / flame);
			play(file.getAbsolutePath());
			try {
				Thread.sleep((playTime + 5) * 1000000);
			} catch (InterruptedException e3) {
				e3.printStackTrace();
			}
		}
	}


	public void play(String filename){
		try{
			//ファイルを開いて再生、音量、パンなどを設定する
		    control.open(new File(filename));
		    control.play();
		    control.setGain(volume);
		    control.setPan(0.0);

		}catch (BasicPlayerException e){
			System.out.println("b");
			e.printStackTrace();
		}
	}

	static double getVolume() {
		return volume;
	}

	static void setVolume(double cvolume) {
		volume = cvolume;
	}
}
