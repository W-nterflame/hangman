package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameTimer {
    private Timeline timeline;
    private long startTime;
    private long elapsedTime;
    private Label timerLabel;
    private Runnable timeUpCallback;
    private long duration;

    public GameTimer(Label timerLabel, Runnable timeUpCallback, long duration) {
        this.timerLabel = timerLabel;
        this.timeUpCallback = timeUpCallback;
        this.duration = duration;
        this.elapsedTime = 0;
    }

    public void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= duration) {
                    stopTimer();
                    timeUpCallback.run();
                }
                updateGameTime();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    public void resumeTimer() {
        if (timeline != null) {
            startTime = System.currentTimeMillis() - elapsedTime;
            timeline.play();
        }
    }

    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    private void updateGameTime() {
        long remainingTime = duration - elapsedTime;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
        int seconds = (int) (remainingTime / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getDuration() {
        return duration;
    }
}
