package com.github.mohaka.timeTrackerTextView;

import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This implementation of {@link TimeTracker} is used to show deference between {@link PrettyTimeTracker#getTrackingDate()} and {@link PrettyTimeTracker#getReferenceDate()}
 * in user-friendly way. e.g: 4 minutes ago, in 3 month and so on.
 * This class uses {@link PrettyTime} to calculate and show the date.
 */
public class PrettyTimeTracker extends TimeTracker {
    private PrettyTime prettyTime;
    private long interval;

    private Date trackingDate;

    public PrettyTimeTracker(Date trackingDate) {
        this(trackingDate, TimeUnit.SECONDS.toMillis(10));
    }

    public PrettyTimeTracker(Date trackingDate, long interval) {
        setTrackingDate(trackingDate);

        this.prettyTime = new PrettyTime();
        prettyTime.getUnit(JustNow.class)
                .setMaxQuantity(TimeUnit.MINUTES.toMillis(1));

        this.interval = interval;
    }

    /**
     * @return the instance of {@link PrettyTime} that is being used to calculate the date.
     * This might be useful to change the locale or the time units.
     */
    public PrettyTime getPrettyTime() {
        return prettyTime;
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

    @Override
    public boolean onUpdate(AppCompatTextView textView) {
        Log.i("PrettyTimeTracker", "onUpdate(AppCompatTextView)");

        Date referenceDate = getReferenceDate(true);
        Date trackingDate = getTrackingDate();
        if (trackingDate == null) return false;

        prettyTime.setReference(referenceDate);
        String value = prettyTime.format(trackingDate);

        textView.setText(value);
        return true;
    }

    @Override
    public long getInterval() {
        return interval;
    }
}
