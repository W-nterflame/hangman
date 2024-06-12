package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class GameTimer {
	private Timeline timeline;
	private long startTime;
	private long elapsedTime;
	private Label timerLabel;
	
	public GameTimer(Label timerLabel) {
		this.timerLabel = timerLabel;
		this.startTime = System.currentTimeMillis();
		this.elapsedTime = 0;
	}
	
	public void startTimer() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			elapsedTime = System.currentTimeMillis() - startTime;
			updateGameTime();
		}));
		
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	public void stopTimer() {
		if (timeline != null) {
			timeline.stop();
		}
		
		elapsedTime = System.currentTimeMillis() - startTime;
	}
	
	public void pauseTimer() {
		if (timeline != null) {
			timeline.pause();
		}
	}
	
	public void resumeTimer() {
		if (timeline != null) {
			startTime = System.currentTimeMillis() - elapsedTime;
			timeline.play();
		}
	}
	
	private void updateGameTime() {
		int minutes = (int)(elapsedTime / 60000);
		int seconds = (int)(elapsedTime % 60000 / 1000);
		timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
}

