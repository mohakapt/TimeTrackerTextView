package com.github.mohaka.timeTrackerTextView;

import android.support.v7.widget.AppCompatTextView;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PrettyTimeTracker extends TimeTracker {
    private PrettyTime prettyTime;

    private Date referenceDate;
    private Date trackingDate;
    private int correctionUnit = -1;
    private int correctionAmount = -1;

    public PrettyTimeTracker(Date trackingDate) {
        this.trackingDate = trackingDate;

        this.prettyTime = new PrettyTime();
        prettyTime.getUnit(JustNow.class).setMaxQuantity(1000L * 60L);
    }

    public PrettyTime getPrettyTime() {
        return prettyTime;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(Date trackingDate) {
        this.trackingDate = trackingDate;
    }

    public void setCorrectionMargin(int unit, int amount) {
        this.correctionUnit = unit;
        this.correctionAmount = amount;
    }

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
