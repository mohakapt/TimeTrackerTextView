package com.github.mohaka.timeTrackerTextView;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;

public abstract class TimeTracker implements Comparable {
    /**
     * This method is called every {@link #getInterval()} on the UI thread and should be used only to update time-dependent textView.
     *
     * @param textView The {@link AppCompatTextView} that needs to get updated.
     * @see TimeTracker#getInterval()
     */
    abstract void onUpdate(AppCompatTextView textView);

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
