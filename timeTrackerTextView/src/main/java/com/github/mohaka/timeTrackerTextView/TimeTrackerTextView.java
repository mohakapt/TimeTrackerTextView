package com.github.mohaka.timeTrackerTextView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class TimeTrackerTextView extends AppCompatTextView {

    private TreeMap<TimeTracker, Timer> timeTrackers;

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

    private boolean isAttachedToWindow = false;

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
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        startTracking();
    }

    private void init() {
        timeTrackers = new TreeMap<>();
    }

    public void addTracker(TimeTracker tracker) {
        if (timeTrackers.containsKey(tracker)) return;
        timeTrackers.put(tracker, null);
    }

    public void removeTracker(TimeTracker tracker) {
        if (!timeTrackers.containsKey(tracker)) return;
        Timer timer = timeTrackers.get(tracker);
        if (timer != null) timer.cancel();
        timeTrackers.remove(tracker);
    }

    public void removeAllTrackers() {
        for (TimeTracker tracker : timeTrackers.keySet())
            removeTracker(tracker);
    }

    public void startTracking() {
        stopTracking();

        if (isInEditMode()) return;
        if (!isEnabled()) return;
        if (!isAttachedToWindow) return;

        for (TimeTracker tracker : timeTrackers.keySet()) {
            TimerUpdater timerUpdater = new TimerUpdater(this, tracker);

            Timer timer = timeTrackers.get(tracker);
            if (timer != null) timer.cancel();
            timer = new Timer();
            timer.schedule(timerUpdater, 0, tracker.getInterval());

            timeTrackers.put(tracker, timer);
        }
    }

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
        private WeakReference<AppCompatTextView> textView;
        private TimeTracker timeTracker;

        private TimerUpdater(AppCompatTextView textView, TimeTracker timeTracker) {
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
                AppCompatTextView textView = TimerUpdater.this.textView.get();
                if (textView == null) return;
                timeTracker.onUpdate(textView);
            }
        };
    }
}
