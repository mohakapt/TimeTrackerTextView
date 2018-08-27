package com.github.mohaka.timeTrackerTextView.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mohaka.timeTrackerTextView.PrettyTimeTracker;
import com.github.mohaka.timeTrackerTextView.TimeTrackerTextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeTrackerTextView txtCreationDate = findViewById(R.id.txtCreationDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -20);

        txtCreationDate.addTracker(new PrettyTimeTracker(cal.getTime()));
    }
}
