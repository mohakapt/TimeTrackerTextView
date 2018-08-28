package com.github.mohaka.timeTrackerTextView;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;

import java.util.Calendar;
import java.util.Date;

/**
 * @see PrettyTimeTracker
 * @see BackgroundTimeTracker
 */
public abstract class TimeTracker implements Comparable {
    private Date referenceDate;
    private int correctionUnit = -1;
    private int correctionAmount = -1;

    /**
     * @return the date that is taken as reference to calculate the deference from {@link PrettyTimeTracker#getTrackingDate()}.
     * @see #setReferenceDate(Date)
     */
    public Date getReferenceDate() {
        return referenceDate;
    }

    /**
     * This method should be used by subclasses to retrive the reference date (if null the current date).
     *
     * @param corrected if true the date will be corrected by {@link #setCorrectionMargin(int, int)} if available.
     * @return the date to be used as a reference in time calculations or the current date if {@link #getReferenceDate()} is null.
     */
    protected Date getReferenceDate(boolean corrected) {
        Date reVal = getReferenceDate();
        if (reVal == null) reVal = new Date();

        if (correctionUnit != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(reVal);
            cal.add(correctionUnit, correctionAmount);
            reVal = cal.getTime();
        }

        return reVal;
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

    /**
     * This method is called every {@link #getInterval()} on the UI thread and should be used only to update time-dependent textView.
     *
     * @param textView The {@link AppCompatTextView} that needs to get updated.
     * @return true to reschedule the timer one more time, false to stop the timer.
     * @see TimeTracker#getInterval()
     */
    abstract boolean onUpdate(AppCompatTextView textView);

    /**
     * This method is used to set the interval of calling {@link #onUpdate(AppCompatTextView)}.
     * Try to set the interval to the longest interval possible to avoid calling {@link #onUpdate(AppCompatTextView)} too much and waste cpu time.
     *
     * @return The interval in which {@link #onUpdate(AppCompatTextView)} should be called.
     * @see #onUpdate(AppCompatTextView)
     */
    abstract long getInterval();

    @Override
    public int compareTo(@NonNull Object other) {
        return this == other ? 0 : 1;
    }
}
