package com.anythingintellect.jakesgit.util;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

public class BindingUtils {

    @BindingAdapter("android:text")
    public static void bindTextViewTextHideIfEmpty(TextView textView, String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(value);
        }
    }

}
