package com.github.mohaka.timeTrackerTextView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * This implementation of {@link AppCompatTextView} updates its properties(Text, BackgroundColor, or any other property) depending on time intervals.
 * Making a time-dependent {@link AppCompatTextView} can quickly consume so much time (You need to set the timer and manage it manually and update the content on every intervals and make sure the timer is killed when it finishes its work)
 * That's a lot if work to be done, that's why i recommend using this View
 */
public class TimeTrackerTextView extends AppCompatTextView {

    private TreeMap<TimeTracker, Timer> timeTrackers;
    private boolean isAttachedToWindow = false;
    private boolean startTrackingAutomatically = true;

    public TimeTrackerTextView(Context context) {
        super(context);
        init();
    }

    public TimeTrackerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeTrackerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        timeTrackers = new TreeMap<>();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedToWindow = true;

        startTracking();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedToWindow = false;

        stopTracking();
    }

    @Override
    public boolean isAttachedToWindow() {
        if (Utilities.hasKitKat())
            return super.isAttachedToWindow();
        else
            return isAttachedToWindow;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        startTracking();
    }

    /**
     * Adds an implementation of {@link TimeTracker} to the list of trackers
     * to be watched when the {@link AppCompatTextView} shows on the screen.
     * Nothing happens when the same tracker is added twice.
     *
     * @param tracker the tracker to be added.
     * @see #removeTracker(TimeTracker)
     * @see #removeAllTrackers()
     */
    public void addTracker(TimeTracker tracker) {
        if (timeTrackers.containsKey(tracker)) return;
        timeTrackers.put(tracker, null);
    }

    /**
     * Immediately stops the timer related to the provided tracker and removes the tracker from the tracking list.
     *
     * @param tracker the tracker to be removed.
     * @see #addTracker(TimeTracker)
     * @see #removeAllTrackers()
     */
    public void removeTracker(TimeTracker tracker) {
        if (!timeTrackers.containsKey(tracker)) return;
        Timer timer = timeTrackers.get(tracker);
        if (timer != null) timer.cancel();
        timeTrackers.remove(tracker);
    }

    /**
     * Immediately stops all timer and removes all time trackers.
     *
     * @see #addTracker(TimeTracker)
     * @see #removeTracker(TimeTracker)
     */
    public void removeAllTrackers() {
        for (TimeTracker tracker : timeTrackers.keySet())
            removeTracker(tracker);
    }

    /**
     * @return true if the {@link TimeTrackerTextView} should start tracking as soon as it gets attached to the window, otherwise false.
     * @see #setStartTrackingAutomatically(boolean)
     */
    public boolean isStartTrackingAutomatically() {
        return startTrackingAutomatically;
    }

    /**
     * Determines whether to start tracking as soon as the view gets attached to the window, otherwise false.
     * Default value is true
     *
     * @see #isStartTrackingAutomatically()
     */
    public void setStartTrackingAutomatically(boolean startTrackingAutomatically) {
        this.startTrackingAutomatically = startTrackingAutomatically;
    }

    /**
     * Start all the time trackers simultaneously.
     * This method is called automatically when {@link #isAttachedToWindow()} and {@link #isStartTrackingAutomatically()} are true.
     * This method won't start the timers if the {@link #isEnabled()} is false or the
     *
     * @see #stopTracking()
     */
    public void startTracking() {
        stopTracking();

        if (isInEditMode()) return;
        if (!isEnabled()) return;
        if (!isAttachedToWindow()) return;

        for (TimeTracker tracker : timeTrackers.keySet()) {
            TimerUpdater timerUpdater = new TimerUpdater(this, tracker);

            Timer timer = timeTrackers.get(tracker);
            if (timer != null) timer.cancel();
            timer = new Timer();
            timer.schedule(timerUpdater, 0, tracker.getInterval());

            timeTrackers.put(tracker, timer);
        }
    }

    /**
     * Immediately stops all the time trackers.
     *
     * @see #startTracking()
     */
    public void stopTracking() {
        for (TimeTracker tracker : timeTrackers.keySet()) {
            Timer timer = timeTrackers.get(tracker);

            if (timer != null) {
                timer.cancel();
                timeTrackers.put(tracker, null);
            }
        }
    }

    private static class TimerUpdater extends TimerTask {
        private WeakReference<TimeTrackerTextView> textView;
        private TimeTracker timeTracker;

        private TimerUpdater(TimeTrackerTextView textView, TimeTracker timeTracker) {
            this.textView = new WeakReference<>(textView);
            this.timeTracker = timeTracker;
        }

        @Override
        public void run() {
            Utilities.runOnUiThread(runnable);
        }

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TimeTrackerTextView textView = TimerUpdater.this.textView.get();
                if (textView == null) return;
                boolean reschedule = timeTracker.onUpdate(textView);

                if (!reschedule)
                    textView.timeTrackers.get(timeTracker).cancel();
            }
        };
    }
}
