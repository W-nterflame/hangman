package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * The GameTimer class manages a countdown timer for the game. 
 * It updates a provided Label with the remaining time and 
 * executes a callback when the time is up.
 */
public class GameTimer {
    private Timeline timeline;
    private long startTime;
    private long totalElapsedTime;  // Total elapsed time across all rounds
    private Label timerLabel;
    private Runnable timeUpCallback;
    private long duration;

    /**
     * Constructs a GameTimer with the specified label, callback, and duration.
     * 
     * @param timerLabel The label to update with the remaining time.
     * @param timeUpCallback The callback to execute when time is up.
     * @param duration The total duration of the timer in milliseconds.
     */
    public GameTimer(Label timerLabel, Runnable timeUpCallback, long duration) {
        this.timerLabel = timerLabel;
        this.timeUpCallback = timeUpCallback;
        this.duration = duration;
        this.totalElapsedTime = 0;
    }

    /**
     * Starts the timer and updates the label every second.
     */
    public void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                long currentTime = System.currentTimeMillis();
                totalElapsedTime += currentTime - startTime;
                startTime = currentTime;

                if (totalElapsedTime >= duration) {
                    stopTimer();
                    timeUpCallback.run();
                }
                updateGameTime();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Pauses the timer, keeping track of the elapsed time.
     */
    public void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
            long currentTime = System.currentTimeMillis();
            totalElapsedTime += currentTime - startTime;
        }
    }

    /**
     * Resumes the timer from where it was paused.
     */
    public void resumeTimer() {
        if (timeline != null) {
            startTime = System.currentTimeMillis();
            timeline.play();
        }
    }

    /**
     * Stops the timer and calculates the total elapsed time.
     */
    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            long currentTime = System.currentTimeMillis();
            totalElapsedTime += currentTime - startTime;
        }
    }

    /**
     * Updates the label with the remaining time formatted as minutes and seconds.
     */
    private void updateGameTime() {
        long remainingTime = duration - totalElapsedTime;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
        int seconds = (int) (remainingTime / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Returns the total elapsed time across all rounds.
     * 
     * @return The total elapsed time in milliseconds.
     */
    public long getTotalElapsedTime() {
        return totalElapsedTime;
    }

    /**
     * Returns the total duration of the timer.
     * 
     * @return The total duration in milliseconds.
     */
    public long getDuration() {
        return duration;
    }
}
