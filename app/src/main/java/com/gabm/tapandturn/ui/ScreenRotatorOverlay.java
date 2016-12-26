package com.gabm.tapandturn.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by gabm on 31.10.16.
 */

public class ScreenRotatorOverlay {
    private LinearLayout dummyLayout;
    private WindowManager curWindowManager;
    private int currentlySetScreenOrientation ;
    private int defaultOrientation;


    public ScreenRotatorOverlay(Context context, WindowManager windowManager, int orientation, int defaultOrientation) {
        dummyLayout = new LinearLayout(context);
        curWindowManager = windowManager;
        this.defaultOrientation = defaultOrientation;

        changeOrientation(orientation);
    }

    public int getCurrentlySetScreenOrientation() {
        return currentlySetScreenOrientation;
    }

    public boolean isDefaultOrientation(int orientation) {
        return orientation == defaultOrientation;
    }
    public void changeOrientation(int orientation) {
        removeView();

        // if requested orientation is different from the device configured orientation
        // then enforece the new rotation by adding an overlay
        if (!isDefaultOrientation(orientation)) {

            Log.i("Overlay", "Adding for " + orientation);
            Log.i("Overlay", "Configured orientation " + defaultOrientation);

            WindowManager.LayoutParams dummyParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, 0, PixelFormat.RGBA_8888);
            dummyParams.screenOrientation = orientation;

            curWindowManager.addView(dummyLayout, dummyParams);

        } else {
            Log.i("Overlay", "Not adding anything");
        }

        currentlySetScreenOrientation = orientation;
    }

    // Immidiately removes the current view
    public void removeView() {
        if (dummyLayout.getParent() != null) {
            Log.i("Overlay", "Removing overlay");

            curWindowManager.removeView(dummyLayout);
        }
    }
}
