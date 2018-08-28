package com.github.mohaka.timeTrackerTextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class TintTimeTracker extends TimeTracker {
    private Date trackingDate;
    private int[] durationArray;
    private int[] colorArray;
    private boolean gradual;

    public TintTimeTracker(Date trackingDate, int[] durationArray, int[] colorArray) {
        setTrackingDate(trackingDate);
        this.durationArray = durationArray;
        this.colorArray = colorArray;
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

    protected int getTintColor() {
        return 0;
    }
}
