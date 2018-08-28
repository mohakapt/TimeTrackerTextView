package com.github.mohaka.timeTrackerTextView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by MoHaKa on 2/7/17.
 */
class Utilities {
    private Context context;

    public static Utilities with(Context context) {
        Utilities reVal = new Utilities();
        reVal.context = context;
        return reVal;
    }

    // Resources
    public String getRS(int res) {
        return context.getResources().getString(res);
    }

    public String getRS(int res, Object... formatArgs) {
        String string = getRS(res);
        if (!isEmpty(formatArgs))
            string = String.format(string, formatArgs);
        return string;
    }

    public String getRP(int res, int quantity) {
        return context.getResources().getQuantityString(res, quantity, quantity);
    }

    public int getRDi(int res) {
        return context.getResources().getDimensionPixelSize(res);
    }

    public boolean getRB(int res) {
        return context.getResources().getBoolean(res);
    }

    public int getRI(int res) {
        return context.getResources().getInteger(res);
    }

    public int getRC(int res) {
        return ContextCompat.getColor(context.getApplicationContext(), res);
    }

    public ColorStateList getRCs(int res) {
        return ContextCompat.getColorStateList(context.getApplicationContext(), res);
    }

    public Drawable getRD(int res) {
        return ContextCompat.getDrawable(context.getApplicationContext(), res);
    }

    public Typeface getRF(int res) {
        return ResourcesCompat.getFont(context, res);
    }

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

    public static <T> boolean isEmpty(final T[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isEmpty(final int[] a) {
        return a == null || a.length == 0;
    }
}
