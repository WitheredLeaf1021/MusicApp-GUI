package application;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class ChangeListener implements javax.swing.event.ChangeListener {
	private BasicController control;
	private double gain;
	private JSlider volume;

	public ChangeListener(BasicController control, JSlider volume) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.control = control;
		this.volume = volume;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		gain = (double)volume.getValue()/(double)100;

		try {
			control.setGain(gain);
			ButtonListener.setVolume(gain);
		} catch (BasicPlayerException e1) {
			e1.printStackTrace();
		}
	}

}
