package com.github.mohaka.timeTrackerTextView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import java.util.Date;

import static com.github.mohaka.timeTrackerTextView.Utilities.isEmpty;

public class BackgroundTintTimeTracker extends TimeTracker {
    private Date trackingDate;
    private int[] durationArray;
    private int[] colorArray;
    private boolean gradual;
    private long interval;

    public BackgroundTintTimeTracker(Date trackingDate, int[] durationArray, int[] colorArray) {
        this(trackingDate, durationArray, colorArray, false);
    }

    public BackgroundTintTimeTracker(Date trackingDate, int[] durationArray, int[] colorArray, boolean gradual) {
        setTrackingDate(trackingDate);
        this.durationArray = durationArray;
        this.colorArray = colorArray;
        setGradual(gradual);
        interval = gradual ? 300 : 1000;
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

    public int[] getColorArray() {
        return colorArray;
    }

    public boolean isGradual() {
        return gradual;
    }

    public void setGradual(boolean gradual) {
        this.gradual = gradual;
    }

    @Override
    boolean onUpdate(AppCompatTextView textView) {
        Log.i("BackgroundTintTimeTracker", "onUpdate(AppCompatTextView)");

        Date referenceDate = getReferenceDate(true);
        Date trackingDate = getTrackingDate();

        if (isEmpty(durationArray) || isEmpty(colorArray)) return false;
        if (trackingDate == null) return false;

        long difference = referenceDate.getTime() - trackingDate.getTime();
        int tintColor = 0;
        boolean result = false;

        for (int i = 0; i < durationArray.length; i++) {
            if (difference < durationArray[i] && colorArray.length > i) {
                tintColor = colorArray[i];
                result = true;
                break;
            }

            tintColor = colorArray[colorArray.length - 1];
            result = false;
        }

        Drawable background = textView.getBackground();
        background.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);

        return result;
    }

    @Override
    long getInterval() {
        return interval;
    }
}
