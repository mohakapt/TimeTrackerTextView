package com.github.mohaka.timeTrackerTextView;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by MoHaKa on 2/7/17.
 */
class Utilities {

    /**
     * Execute the given {@link Runnable} on the ui thread.
     *
     * @param runnable The runnable to execute.
     */
    public static void runOnUiThread(Runnable runnable) {
        Thread uiThread = Looper.getMainLooper().getThread();
        if (Thread.currentThread() != uiThread) new Handler(Looper.getMainLooper()).post(runnable);
        else runnable.run();
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
