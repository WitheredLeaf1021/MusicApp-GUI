package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class Main {
	BasicPlayer player;
	BasicController control;
	Map audioInfo;
	JScrollBar seekBar;
	JLabel time;
	static double volume = 50.0;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		player = new BasicPlayer();
		control = (BasicController) player;

		JFrame frame = new JFrame("MP3 Player");
		frame.setSize(800 , 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setResizable(false);

		JPanel panel = new JPanel();
	    panel.setBounds(0, 0, 0, 0);
		panel.setSize(800 , 200);
		panel.setBackground(Color.white);
		panel.setLayout(null);
	    frame.add(panel);

	    JButton openButton = new JButton("File Open");
	    openButton.setBounds(290,10,100,30);
	    panel.add(openButton);

		JButton pauseButton = new JButton("Resume/Pause");
	    pauseButton.setBounds(120,110,150,30);
	    panel.add(pauseButton);

	    JButton stopButton = new JButton("Stop");
	    stopButton.setBounds(290,110,100,30);
	    panel.add(stopButton);

	    JButton nextButton = new JButton("Next");
	    nextButton.setBounds(410,110,80,30);
	    panel.add(nextButton);

	    JButton previewButton = new JButton("Prev");
	    previewButton.setBounds(20,110,80,30);
	    panel.add(previewButton);

	    JButton historyButton = new JButton("History");
	    historyButton.setBounds(50,10,100,30);
	    panel.add(historyButton);

	    JButton listButton = new JButton("Playlist");
	    listButton.setBounds(170,10,100,30);
	    panel.add(listButton);

	    JSlider volume = new JSlider(JSlider.VERTICAL,1,100,50);
	    volume.setBounds(550,30,30,100);
	    panel.add(volume);

	    seekBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
	    seekBar.setBounds(20,70,430,20);
	    seekBar.setPreferredSize(new Dimension(200, seekBar.getPreferredSize().height));
	    panel.add(seekBar);

	    JLabel label = new JLabel();
	    label.setText("volume");
	    label.setBounds(540,140,100,10);
	    panel.add(label);

	    time = new JLabel();
	    time.setText("00:00");
	    time.setBounds(460,75,100,10);
	    panel.add(time);

	    JScrollPane sp = new JScrollPane();
	    sp.getViewport().setView(ButtonListener.defList);
	    sp.setBounds(600, 10, 180, 180);
	    panel.add(sp);

	    frame.setVisible(true);

	    player.addBasicPlayerListener(bpl);
	    volume.addChangeListener(new ChangeListener(control, volume));
	    openButton.addActionListener(new ButtonListener(frame , "Open", control));
	    pauseButton.addActionListener(new ButtonListener(frame , "PauseorResume", control, player));
	    stopButton.addActionListener(new ButtonListener(frame , "Stop", control));
	    nextButton.addActionListener(new ButtonListener(frame , "Next", control));
	    previewButton.addActionListener(new ButtonListener(frame , "Preview", control));
	    historyButton.addActionListener(new ButtonListener(frame , "History", control));
	    listButton.addActionListener(new ButtonListener(frame , "Playlist", control));
	}
	AdjustmentListener al = new AdjustmentListener() {
        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
          if (!seekBar.getValueIsAdjusting()) {
            try {
              // シーク位置計算
              long bytes = Long.parseLong(audioInfo.get("audio.length.bytes").toString());;
              long seek = bytes * seekBar.getValue() / seekBar.getMaximum();

              // シーク
              player.removeBasicPlayerListener(bpl);
              volume = ButtonListener.getVolume();
              player.seek(seek);
              player.addBasicPlayerListener(bpl);
              ButtonListener.setVolume(volume);
              control.setGain(volume);
            } catch (NumberFormatException e1) {
              e1.printStackTrace();
            } catch (BasicPlayerException e1) {
              e1.printStackTrace();
            }
          }
        }
	};

      BasicPlayerListener bpl = new BasicPlayerListener() {
    	    @Override
    	    public void stateUpdated(BasicPlayerEvent event) {
    	    }

    	    @Override
    	    public void setController(BasicController controller) {
    	    }

    	    @Override
    	    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
    	      long total = Long.parseLong(audioInfo.get("audio.length.bytes").toString());
    	      int newValue = (int) ((double) bytesread / total * seekBar.getMaximum());
    	      if (newValue != seekBar.getValue() && !seekBar.getValueIsAdjusting()) {
    	        seekBar.removeAdjustmentListener(al);

    	        // トータル秒数計算
    	        long bitrate = Long.parseLong(audioInfo.get("bitrate").toString());
    	        int seconds = (int) (total / (bitrate / 8));

    	        // 現在の秒数
    	        int nowSecond = (int) (bytesread * seconds / total);

    	        // 分と秒を計算
    	        int tm = seconds / 60;
    	        int ts = seconds % 60;
    	        int m = nowSecond / 60;
    	        int s = nowSecond % 60;

    	        seekBar.setValue(newValue);
    	        time.setText(String.format("%1$02d:%2$02d / %3$02d:%4$02d", m, s, tm, ts));
    	        seekBar.addAdjustmentListener(al);
    	      }
    	    }

    	    @Override
    	    public void opened(Object stream, Map properties) {
    	      audioInfo = properties;
    	    }
      };
}
