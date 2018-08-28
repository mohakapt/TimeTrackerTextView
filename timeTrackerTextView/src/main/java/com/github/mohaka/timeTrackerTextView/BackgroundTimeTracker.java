package com.github.mohaka.timeTrackerTextView;

import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import java.util.Date;

import static com.github.mohaka.timeTrackerTextView.Utilities.isEmpty;

public class BackgroundTimeTracker extends TimeTracker {
    private Date trackingDate;
    private int[] durationArray;

    private int[] backgroundResIdArray;
    private int[] tintColorArray;

    private boolean gradual;

    public BackgroundTimeTracker(Date trackingDate, int[] durations) {
        setTrackingDate(trackingDate);
        this.durationArray = durations;
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
    boolean onUpdate(AppCompatTextView textView) {
        Log.i("BackgroundTimeTracker", "onUpdate(AppCompatTextView)");

        Date referenceDate = getReferenceDate(true);
        Date trackingDate = getTrackingDate();
        if (trackingDate == null) return false;
        if (isEmpty(durationArray)) return false;

        boolean hasTints = !isEmpty(tintColorResIdArray) || !isEmpty(tintColorArray);
        boolean hasBackgrounds = !isEmpty(backgroundResIdArray) || !isEmpty(backgroundArray);
        if (!hasTints && !hasBackgrounds) return false;

        if (hasTints) {
            if (!isEmpty(backgroundArray))
                textView.setBackgroundDrawable(backgroundArray[0]);
            else
                textView.setBackgroundResource(backgroundResIdArray[0]);
        }

        int background = 0;
        boolean result = false;

        long difference = referenceDate.getTime() - trackingDate.getTime();

        for (int i = 0; i < durationArray.length; i++) {
            if (difference < durationArray[i]) {

                backgroundArray.length > i


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
        boolean hasTints = !isEmpty(tintColorResIdArray) || !isEmpty(tintColorArray);

        if (gradual && hasTints)
            return 200;

        if (!isEmpty(durationArray))
            return durationArray[0] / 2;

        return 1000;
    }
}
