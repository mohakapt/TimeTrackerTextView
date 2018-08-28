package com.github.mohaka.timeTrackerTextView;

import android.support.v7.widget.AppCompatTextView;

import java.util.concurrent.TimeUnit;

public abstract class SimpleTimeTracker extends TimeTracker {
    public abstract boolean onUpdate(AppCompatTextView textView);

    @Override
    long getInterval() {
        return TimeUnit.SECONDS.toMillis(1);
    }
}
