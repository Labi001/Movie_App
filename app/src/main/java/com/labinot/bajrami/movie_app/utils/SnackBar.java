package com.labinot.bajrami.movie_app.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.labinot.bajrami.movie_app.R;

public class SnackBar {

    public static void showMessageSnackBar(Context context, View view,View view2, String message) {

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(context.getColor(R.color.accent))
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .setActionTextColor(context.getColor(R.color.white))
                .setAnchorView(view2)
                .setTextColor(context.getColor(R.color.white));
        snackbar.show();

    }
}
