package com.github.mohaka.timeTrackerTextView;

import android.support.v7.widget.AppCompatTextView;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This implementation of {@link TimeTracker} is used to show deference between {@link PrettyTimeTracker#getTrackingDate()} and {@link PrettyTimeTracker#getReferenceDate()}
 * in user-friendly way. e.g: 4 minutes ago, in 3 month and so on.
 * This class uses {@link PrettyTime} to calculate and show the date.
 */
public class PrettyTimeTracker extends TimeTracker {
    private PrettyTime prettyTime;

    private Date referenceDate;
    private Date trackingDate;
    private int correctionUnit = -1;
    private int correctionAmount = -1;

    public PrettyTimeTracker(Date trackingDate) {
        setTrackingDate(trackingDate);

        this.prettyTime = new PrettyTime();
        prettyTime.getUnit(JustNow.class).setMaxQuantity(1000L * 60L);
    }

    /**
     * @return the instance of {@link PrettyTime} that is being used to calculate the date.
     * This might be useful to change the locale or the time units.
     */
    public PrettyTime getPrettyTime() {
        return prettyTime;
    }

    /**
     * @return the date that is taken as reference to calculate the deference from {@link PrettyTimeTracker#getTrackingDate()}.
     * @see #setReferenceDate(Date)
     */
    public Date getReferenceDate() {
        return referenceDate;
    }

    /**
     * Sets the reference date.
     *
     * @param referenceDate this date can be null in which case the current date is taken as reference.
     * @see #getReferenceDate()
     */
    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
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

    /**
     * Sets a specific amount of time to add or subtract from {@link #getReferenceDate()} while calculating the deference.
     * This is especially useful if you're showing dates that are very near from the current date,
     * In which case adding a few seconds will help showing "moments ago" instead of "in a few moments".
     *
     * @param unit   the calendar field. e.g: {@link Calendar#SECOND} or {@link Calendar#MINUTE}
     * @param amount the amount of date or time to be added to the field.
     *               this can be a negative number to subtract.
     * @see #unsetCorrectionMargin()
     */
    public void setCorrectionMargin(int unit, int amount) {
        this.correctionUnit = unit;
        this.correctionAmount = amount;
    }

    /**
     * Unsets the correction margin
     *
     * @see #setCorrectionMargin(int, int)
     */
    public void unsetCorrectionMargin() {
        this.correctionAmount = -1;
        this.correctionUnit = -1;
    }

    @Override
    public void onUpdate(AppCompatTextView textView) {
        Date trackingDate = getTrackingDate();
        if (trackingDate == null) return;

        Date referenceDate = getReferenceDate();
        if (referenceDate == null) referenceDate = new Date();

        if (correctionUnit != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(referenceDate);
            cal.add(correctionUnit, correctionAmount);
        }

        prettyTime.setReference(referenceDate);
        String formattedDate = prettyTime.format(trackingDate);

        textView.setText(formattedDate);
    }

    @Override
    public long getInterval() {
        return TimeUnit.SECONDS.toMillis(10);
    }
}
