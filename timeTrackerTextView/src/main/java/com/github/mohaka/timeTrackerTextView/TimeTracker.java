package com.github.mohaka.timeTrackerTextView;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;

public abstract class TimeTracker implements Comparable {
    abstract void onUpdate(AppCompatTextView textView);

    abstract long getInterval();

    @Override
    public int compareTo(@NonNull Object other) {
        return this == other ? 0 : 1;
    }
}
