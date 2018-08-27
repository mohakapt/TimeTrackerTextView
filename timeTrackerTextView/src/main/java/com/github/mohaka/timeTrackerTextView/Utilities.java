package com.github.mohaka.timeTrackerTextView;

import android.content.Context;

/**
 * Created by MoHaKa on 2/7/17.
 */
class Utilities {
    private Context context;

    static Utilities with(Context context) {
        Utilities reVal = new Utilities();
        reVal.context = context;
        return reVal;
    }
}
