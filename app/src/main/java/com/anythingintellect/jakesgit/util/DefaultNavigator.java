package com.anythingintellect.jakesgit.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

public class DefaultNavigator implements Navigator {

    private final Context context;

    public DefaultNavigator(Context context) {
        this.context = context;
    }

    @Override
    public void openBrowser(String url) {
        if (url == null || url.equalsIgnoreCase("")) {
            return;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

}
