package com.github.mohaka.timeTrackerTextView.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mohaka.timeTrackerTextView.BackgroundTimeTracker;
import com.github.mohaka.timeTrackerTextView.PrettyTimeTracker;
import com.github.mohaka.timeTrackerTextView.TimeTrackerTextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeTrackerTextView txtCreationDate = findViewById(R.id.txtCreationDate);

        int[] colors = {R.color.colorTrivial, R.color.colorAccent, R.color.colorUrgent};
        int[] durations = {4000, 8000};
        BackgroundTimeTracker timeTracker = new BackgroundTimeTracker(new Date(), durations, colors);

        txtCreationDate.addTracker(new PrettyTimeTracker(new Date(),200));
        txtCreationDate.addTracker(timeTracker);
    }
}
