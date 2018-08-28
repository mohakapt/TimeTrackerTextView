package com.github.mohaka.timeTrackerTextView;

import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.github.mohaka.timeTrackerTextView.Utilities.isEmpty;

public class BackgroundTimeTracker extends TimeTracker {
    private Date trackingDate;
    private int[] durationArray;
    private int[] backgroundArray;

    private long interval;

    public BackgroundTimeTracker(Date trackingDate, int[] durationArray, int[] backgroundArray) {
        this(trackingDate, durationArray, backgroundArray, TimeUnit.SECONDS.toMillis(1));
    }

    public BackgroundTimeTracker(Date trackingDate, int[] durationArray, int[] backgroundArray, long interval) {
        setTrackingDate(trackingDate);
        this.durationArray = durationArray;
        this.backgroundArray = backgroundArray;

        this.interval = interval;
    }

    /**
     * @return the date that is being tracked.
     * @see #setTrackingDate(Date)
     */
    public Date getTrackingDate() {
        return trackingDate;
    }

    /**
     * @param trackingDate the date that will be tracked.
     * @throws IllegalArgumentException if trackingDate is null
     * @see #getTrackingDate()
     */
    public void setTrackingDate(Date trackingDate) {
        if (trackingDate == null)
            throw new IllegalArgumentException("trackingDate cannot be null. Setting a null trackingDate will start the timer anyway and waste a lot of cpu time.");
        this.trackingDate = trackingDate;
    }

    public int[] getDurationArray() {
        return durationArray;
    }

    public int[] getBackgroundArray() {
        return backgroundArray;
    }

    @Override
    boolean onUpdate(AppCompatTextView textView) {
        Log.i("BackgroundTimeTracker", "onUpdate(AppCompatTextView)");

        Date referenceDate = getReferenceDate(true);
        Date trackingDate = getTrackingDate();

        if (isEmpty(durationArray) || isEmpty(backgroundArray)) return false;
        if (trackingDate == null) return false;

        long difference = referenceDate.getTime() - trackingDate.getTime();
        int background = 0;
        boolean result = false;

        for (int i = 0; i < durationArray.length; i++) {
            if (difference < durationArray[i] && backgroundArray.length > i) {
                background = backgroundArray[i];
                result = true;
                break;
            }

            background = backgroundArray[backgroundArray.length - 1];
            result = false;
        }

        textView.setBackgroundResource(background);

        return result;
    }

    @Override
    long getInterval() {
        return interval;
    }
}
