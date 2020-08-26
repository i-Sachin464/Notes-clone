package com.wrabbit.notesclone.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Progress {
    private Context mCtx;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    public Progress(Context ctx) {
        mCtx = ctx;
        init();
    }

    private void init() {
        relativeLayout = new RelativeLayout(mCtx);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));
        relativeLayout.setGravity(RelativeLayout.CENTER_IN_PARENT);

        progressBar = new ProgressBar(mCtx, null, android.R.attr.progressBarStyle);
        // Create new layout parameters for progress bar
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, // Width in pixels
                RelativeLayout.LayoutParams.WRAP_CONTENT // Height of progress bar
        );
        // Apply the layout parameters for progress bar
        progressBar.setLayoutParams(lp);
        // Get the progress bar layout parameters
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
        // Apply the layout rule for progress bar
        progressBar.setLayoutParams(params);
        // Set the progress bar color
//        progressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        // Finally,  add the progress bar to layout
        relativeLayout.addView(progressBar);
    }

    public void start() {
        relativeLayout.setVisibility(View.VISIBLE);
    }

    public void stop() {
        relativeLayout.setVisibility(View.GONE);
    }
}
