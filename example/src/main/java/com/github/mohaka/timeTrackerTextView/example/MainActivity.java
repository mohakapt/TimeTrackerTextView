package com.github.mohaka.timeTrackerTextView.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.github.mohaka.timeTrackerTextView.BackgroundTintTimeTracker;
import com.github.mohaka.timeTrackerTextView.SimpleTimeTracker;
import com.github.mohaka.timeTrackerTextView.TimeTrackerTextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeTrackerTextView txtCreationDate = findViewById(R.id.txtCreationDate);

        int[] colors = {R.color.colorTrivial, R.color.colorAccent, R.color.colorUrgent};
        for (int i = 0; i < colors.length; i++)
            colors[i] = getResources().getColor(colors[i]);

        int[] durations = {4000, 8000};
        BackgroundTintTimeTracker timeTracker = new BackgroundTintTimeTracker(new Date(), durations, colors);

//        txtCreationDate.addTracker(new PrettyTimeTracker(new Date(),200));
        txtCreationDate.addTracker(timeTracker);
    }
}
