package com.teamup.mihaylov.teamup.base.utils.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.teamup.mihaylov.teamup.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by samui on 8.10.2017 г..
 */

public class LoadingIndicator {
    public static void showLoadingIndicator(LoadingView fragment) {

        AVLoadingIndicatorView spinner = new AVLoadingIndicatorView(fragment.getContext());
        spinner.setBackgroundColor(ContextCompat.getColor(fragment.getContext(), R.color.primary));
        spinner.setIndicator("BallClipRotatePulseIndicator");
        spinner.setPadding(0, 200, 0, 0);
        spinner.show();

        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        spinner.setLayoutParams(layoutParams);

        fragment.getLoadingContainer().removeAllViews();
        fragment.getLoadingContainer().addView(spinner);

        fragment.getLoadingContainer().setVisibility(View.VISIBLE);
        fragment.getContentContainer().setVisibility(View.GONE);
    }

    public static void hideLoadingIndicator(LoadingView fragment) {
        fragment.getLoadingContainer().setVisibility(View.GONE);
        fragment.getContentContainer().setVisibility(View.VISIBLE);
    }

    public interface LoadingView {
        View getContentContainer();

        ViewGroup getLoadingContainer();

        Context getContext();

        void showLoading();

        void hideLoading();
    }
}
